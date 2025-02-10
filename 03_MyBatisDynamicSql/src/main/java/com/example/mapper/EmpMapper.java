package com.example.mapper;

import com.example.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {

    /** 多条件查询，使用 if 标签 */
    List<Emp> queryEmpByConditionUseIf(@Param("emp") Emp emp);

    /** 多条件查询，使用 where + if 标签 */
    List<Emp> queryEmpByConditionUseWhere(@Param("emp") Emp emp);

    /** 多条件查询，使用 trim 标签 */
    List<Emp> queryEmpByConditionUseTrim(@Param("emp") Emp emp);

    /** 多条件查询，使用 choose 标签，选择某个一条件 */
    List<Emp> queryEmpByConditionUseChoose(@Param("emp") Emp emp);

    /** 批量插入 emp 数据 */
    void batchInsertEmp(@Param("emps") List<Emp> emps);

    /** 批量删除 emp 数据 */
    void batchDeleteByIds(@Param("ids") List<Integer> ids);

    Emp checkLogin(@Param("username") String username, @Param("password") String password);

}
