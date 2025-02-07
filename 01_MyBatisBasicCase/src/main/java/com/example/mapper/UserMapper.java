package com.example.mapper;

import com.example.pojo.UserDTO;

import java.util.List;

public interface UserMapper {

    /** 插入用户数据，返回变更行数 */
    int insertUser();

    /** 删除一条数据 */
    int deleteUser();

    /** 更新一条数据 */
    int updateUser();

    /** 查询一条数据 */
    UserDTO queryUser();

    /** 查询所有数据 */
    List<UserDTO> queryAll();

}
