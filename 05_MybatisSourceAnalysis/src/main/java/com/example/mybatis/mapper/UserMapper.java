package com.example.mybatis.mapper;

import com.example.mybatis.pojo.User;

import java.util.List;

public interface UserMapper {

    List<User> selectAll();

}
