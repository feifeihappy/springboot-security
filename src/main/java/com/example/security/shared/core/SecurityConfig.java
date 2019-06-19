package com.example.security.shared.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @Description: 通过WebSecurityConfigurerAdapter来实现请求的统一拦截
 * @Author: zhaopf@mti-sh.cn
 * @Date: 2019/6/17 15:34
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    MyUserDetailService myUserDetailService;

    //身份验证管理生成器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        1.启用内存用户存储
//        auth.inMemoryAuthentication()
//                .withUser("xfy").password(passwordEncoder().encode("1234")).roles("ADMIN").and()
//                .withUser("tom").password(passwordEncoder().encode("1234")).roles("USER");
////        2.基于数据库表进行验证
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username,password,enabled from user where username = ?")
//                .authoritiesByUsernameQuery("select username,rolename from role where username=?")
//                .passwordEncoder(passwordEncoder());

//        3.配置自定义的用户服务
        auth.userDetailsService(myUserDetailService);
    }

    //HTTP请求安全处理
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
                .and()
                .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
                .anyRequest()               // 任何请求,登录后可以访问
                .authenticated();


    }

    //WEB安全
    @Override
    public void configure(WebSecurity web) throws Exception {

    }


    //手动将PasswordEncoder注入到ioc容器中
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
