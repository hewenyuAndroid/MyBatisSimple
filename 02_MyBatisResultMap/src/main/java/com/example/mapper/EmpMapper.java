package com.example.mapper;

import com.example.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {

    /**
     * 根据员工id查询员工
     */
    Emp queryEmpByEmpId(@Param("empId") Integer empId);

    /**
     * 根据员工id查询员工使用 resultMap 方式映射字段和属性
     */
    Emp queryEmpByEmpIdWithResultMap(@Param("empId") Integer empId);

    /**
     * 根据 deptId 查询员工信息
     */
    List<Emp> queryEmpListByDeptId(@Param("deptId") Integer deptId);

}
