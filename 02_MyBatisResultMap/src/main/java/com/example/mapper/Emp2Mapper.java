package com.example.mapper;

import com.example.pojo.Emp2;
import org.apache.ibatis.annotations.Param;

public interface Emp2Mapper {

    /**
     * 根据 empId 查询 emp 对象，多对一，使用级联方式
     */
    Emp2 queryEmpByEmpIdUseJiLian(@Param("empId") Integer empId);

    /**
     * 根据 empId查询 emp 对象，多对一，使用 association
     */
    Emp2 queryEmpByEmpIdAssociation(@Param("empId") Integer empId);

    /**
     * 通过分步查询的方式，根据 empId 查询员工信息
     */
    Emp2 queryEmpByEmpIdUseStep(@Param("empId") Integer empId);

}
