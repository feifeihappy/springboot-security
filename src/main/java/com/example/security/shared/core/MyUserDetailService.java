package com.example.security.shared.core;


import com.example.security.dao.RoleMapper;
import com.example.security.dao.UserMapper;
import com.example.security.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by stefan
 * Date on 2018-05-17  22:49
 * Convertion over Configuration!
 */
@Component
@Slf4j
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    UserMapper userMapper;
    @Resource
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Map<Object,Object>> list = userMapper.findByUsername(username);

        if (CollectionUtils.isEmpty(list)){
            throw new AuthenticationCredentialsNotFoundException("authError");
        }
        Map<Object, Object>  user =  list.get(0);

        String password = passwordEncoder.encode(String.valueOf(user.get("password")));

        log.info("{}",user);
        List<Role> role = roleMapper.findByUsername(username);
        log.info("{}",role);
        List<GrantedAuthority> authorities = new ArrayList<>();
        role.forEach(role1 -> authorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+role1.getRolename())));
        log.info("{}",authorities);
        return new User(String.valueOf(user.get("username")),password,authorities);
    }
}
