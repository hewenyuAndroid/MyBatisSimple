

myBatis resultMap 使用

# resultMap 处理属性和字段的映射关系

```xml
<!-- Emp queryEmpByEmpId(@Param("id") Integer empId); -->
<select id="queryEmpByEmpId" resultType="Emp">
    <!--
        由于数据库字段名称 emp_id 和实体类属性名称 empId 不一致，直接查询时无法正确赋值
        emp=Emp{empId=null, empUsername='null', empPassword='null', age=20, gender='男', deptId=null}
    -->
    select * from t_emp where emp_id = #{empId}
</select>

<!--
    resultMap 设置自定义的映射关系
    id: 唯一标识
    type: 处理映射关系的实体类类型

    resultMap 子标签
    <id> 处理主键和实体类中属性的映射关系
    <result> 处理普通字段和实体类中属性的映射关系
    <association> 处理多对一映射关系 ( 处理实体类型的属性 )
    <collection> 处理一对多的映射关系 ( 处理集合类型的属性 )

    子标签属性
    column: 设置映射关系中的字段名，必须是sql查询出的某个字段
    property: 设置映射关系中的属性的属性名，必须是处理的实体类类型中的属性名
-->
<resultMap id="EmpResultMap" type="Emp">
    <id property="empId" column="emp_id"/>
    <result property="empUsername" column="emp_username"/>
    <result property="empPassword" column="emp_password"/>
    <result property="age" column="age"/>
    <result property="gender" column="gender"/>
    <result property="deptId" column="dept_id"/>
</resultMap>
<!-- Emp queryEmpByEmpIdWithResultMap(@Param("empId") Integer empId); -->
<select id="queryEmpByEmpIdWithResultMap" resultMap="EmpResultMap">
    <!--
        使用 resultMap 之后可以正确的查询并封装数据
        emp=Emp{empId=1, empUsername='zhangsan', empPassword='123456', age=20, gender='男', deptId=1}
    -->
    select * from t_emp where emp_id = #{empId}
</select>
```

# resultMap 多对一映射处理 (实体类型属性)

## 多表查询，使用 级联方式映射属性和字段

```xml
<resultMap id="EmpResultMapJiLian" type="Emp2">
    <id property="empId" column="emp_id"/>
    <result property="empUsername" column="emp_username"/>
    <result property="empPassword" column="emp_password"/>
    <result property="age" column="age"/>
    <result property="gender" column="gender"/>
    <!-- 使用级联方式映射属性和字段 -->
    <result property="dept.deptId" column="dept_id"/>
    <result property="dept.deptName" column="dept_name"/>
</resultMap>
<!-- Emp2 queryEmpByEmpIdUseJiLian(@Param("empId") Integer empId); -->
<select id="queryEmpByEmpIdUseJiLian" resultMap="EmpResultMapJiLian">
    <!--
        使用resultMap级联方式映射属性和字段
        得到结果: emp=Emp2{
                    empId=1, empUsername='zhangsan', empPassword='123456', age=20, gender='男',
                    dept=Dept{deptId=1, deptName='开发'}
                    }
    -->
    select t_emp.*, t_dept.*
    from t_emp
    left JOIN t_dept
    ON t_emp.dept_id = t_dept.dept_id
    where t_emp.emp_id = #{empId}
</select>
```

## 多表查询，使用 association 方式映射实体对象

```xml
<resultMap id="EmpResultMap" type="Emp2">
    <id property="empId" column="emp_id"/>
    <result property="empUsername" column="emp_username"/>
    <result property="empPassword" column="emp_password"/>
    <result property="age" column="age"/>
    <result property="gender" column="gender"/>
    <!--
        <association>: 处理多对一的映射关系(处理实体类型的属性)
        property: 设置需要处理映射关系的属性的属性名
        javaType: 设置需要处理的属性的类型
    -->
    <association property="dept" javaType="Dept">
        <id property="deptId" column="dept_id"/>
        <result property="deptName" column="dept_name"/>
    </association>
</resultMap>

<!-- Emp2 queryEmpByEmpIdAssociation(@Param("empId") Integer empId); -->
<select id="queryEmpByEmpIdAssociation" resultMap="EmpResultMap">
    <!--
        使用 association 方式映射实体类型属性
        得到结果: emp=Emp2{
                    empId=1, empUsername='zhangsan', empPassword='123456', age=20, gender='男',
                    dept=Dept{deptId=1, deptName='开发'}
                    }
    -->
    SELECT t_emp.*, t_dept.*
    FROM t_emp
    LEFT JOIN t_dept
    ON t_emp.dept_id = t_dept.dept_id
    WHERE t_emp.emp_id = #{empId}
</select>
```

## 分步查询方案

> step1 创建 dept 表查询的 mapper 方法

// DeptMapper.xml
```xml
<resultMap id="DeptResultMap" type="Dept">
    <id property="deptId" column="dept_id"/>
    <result property="deptName" column="dept_name"/>
</resultMap>

<!-- Dept queryDeptByDeptId(@Param("deptId") String deptId); -->
<select id="queryDeptByDeptId" resultMap="DeptResultMap">
    select * from t_dept where dept_id = #{deptId}
</select>
```

> step2 使用分步查询，查询 emp 对象

```xml
<resultMap id="EmpResultMapStep" type="Emp2">
    <id property="empId" column="emp_id"/>
    <result property="empUsername" column="emp_username"/>
    <result property="empPassword" column="emp_password"/>
    <result property="age" column="age"/>
    <result property="gender" column="gender"/>
    <!--
        property: 设置需要处理映射关系的属性的属性名
        select: 设置分布查询 sql 的唯一标识 ( namespace + id )
        column: 将查询出的某个字段作为分步查询的 sql 的条件
        fetchType: 在开启了延迟加载的环境中，通过该属性设置当前的分布查询是否使用延迟加载
        fetchType="eager(立即加载)|lazy(延迟加载)"
    -->
    <association property="dept"
                 fetchType="eager"
                 select="com.example.mapper.DeptMapper.queryDeptByDeptId"
                 column="dept_id"/>
</resultMap>
<!-- Emp2 queryEmpByEmpIdUseStep(@Param("empId") Integer empId); -->
<select id="queryEmpByEmpIdUseStep" resultMap="EmpResultMapStep">
    select * from t_emp where emp_id = #{empId}
</select>
```










