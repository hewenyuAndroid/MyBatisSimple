package com.example.mybatis.mp;

import com.example.mybatis.mp.domain.User;
import com.example.mybatis.mp.mapper.UserMapper2;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusBaseMapperTest {

    @Resource
    private UserMapper2 userMapper2;

    @Test
    public void testInsert() {
        User user = new User();
        user.setId(6L);
        user.setName("zhangsan");
        user.setAge(20);
        user.setEmail("zhangsan@qq.com");

        userMapper2.insert(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(6L);
        user.setName("lisi");
        user.setAge(22);
        user.setEmail("lisi@qq.com");

        userMapper2.updateById(user);
    }


    @Test
    public void testSelect() {
        User user = userMapper2.selectById(6L);
        System.out.println(user);
    }

    @Test
    public void testDelete() {
        int count = userMapper2.deleteById(6L);
        System.out.println("delete count: " + count);
    }

    @Test
    public void testSelectByUsername() {
        User user = userMapper2.selectByUsername("Tom");
        System.out.println(user);
    }


}
