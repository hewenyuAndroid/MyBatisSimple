package com.example;

import com.example.mapper.Dept2Mapper;
import com.example.mapper.Emp2Mapper;
import com.example.mapper.EmpMapper;
import com.example.pojo.Dept2;
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
    public void testQueryEmpByEmpIdJiLian() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        Emp2Mapper mapper = sqlSession.getMapper(Emp2Mapper.class);
        Emp2 emp = mapper.queryEmpByEmpIdUseJiLian(1);
        System.out.println("ResultMapCaseTest: testQueryEmpByEmpIdJiLian(): emp=" + emp);
        sqlSession.close();
    }

    @Test
    public void testQueryEmpByEmpIdAssociation() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        Emp2Mapper mapper = sqlSession.getMapper(Emp2Mapper.class);
        Emp2 emp = mapper.queryEmpByEmpIdAssociation(1);
        System.out.println("ResultMapCaseTest: testQueryEmpByEmpIdAssociation(): emp=" + emp);
        sqlSession.close();
    }

    @Test
    public void testQueryEmpByEmpIdUseStep() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        Emp2Mapper mapper = sqlSession.getMapper(Emp2Mapper.class);
        Emp2 emp = mapper.queryEmpByEmpIdUseStep(1);
        System.out.println("ResultMapCaseTest: testQueryEmpByEmpIdUseStep(): emp=" + emp);
        sqlSession.close();
    }

    @Test
    public void testQueryDeptEmpByDeptId() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        Dept2Mapper mapper = sqlSession.getMapper(Dept2Mapper.class);
        Dept2 dept = mapper.queryDeptEmpByDeptId(1);
        System.out.println("ResultMapCaseTest: testQueryDeptEmpByDeptId(): dept=" + dept);
        sqlSession.close();
    }

    @Test
    public void testQueryDeptEmpByDeptIdUseStep() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        Dept2Mapper mapper = sqlSession.getMapper(Dept2Mapper.class);
        Dept2 dept = mapper.queryDeptEmpByDeptIdUseStep(1);
        System.out.println("ResultMapCaseTest: testQueryDeptEmpByDeptIdUseStep(): dept=" + dept);
        sqlSession.close();
    }

}
