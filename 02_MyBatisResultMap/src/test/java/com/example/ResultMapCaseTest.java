package com.example;

import com.example.mapper.Emp2Mapper;
import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import com.example.pojo.Emp2;
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

    @Test
    public void testQueryEmpByEmpIdWithObj() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        Emp2Mapper mapper = sqlSession.getMapper(Emp2Mapper.class);
        Emp2 emp = mapper.queryEmpByEmpId(1);
        System.out.println("ResultMapCaseTest: testQueryEmpByEmpIdWithObj(): emp=" + emp);
        sqlSession.close();
    }

}
