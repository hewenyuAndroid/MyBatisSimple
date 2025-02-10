

动态SQL

# 使用 `<if text="">` 标签增加判断

```xml
<!-- List<Emp> queryEmpByConditionUseIf(@Param("emp") Emp emp); -->
<select id="queryEmpByConditionUseIf" resultType="Emp">
    <!--
        通过 1=1 恒成立的条件解决如下问题:
        1. 解决第一个条件不成立时，后续替补的条件 and 前缀问题 select * from t_emp where and age = 10
        2. 解决所有条件都不成立时的 where 关键字问题 select * from t_emp where
    -->
    select * from t_emp where 1=1
    <if test="emp.empUsername != null and emp.empUsername != ''">
        and emp_username = #{emp.empUsername}
    </if>
    <if test="emp.age != null and emp.age != ''">
        and age = #{emp.age}
    </if>
</select>
```

# 使用 `<where>` 标签

`<where>`  标签和 `<if>` 一般结合使用

```xml
<!-- List<Emp> queryEmpByConditionUseWhere(@Param("emp") Emp emp); -->
<select id="queryEmpByConditionUseWhere" resultType="Emp">
    <!--
        使用 <where> 标签
        1. 若 <where> 标签内部有条件成立，则会添加 where 关键字，反之不会添加。
        2. <where> 标签会将标签中内容 前 多余的 and 去掉，如果是内容 后 有多余的 and 时，无法去除。
        3. 若 <where> 标签内部没有任何条件成立时，则 <where> 标签没有任何作用。
    -->

    select * from t_emp
    <where>
        <if test="emp.empUsername != null and emp.empUsername != ''">
            and emp_username = #{emp.empUsername}
        </if>
        <if test="emp.age != null and emp.age != ''">
            and age = #{emp.age}
        </if>
    </where>
</select>
```

# 使用 `<trim>` 标签

`<trim>` 标签可以灵活的处理 SQL 中的前缀，后缀，前缀去除和后缀去除

```xml
<!-- List<Emp> queryEmpByConditionUseTrim(@Param("emp") Emp emp); -->
<select id="queryEmpByConditionUseTrim" resultType="Emp">
    <!--
        <trim> 标签用于增加或删除sql中的内容
        prefix: 在 <trim> 标签中的内容结果前面添加前缀
        prefixOverrides: 在 <trim> 标签中的内容结果的前面去掉某些内容
        suffix: 在 <trim> 标签中的内容结果后面添加后缀
        suffixOverrides: 在 <trim> 标签中的内容结果的后面去掉某些内容

        prefix="where" prefixOverrides="and" 增加 where 前缀，去除头部的 and 字符
    -->
    select * from t_emp
    <trim prefix="where" prefixOverrides="and">
        <if test="emp.empUsername != null and emp.empUsername != ''">
            and emp_username = #{emp.empUsername}
        </if>
        <if test="emp.age != null and emp.age != ''">
            and age = #{emp.age}
        </if>
    </trim>
</select>
```


# 使用 `<choose>`、`<when>`、`<otherwise>` 组合

`<choose>`、`<when>`、`<otherwise>` 标签组合类似于 java 中的 `switch case default` 语句功能

```xml
<!-- List<Emp> queryEmpByConditionUseChoose(@Param("emp") Emp emp); -->
<select id="queryEmpByConditionUseChoose" resultType="Emp">
    <!--
        使用 choose when otherwise 标签类似于java的 switch 语句
        when 标签最少需要一个，otherwise 标签最多只有一个
    -->
    select * from t_emp
    <where>
        <choose>
            <when test="emp.empUsername != null and emp.empUsername != ''">
                and emp_username = #{emp.empUsername}
            </when>
            <when test="emp.age != null and emp.age != ''">
                and age = #{emp.age}
            </when>
            <otherwise>
                and gender = '男'
            </otherwise>
        </choose>
    </where>
</select>
```

# `foreach` 标签

> 批量插入数据

```xml
<!-- void batchInsertEmp(@Param("emps") List<Emp> emps); -->
<insert id="batchInsertEmp">
    <!--
        insert into t_emp values (?,?..),(?,?..),(?,?..)
        <foreach>
        collection: 设置要循环的数组或集合
        item: 循环执行的每一项数据名称
        separator: 设置每次循环数据之间的分隔符
        open: 循环的所有内容以什么开始
        close: 循环的所有内容以什么结束
    -->
    insert into t_emp values
    <foreach collection="emps" item="emp" separator=",">
        (null, #{emp.empUsername}, #{emp.empPassword}, #{emp.age}, #{emp.gender}, #{emp.deptId})
    </foreach>
</insert>
```

> 批量删除数据

```xml
<!-- void batchDeleteByIds(@Param("ids") List<Integer> ids); -->
<delete id="batchDeleteByIds">
    <!--
        delete from t_emp where emp_id in (1, 2)
        
        open + close 为拼接内容添加首尾字符
    -->
    delete from t_emp where emp_id in
    <foreach collection="ids" item="id" separator="," open="(" close=")">
        #{id}
    </foreach>
</delete>
```

# sql 片段

sql 片段可以记录一段sql，在需要使用的地方使用 include 标签引入

```xml
<sql id="empColumns">
    emp_id, emp_username, emp_password, age, gender, dept_id
</sql>

<!-- Emp checkLogin(@Param("username") String username, @Param("password") String password); -->
<select id="checkLogin" resultType="Emp">
    <!--
        嵌入 sql 片段后
        select emp_id, emp_username, emp_password, age, gender, dept_id from t_emp where emp_username=? and emp_password=?
    -->
    select <include refid="empColumns"/> from t_emp where emp_username=#{username} and emp_password=#{password}
</select>
```













