package com.example;

import com.example.mapper.UserParamMapper;
import com.example.pojo.UserDTO;
import com.example.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MyBatisParamCaseTest {

    @Test
    public void testQueryById() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        UserParamMapper mapper = sqlSession.getMapper(UserParamMapper.class);
        // select * from t_user where id = 1
        UserDTO user = mapper.queryUserById(1);
        System.out.println("MyBatisParamCaseTest: testQueryById() user = " + user);
        sqlSession.close();
    }

    @Test
    public void testQueryByUserName() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        UserParamMapper mapper = sqlSession.getMapper(UserParamMapper.class);
        UserDTO user = mapper.queryUserByUsername("zhangsan");
        System.out.println("MyBatisParamCaseTest: testQueryByUserName() user = " + user);
        sqlSession.close();
    }

    @Test
    public void testCheckLogin() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        UserParamMapper mapper = sqlSession.getMapper(UserParamMapper.class);
        UserDTO user = mapper.checkLogin("zhangsan", "8888");
        System.out.println("MyBatisParamCaseTest: testCheckLogin() user = " + user);
        sqlSession.close();
    }

    @Test
    public void testCheckLoginByMap() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        UserParamMapper mapper = sqlSession.getMapper(UserParamMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("username", "zhangsan");
        map.put("password", "8888");
        UserDTO user = mapper.checkLoginByMap(map);
        System.out.println("MyBatisParamCaseTest: testCheckLoginByMap() user = " + user);
        sqlSession.close();
    }

    @Test
    public void testInsertUser() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        UserParamMapper mapper = sqlSession.getMapper(UserParamMapper.class);
        UserDTO user = new UserDTO(null, "lisi", "123456", 21, "女", "345@126.com");
        Integer result = mapper.insertUser(user);
        System.out.println("MyBatisParamCaseTest: testInsertUser() result = " + result);
        sqlSession.close();
    }

    @Test
    public void testCheckLoginByParam() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        UserParamMapper mapper = sqlSession.getMapper(UserParamMapper.class);
        UserDTO user = mapper.checkLoginByParam("zhangsan", "8888");
        System.out.println("MyBatisParamCaseTest: testCheckLoginByParam() user = " + user);
        sqlSession.close();
    }

}
