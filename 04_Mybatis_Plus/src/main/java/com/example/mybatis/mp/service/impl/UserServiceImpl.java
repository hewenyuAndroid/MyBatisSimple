package com.example.mybatis.mp.service.impl;

import com.example.mybatis.mp.domain.User;
import com.example.mybatis.mp.mapper.UserMapper;
import com.example.mybatis.mp.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

}
