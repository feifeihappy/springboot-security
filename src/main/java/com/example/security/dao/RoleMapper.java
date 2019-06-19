package com.example.security.dao;

import com.example.security.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {

    @Select("select * from sys_role where username = #{username}")
    List<Role> findByUsername(String username);
}
