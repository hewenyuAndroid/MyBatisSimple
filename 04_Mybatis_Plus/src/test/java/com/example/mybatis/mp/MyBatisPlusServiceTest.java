package com.example.mybatis.mp;

import com.example.mybatis.mp.domain.User;
import com.example.mybatis.mp.service.UserService2;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusServiceTest {

    @Resource
    private UserService2 userService2;

    @Test
    public void testInsert() {
        User user = new User();
        user.setId(7L);
        user.setName("wangwu");
        user.setAge(21);
        user.setEmail("wangwu@qq.com");
        userService2.save(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(7L);
        user.setName("wangwu2");
        user.setAge(22);
        user.setEmail("wangwu2@qq.com");
        userService2.updateById(user);
    }

    @Test
    public void testSelect() {
        User user = userService2.getById(7L);
        System.out.println(user);
    }

    @Test
    public void testDelete() {
        boolean result = userService2.removeById(7L);
        System.out.println("result:" + result);
    }

}
