package com.example;

import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import com.example.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MybatisDynamicSqlCaseTest {

    @Test
    public void testQueryEmpByConditionUseIf() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = new Emp(null, "zhangsan", null, 20, null, null);
        List<Emp> result = mapper.queryEmpByConditionUseIf(emp);
        System.out.println("MybatisDynamicSqlCaseTest: testQueryEmpByConditionUseIf(), result=" + result);
        sqlSession.close();
    }

    @Test
    public void testQueryEmpByConditionUseWhere() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = new Emp(null, "zhangsan", null, 20, null, null);
        List<Emp> result = mapper.queryEmpByConditionUseWhere(emp);
        System.out.println("MybatisDynamicSqlCaseTest: testQueryEmpByConditionUseWhere(), result=" + result);
        sqlSession.close();
    }

    @Test
    public void testQueryEmpByConditionUseTrim() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = new Emp(null, "zhangsan", null, 20, null, null);
        List<Emp> result = mapper.queryEmpByConditionUseTrim(emp);
        System.out.println("MybatisDynamicSqlCaseTest: testQueryEmpByConditionUseTrim(), result=" + result);
        sqlSession.close();
    }

    @Test
    public void testQueryEmpByConditionUseChoose() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        // 优先使用 empUsername 条件
//        Emp emp = new Emp(null, "zhangsan", null, 20, null, null);
        // empUsername 条件不成立时，使用 age 条件
//        Emp emp = new Emp(null, null, null, 20, null, null);
        // 以上条件都不成立时，走最后的 otherwise 分支
        Emp emp = new Emp(null, null, null, null, null, null);
        List<Emp> result = mapper.queryEmpByConditionUseChoose(emp);
        System.out.println("MybatisDynamicSqlCaseTest: testQueryEmpByConditionUseChoose(), result=" + result);
        sqlSession.close();
    }

    @Test
    public void testBatchInsert() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp1 = new Emp(null, "insert3", "123456", 20, "男", 1);
        Emp emp2 = new Emp(null, "insert4", "123456", 21, "女", 2);
        List<Emp> list = new ArrayList<>();
        list.add(emp1);
        list.add(emp2);
        mapper.batchInsertEmp(list);
        System.out.println("MybatisDynamicSqlCaseTest: testBatchInsert()");
        sqlSession.close();
    }

    @Test
    public void testBatchDelete() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        mapper.batchDeleteByIds(list);
        System.out.println("MybatisDynamicSqlCaseTest: testBatchDelete()");
        sqlSession.close();
    }

    @Test
    public void testCheckLogin() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.checkLogin("zhangsan", "123456");
        System.out.println("MybatisDynamicSqlCaseTest: testCheckLogin() emp=" + emp);
        sqlSession.close();
    }

}
