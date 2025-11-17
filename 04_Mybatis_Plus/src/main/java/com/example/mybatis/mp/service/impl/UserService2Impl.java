package com.example.mybatis.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.mp.domain.User;
import com.example.mybatis.mp.mapper.UserMapper2;
import com.example.mybatis.mp.service.UserService2;
import org.springframework.stereotype.Service;

/**
 * UserService2Impl 需要继承自 com.baomidou.mybatisplus.extension.service.impl.ServiceImpl，其内部实现了 IService<T> 接口定义的方法
 */
@Service
public class UserService2Impl extends ServiceImpl<UserMapper2, User> implements UserService2 {


}
