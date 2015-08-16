package example.microservice;

import com.ajaxjson.JSONMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @project spring-boot-tutorial
 * @author tony.shen
 * @date 2014年4月26日 下午5:33:04
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 * 参考：Web MVC framework   http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html
 */
@RestController
@RequestMapping("/json")
public class UserController {

    private transient static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${spring.mvc.view.prefix:aaa}")
    private String prefix;

    @RequestMapping(value={"/message"}, method = {RequestMethod.GET, RequestMethod.POST})
    private JSONMessage getMessage(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        logger.debug("getMessage success, code: {}", 200);
        return JSONMessage.newMessageOnSuccess();
    }

    @RequestMapping(value={"/message2"}, method={RequestMethod.GET, RequestMethod.POST})
    private JSONMessage getMessage2(@ModelAttribute("user") User user, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<User> userList = new ArrayList<>();
        userList.add(new User("tony"));
        userList.add(new User("shen"));
        return JSONMessage.newMessageOnSuccess()
                .setResult("userName", user.getName())
                .setResult("userId", user.getId())
                .setResult("userList", userList);
    }

    @RequestMapping("/{id}")
    private User view(@PathVariable("id") Long id, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        User user = new User();
        user.setId(id);
        user.setName("foo-bar" + this.prefix);
        return user;
    }

    @RequestMapping("/getBody")
    private String getBody(HttpSession session) {
        return "Hello1";
    }
    
    @RequestMapping("/mv")
    private ModelAndView modelAndView() {
        return new ModelAndView("abc");
    }
    
    @RequestMapping("/mod")
    private String model(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", "test");
        return "index";
    }
}
