package cn.itcast.user.web;

import cn.itcast.user.config.PatternProperties;
import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PatternProperties properties;
//    @Value("${pattern.dateformat}")
//    String dateformat;

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable Long id
            ,@RequestHeader(required = false) String wocao//必须加上这个required=false
    ) {
        System.err.println(wocao);
        return userService.queryById(id);
    }
//    @GetMapping("/otto")
//    public String getDateformat(){
//        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
//    }
    @GetMapping("/prop")
    public PatternProperties getEnvSharedValue(){
        return properties;
    }
}
