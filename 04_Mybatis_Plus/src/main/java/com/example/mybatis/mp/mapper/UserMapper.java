package com.example.mybatis.mp.mapper;

import com.example.mybatis.mp.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * User Mapper 接口
 */
@Mapper
public interface UserMapper {

    /**
     * 查询所有用户
     *
     * @return 所有用户信息
     */
    List<User> selectAll();

}
