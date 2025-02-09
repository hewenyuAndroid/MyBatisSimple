package com.example.mapper;

import com.example.pojo.Dept2;
import org.apache.ibatis.annotations.Param;

public interface Dept2Mapper {

    /** 根据 deptId 查询所有员工 */
    Dept2 queryDeptEmpByDeptId(@Param("deptId") Integer deptId);

    /** 根据 deptId 查询部门所有员工 */
    Dept2 queryDeptEmpByDeptIdUseStep(@Param("deptId") Integer deptId);

}
