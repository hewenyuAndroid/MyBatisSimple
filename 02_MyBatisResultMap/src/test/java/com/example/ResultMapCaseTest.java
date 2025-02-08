package com.example;

import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import com.example.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class ResultMapCaseTest {

    @Test
    public void testQueryEmpByEmpId() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.queryEmpByEmpId(1);
        System.out.println("ResultMapCaseTest: testQueryEmpByEmpId(): emp=" + emp);
        sqlSession.close();
    }

    @Test
    public void testQueryEmpByEmpIdWithResultMap() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.queryEmpByEmpIdWithResultMap(1);
        System.out.println("ResultMapCaseTest: testQueryEmpByEmpIdWithResultMap(): emp=" + emp);
        sqlSession.close();
    }

}
