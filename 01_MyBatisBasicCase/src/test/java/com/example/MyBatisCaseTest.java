package com.example;

import com.example.mapper.UserMapper;
import com.example.pojo.UserDTO;
import com.example.utils.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisCaseTest {

    @Test
    public void testInsert() throws IOException {
        // 1. 获取核心配置文件的输入流
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        // 2. 获取 SqlSessionFactoryBuilder 对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 3. 获取 SqlSessionFactory 对象
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 4. 获取 SqlSession 会话对象
        // sqlSessionFactory.openSession() 获取的 sqlSession 会话对象默认不会自动提交事务
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        // sqlSessionFactory.openSession(true) 获取的 sqlSession 会话对象默认自动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 5. 获取 UserMapper 的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 6. 执行 UserMapper 中的方法执行对应的sql
        int result = mapper.insertUser();
        System.out.println("MyBatisCaseTest: testInsert(): result=" + result);
        // 7. 如果获取的 sqlSession 会话没有主动提交事务时，需要手动提交事务
//        sqlSession.commit();
        // 8. 关闭会话
        sqlSession.close();
    }

    @Test
    public void testDelete() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int result = mapper.deleteUser();
        System.out.println("MyBatisCaseTest: testDelete(): result=" + result);
        sqlSession.close();
    }

    @Test
    public void testUpdate() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int result = mapper.updateUser();
        System.out.println("MyBatisCaseTest: testUpdate(): result=" + result);
        sqlSession.close();
    }

    @Test
    public void testQuery() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        UserDTO user = mapper.queryUser();
        System.out.println("MyBatisCaseTest: testQuery(): user=" + user);
        sqlSession.close();
    }

    @Test
    public void testQueryAll() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<UserDTO> userList = mapper.queryAll();
        System.out.println("MyBatisCaseTest: testQuery(): userList=" + userList);
        sqlSession.close();
    }

}
