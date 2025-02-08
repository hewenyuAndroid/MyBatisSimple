package com.example.mapper;

import com.example.pojo.Emp;
import org.apache.ibatis.annotations.Param;

public interface EmpMapper {

    /**
     * 根据员工id查询员工
     */
    Emp queryEmpByEmpId(@Param("empId") Integer empId);

    /**
     * 根据员工id查询员工使用 resultMap 方式映射字段和属性
     */
    Emp queryEmpByEmpIdWithResultMap(@Param("empId") Integer empId);

}
