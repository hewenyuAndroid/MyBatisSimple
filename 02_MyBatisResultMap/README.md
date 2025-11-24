

myBatis resultMap 使用

# resultMap 处理属性和字段的映射关系

解决 数据库字段名 (下划线) 和 pojo 属性名 (驼峰) 不一致，导致的数据映射问题

1. sql 查询时，使用别名的方式，例如: `select emp_id as empId, emp_name as empName from emp where emp_id = 1`;
2. 在 `mapper-config.xml` 中配置全局配置，自动将下划线转换成驼峰
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--
        通过 mybatis 的全局配置
        解决数据库字段 (emp_id) 和pojo实体属性 (empId) 映射问题
    -->
    <settings>
        <!--
            将 数据库字段的下划线自动映射成驼峰，例如: emp_id -> empId
        -->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
    ...
</configuration>
```
3. 使用 `resultMap` 完成自定义映射

查询员工信息，员工对象所有字段均为基础类型数据，且与表中字段一一对应

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

查询员工信息，员工信息中包含 `Dept dept` 属性，该属性通过员工信息表中的 `emp.dept_id` 关联 `t_dept` 表查询得到

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


# resultMap 一对多映射处理 (List集合属性)

查询部门信息，部门信息下包含 `List<Emp> list` 列表属性，通过 `dept.dept_id` 查询员工表得到该部门的所有员工信息

## collection

```xml
<resultMap id="DeptResultMapCollection" type="Dept2">
    <id property="deptId" column="dept_id"/>
    <result property="deptName" column="dept_name"/>
    <!--
        property: 设置需要处理映射关系的属性的属性名
        ofType: 设置 collection 标签所处理的集合属性中存储的类型
    -->
    <collection property="list" ofType="Emp">
        <id property="empId" column="emp_id"/>
        <result property="empUsername" column="emp_username"/>
        <result property="empPassword" column="emp_password"/>
        <result property="age" column="age"/>
        <result property="gender" column="gender"/>
        <result property="deptId" column="dept_id"/>
    </collection>
</resultMap>
<!-- Dept2 queryDeptEmpByDeptId(@Param("deptId") Integer deptId); -->
<select id="queryDeptEmpByDeptId" resultMap="DeptResultMapCollection">
    <!--
        使用 collection 集合方式完成一对多属性的映射
        查询结果: dept=Dept2{
                deptId=1, deptName='开发',
                list=[
                    Emp{empId=1, empUsername='zhangsan', empPassword='123456', age=20, gender='男', deptId=1},
                    Emp{empId=2, empUsername='lisi', empPassword='123456', age=21, gender='男', deptId=1}
                    ]
                }
    -->
    select t_dept.*, t_emp.*
    from t_dept
    left join t_emp
    on t_dept.dept_id = t_emp.dept_id
    where t_dept.dept_id = #{deptId}
</select>
```


## 分布查询方案

分步查询可以实现延迟加载，但是必须在核心配置文件中设置全局配置信息:

- `lazyLoadingEnable`: 延迟加载的全局开关，当开启时，所有的关联对象都会延迟加载;
- `aggressiveLazyLoading`: 当开启时，任何方法的调用都会加载该对象的所有属性，否则每个属性会按需加载。

> step1 创建根据 dept_id 查询所有员工信息的方法

// EmpMapper.xml
```xml
<resultMap id="EmpResultMap" type="Emp">
    <id property="empId" column="emp_id"/>
    <result property="empUsername" column="emp_username"/>
    <result property="empPassword" column="emp_password"/>
    <result property="age" column="age"/>
    <result property="gender" column="gender"/>
    <result property="deptId" column="dept_id"/>
</resultMap>

<!-- List<Emp> queryEmpListByDeptId(@Param("deptId") Integer deptId); -->
<select id="queryEmpListByDeptId" resultMap="EmpResultMap">
    select * from t_emp where dept_id = #{deptId}
</select>
```

> step2 通过 dept_id 查询当前部门下的所有员工

// Dept2Mapper.xml
```xml
<resultMap id="DeptResultMapStep" type="Dept2">
    <id property="deptId" column="dept_id"/>
    <result property="deptName" column="dept_name"/>
    <association property="list"
                 select="com.example.mapper.EmpMapper.queryEmpListByDeptId"
                 column="dept_id"
                 fetchType="eager"
    />
</resultMap>
<!-- Dept2 queryDeptEmpByDeptIdUseStep(@Param("deptId") Integer deptId); -->
<select id="queryDeptEmpByDeptIdUseStep" resultMap="DeptResultMapStep">
    <!--
        分布查询
        得到结果: dept=Dept2{
                    deptId=1, deptName='开发',
                    list=[
                        Emp{empId=1, empUsername='zhangsan', empPassword='123456', age=20, gender='男', deptId=1},
                        Emp{empId=2, empUsername='lisi', empPassword='123456', age=21, gender='男', deptId=1}
                        ]
                    }
    -->
    select * from t_dept where dept_id = #{deptId}
</select>
```










