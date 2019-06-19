package com.example.security.controller;

import com.example.security.service.SimpleSecureService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@RestController
public class HttpController implements WebMvcConfigurer {

    @Resource
    private SimpleSecureService simpleSecureService;


    @GetMapping(value = "/")
    public String request(Map<String, Object> model) {

        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        String secure = simpleSecureService.secure();
        String authorized = simpleSecureService.authorized();
        System.out.println(secure);
        System.out.println(authorized);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        //instanceof 判定是否是其特定类的一个实例 或其 子类
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        System.out.println(username);



        return "home";
    }


    @GetMapping("/user")
    public String userSecure() {
        return simpleSecureService.secure();
    }

    @GetMapping("/admin")
    public String adminSecure() {

        return simpleSecureService.authorized();
    }



    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        try {
            registry.addViewController("/login").setViewName("login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
