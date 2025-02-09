package com.example.mapper;

import com.example.pojo.Dept;
import org.apache.ibatis.annotations.Param;

public interface DeptMapper {

    /** 根据 deptId 查询部门信息 */
    Dept queryDeptByDeptId(@Param("deptId") String deptId);

}
