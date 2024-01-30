package cn.itcast.order.pojo;

import lombok.Data;
import com.feignapi.pojo.User;
@Data
public class Order {
    private Long id;
    private Long price;
    private String name;
    private Integer num;
    private Long userId;
    private User user;
}