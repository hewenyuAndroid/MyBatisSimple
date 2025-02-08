package com.example.mapper;

import com.example.pojo.UserDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SelectMapper {

    /** 根据id查询用户 */
    UserDTO queryUserById(@Param("id") Integer id);

    /**
     * 查询全部用户
     * 若查询的结果大于 1 条时，不能使用实体类对象接收，需要使用集合
     */
    List<UserDTO> queryAllUser();

    /** 查询用户总量 */
    Integer queryUserCount();

    /** 根据id查询用户，结果封装到map中 */
    Map<String, Object> queryUserByIdToMap(@Param("id") Integer id);

    /**
     * 查询所有用户，每个用户数据封装到一个map中，所有用户map信息封装到一个list中
     */
    List<Map<String, Object>> queryAllUserToMap();

    /**
     * 查询所有用户，每个用户数据封装到一个map中，所有用户map信息，封装到一个容器map中，容器的key为 @MapKey 注解指定的字段
     */
    @MapKey("id")
    Map<String, Map<String, Object>> queryAllUserToKeyMap();

}
