package com.example;

import com.example.mapper.SelectMapper;
import com.example.pojo.UserDTO;
import com.example.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class MyBatisSelectCaseTest {

    @Test
    public void testQueryById() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        SelectMapper mapper = sqlSession.getMapper(SelectMapper.class);
        UserDTO user = mapper.queryUserById(1);
        System.out.println("MyBatisSelectCaseTest: testQueryById(), user=" + user);
        sqlSession.close();
    }

    @Test
    public void testQueryAllUser() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        SelectMapper mapper = sqlSession.getMapper(SelectMapper.class);
        List<UserDTO> list = mapper.queryAllUser();
        System.out.println("MyBatisSelectCaseTest: testQueryAllUser(), list=" + list);
        sqlSession.close();
    }

    @Test
    public void testQueryUserCount() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        SelectMapper mapper = sqlSession.getMapper(SelectMapper.class);
        Integer userCount = mapper.queryUserCount();
        System.out.println("MyBatisSelectCaseTest: testQueryUserCount(), userCount=" + userCount);
        sqlSession.close();
    }

    @Test
    public void testQueryByIdToMap() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        SelectMapper mapper = sqlSession.getMapper(SelectMapper.class);
        Map<String, Object> userMap = mapper.queryUserByIdToMap(1);
        System.out.println("MyBatisSelectCaseTest: testQueryByIdToMap(), userMap=" + userMap);
        sqlSession.close();
    }

    @Test
    public void testQueryAllUserToMap() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        SelectMapper mapper = sqlSession.getMapper(SelectMapper.class);
        List<Map<String, Object>> list = mapper.queryAllUserToMap();
        System.out.println("MyBatisSelectCaseTest: testQueryAllUserToMap(), list=" + list);
        sqlSession.close();
    }

    @Test
    public void testQueryAllUserToKeyMap() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        SelectMapper mapper = sqlSession.getMapper(SelectMapper.class);
        Map<String, Map<String, Object>> map = mapper.queryAllUserToKeyMap();
        System.out.println("MyBatisSelectCaseTest: testQueryAllUserToKeyMap(), map=" + map);
        sqlSession.close();
    }

}
