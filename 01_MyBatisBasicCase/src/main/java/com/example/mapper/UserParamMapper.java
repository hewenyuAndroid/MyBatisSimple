package com.example.mapper;

import com.example.pojo.UserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserParamMapper {

    /** 根据id查询用户信息 */
    UserDTO queryUserById(Integer id);

    /** 根据 username 查询用户信息 */
    UserDTO queryUserByUsername(String username);

    /** 检查登录 */
    UserDTO checkLogin(String username, String password);

    /** 检查登录，使用map传参 */
    UserDTO checkLoginByMap(Map<String, Object> map);

    /** 添加用户信息 */
    Integer insertUser(UserDTO user);

    /** 检查登录，使用 @Param 注解指定参数名称 */
    UserDTO checkLoginByParam(@Param("username") String username, @Param("password") String password);

}
