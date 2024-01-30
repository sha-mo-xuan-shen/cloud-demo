package com.gateway.util;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Order(-1)//执行优先级，越小越高，也可以通过实现Ordered方法并返回-1一样
@Component
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange
            , GatewayFilterChain chain
//这个叫做过滤器链，过滤器们都是通过过滤器链依次执行
//请求路由后，一定是请求路由后，会将当前路由过滤器(id下面的)和DefaultFilter、GlobalFilter
//合并到一个过滤器链中排序后依次执行每个过滤器
    ) {
        /**
         * 过滤器执行顺序
         * 每一个过滤器都必须指定一个int类型的order值，order值越小，优先级越高，执行顺序越靠前
         * GlobalFilter通过实现Ordered接口，或者添加@Order注解来指定order值，由我们自己指定
         * 路由过滤器(内部过滤器)和defaultFilter的order有Spring指定，默认是按照声明顺序从1递增
         * 当过滤器的order值一样时，会按照defaulterFilter>路由过滤器>GlobalFilter的顺序执行
         */
        //获取请求参数
        ServerHttpRequest request = exchange.getRequest();//虽然叫exchange，但它是上下文
        MultiValueMap<String, String> params = request.getQueryParams();//获取请求的参数
        //获取参数中的authorization参数
        String auth = params.getFirst("authorization");
        //判断是否等于admin
        //http://localhost:10010/order/101?authorization=admin
        if ("admin".equals(auth)){
            //是就放行
            return chain.filter(exchange);
        }
        //否就拦截
        //设置状态码
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
