package com.example.mybatis;

import com.example.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MyBatisSimple {

    public static void main(String[] args) throws Exception {
        // 1. 获取配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        // 2. 加载解析配置文件并获取 SqlSessionFactory 对象
        // SqlSessionFactory 对象没有通过 new DefaultSqlSessionFactory() 直接获取，而是通过一个 Builder 对象来建造
        // 首先 SqlSessionFactory 是用来创建 SqlSession 对象的，SqlSessionFactory 应该是个单例
        // 全局配置文件 和 mapper映射文件也只需要在系统启动的时候完成加载操作
        // 通过建造者模式构建复杂的对象，1. 完成配置文件的加载解析 2. 完成 SqlSessionFactory 对象的创建
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                .build(in);
        // 3. 通过 SqlSessionFactory 对象获取 SqlSession 对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4. 通过 SqlSession 提供的 API 操作数据库
        List<User> users = sqlSession.selectList("com.example.mybatis.mapper.UserMapper.selectAll");
        System.out.println(users);
    }

}
