package com.example.mapper;

import com.example.pojo.Emp2;
import org.apache.ibatis.annotations.Param;

public interface Emp2Mapper {

    /**
     * 根据 empId查询 emp 对象，多对一
     */
    Emp2 queryEmpByEmpId(@Param("empId") Integer empId);

}
