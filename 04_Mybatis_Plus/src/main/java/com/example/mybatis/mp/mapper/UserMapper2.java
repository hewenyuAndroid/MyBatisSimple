package com.example.mybatis.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.mp.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 使用MyBatis Plus实现增删改的操作
 *
 * 创建 UserMapper2 接口，继承自 BaseMapper,
 * BaseMapper 内部定义了一系列的增删改操作方法，MyBatis 在扫描到对应方法后会创建对应方法的代理实现
 */
@Mapper
public interface UserMapper2 extends BaseMapper<User> {

}
