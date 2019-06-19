package com.example.security.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    @Select("select * from sys_user where username = #{username}")
    List<Map<Object,Object>> findByUsername(@Param("username") String username);
}
