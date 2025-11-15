package com.example.mybatis.mp.controller;

import com.example.mybatis.mp.domain.User;
import com.example.mybatis.mp.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/select_all")
    public List<User> selectAll() {
        return userService.selectAll();
    }

}
