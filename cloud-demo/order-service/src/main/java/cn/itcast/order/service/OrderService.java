package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import com.feignapi.clients.UserClient;
import com.feignapi.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserClient userClient;
//    @Autowired
//    RestTemplate restTemplate;
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
//        String url = "http://userservice/user/"+order.getUserId();
        User user = userClient.findById(order.getUserId());
//        User user = restTemplate.getForObject(url, User.class);
        order.setUser(user);
        return order;
    }
}
