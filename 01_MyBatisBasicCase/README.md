
# MyBatis

1. MyBatis 是支持定制化 SQL，存储过程以及高级映射的优秀的持久层框架.
2. MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集.
3. MyBatis 可以使用简单的 XML 或注解用于配置和原始映射，将接口和Java的 POJO 映射成数据库中的记录.
4. MyBatis 是一个半自动的 ORM ( Object Relation Mapping ) 框架

MyBatis 项目地址 https://github.com/mybatis/mybatis-3

## 一个简单的案例

> mysql 不同版本注意事项

1. 驱动类 `driver-class-name`
    - `mysql5` 版本使用 `jdbc5` 驱动，驱动类使用: `com.mysql.jdbc.Driver`
    - `mysql8` 版本使用 `jdbc8` 驱动，驱动类使用: `com.mysql.cj.jdbc.Driver`
   
2. 连接地址 `url`
    - `mysql5` 版本使用的 `url`: `jdbc:mysql://localhost:3306/dbName`
    - `mysql8` 版本使用的 `url`: `jdbc:mysql://localhost:3306/dbName?serverTimezone=UTC`
    
注意: `mysql8` 不配置 `serverTimezone=UTC` 时，测试用例报如下错误

```text
java.sql.SQLException: The server time zone value 'ÖÐ¹ú±ê×¼Ê±¼ä' is unrecognized or
represents more
```

### step1 创建 maven 项目，导入 mybatis, mysql坐标

```xml
<dependencies>
    <!-- 导入 mybatis 核心包 -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.9</version>
    </dependency>
    <!-- 导入 junit 包 -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <!-- 导入 mysql 驱动 -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.0.31</version>
    </dependency>
</dependencies>
```


### step2 创建 mybatis 核心配置文件

mybatis 核心配置文件配置在 `src/main/resources` 目录下，习惯上命名为 `mybatis-config.xml`, 但并非是强制要求。mybatis 核心配置文件主要用于配置连接数据库的环境，以及 mybatis 的全局配置信息。

配置模板参考 https://mybatis.org/mybatis-3/zh_CN/getting-started.html

// mybatis-config.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

   <!--
       MyBatis核心配置文件中的标签必须要按照指定的顺序配置：
       properties?,settings?,typeAliases?,typeHandlers?,
       objectFactory?,objectWrapperFactory?,reflectorFactory?,
       plugins?,environments?,databaseIdProvider?,mappers?
   -->

   <!--
       导入 jdbc.properties 配置文件，后续可以在当前文件使用使用 ${key} 的方式读取配置数据
   -->
   <properties resource="jdbc.properties"/>

   <!--
       typeAliases 配置类型别名，即为某个具体的类型配置一个别名
       在 Mybatis 的范围内，可以使用该别名表示一个具体的类型
   -->
   <typeAliases>
      <!--
          typeAlias 标签配置某一个具体类型的别名
          type: 需要配置别名的具体类型
          alias: 设置某个具体类型的别名

          注意: 若不配置 alias 别名，则当前类型会有一个默认的别名，即类名且不区分大小写
      -->
      <!--        <typeAlias type="com.example.pojo.UserDTO" alias="abc"/>-->
      <typeAlias type="com.example.pojo.UserDTO"/>

      <!--
          package 标签配置某个包下所有类的别名，所有类都拥有一个默认的别名，即类名且不区分大小写
      -->
      <package name="com.example.pojo"/>
   </typeAliases>

   <!--
       environments 标签配置连接数据库的环境
       default 属性配置默认使用的环境 ( 取值为 environment 的 id 值 )
   -->
   <environments default="development">

      <!--
          environment 标签配置一个具体的连接数据库环境，environment 可以配置多个
          id 属性配置当前环境的唯一标识，不能重复
      -->
      <environment id="test">

         <!--
             transactionManager 标签配置事务管理器
             type 属性配置数据源类型
             type="JDBC|MANAGED"
             JDBC: 表示使用 JDBC 中原生的事务管理方式
             MANAGED: 表示被管理，例如 spring
         -->
         <transactionManager type="JDBC"/>

         <!--
             dataSource 标签配置数据源
             属性 type 配置数据源的类型
             type="POOLED|UNPOOLED|JNDI"
             POOLED: 表示使用数据库连接池
             UNPOOLED: 表示不使用数据库连接池
             JNDI: 表示使用上下文中的数据源
         -->
         <dataSource type="POOLED">
            <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
            <property name="url" value="jdbc:mysql://localhost:3306/ssm?serverTimezone=UTC"/>
            <property name="username" value="root"/>
            <property name="password" value="123456"/>
         </dataSource>
      </environment>

      <environment id="development">
         <transactionManager type="JDBC"/>
         <dataSource type="POOLED">
            <!--
                使用 ${key} 的方式读取 jdbc.properties 配置文件中的属性值
            -->
            <property name="driver" value="${jdbc.driver}"/>
            <property name="url" value="${jdbc.url}"/>
            <property name="username" value="${jdbc.username}"/>
            <property name="password" value="${jdbc.password}"/>
         </dataSource>
      </environment>

   </environments>

   <!--
       mappers 标签用于引入 mybatis 的映射文件
   -->
   <mappers>
      <!--
          mapper 标签用于引入单个 mybatis 映射文件
      -->
      <mapper resource="mappers/UserMapper.xml"/>

      <!--
          package 标签以包的方式批量引入 mybatis 映射文件，当时必须满足两个条件:
          1. mapper 接口和映射文件的包目录需要保持一致
          2. mapper 接口的名字和映射文件的名字必须一致

          resources 下创建多个目录可以使用 com/example/mapper 方式创建三个嵌套的目录
      -->
      <!--        <package name="com.example.mapper"/>-->
   </mappers>

</configuration>
```

### step3 创建 mapper 接口

mybatis 中只需要提供 mapper 接口，无需提供实现类

```java
package com.example.mapper;

public interface UserMapper {
    /** 插入用户数据，返回变更行数 */
    int insertUser();
}
```

### step4 创建 mybatis 的映射文件

ORM ( `Object Relation Mapping` ) 对象映射关系:

- 对象: Java 的实体对象
- 关系: 关系型数据库
- 映射: 二者之间的对应关系

| Java概念 | 数据库概念 |
|--------|-------|
| 类      | 表     |
| 属性     | 字段/列  |
| 对象     | 记录/行  |


> 映射文件规则

1. 表所对应的实体类的类名 + Mapper.xml，例如: `t_user` 表 映射的实体类为 `User` 所对应的映射文件为 `UserMapper.xml`
2. 一个映射文件对应一个实体类，对应一张表的操作
3. mybatis 映射文件用于编写 Sql，访问以及操作表中的数据
4. mybatis 映射文件存放在 `src/main/resources/mappers` 目录下，如果是按照包目录映射，则为 `src/main/resources/package/`

> mybatis 中可以面向接口操作数据，需要保证如下两点

1. mapper接口的全类名和映射文件 `<mapper>` 标签中的 `namespace` 属性值保持一致;
2. mapper接口中方法名和映射文件中编写 sql 标签的 id 属性值保持一致，例如: `<insert id="insertUser" >`

mapper映射文件模板参考 https://mybatis.org/mybatis-3/zh_CN/getting-started.html

对应 t_user 表的建表语句

```sql
CREATE TABLE `ssm`.`t_user`( `id` INT NOT NULL AUTO_INCREMENT, `username` TEXT, `password` TEXT, `age` INT, `gender` TEXT, `email` TEXT, PRIMARY KEY (`id`) ); 
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.mapper.UserMapper">
    <!--
        mapper 接口和映射文件要保证两个一致
        1. mapper 接口的全类名和映射文件 <mapper> 标签中的 namespace 一致
        2. mapper 接口中的方法名要和映射文件中的 sql 标签 ( 例如: <insert> ) 的 id 保持一致
    -->
    <insert id="insertUser">
        insert into t_user values(null, "admin", "123456", 20, "男", "admin@126.com")
    </insert>

   <!--
       注意: 如果未在 mybatis-config.xml 文件中为 UserDTO 类配置别名，
       则 resultType 中需要配置 UserDTO 的全类名，即: com.example.pojo.UserDTO
      
       select 查询的标签必须设置属性 resultType 或 resultMap，用于设置实体类和数据库表的映射关系
       resultType: 自动映射，用于属性名和表中字段名一致的情况;
       resultMap: 自定义映射，用于一对多，多对一 或 字段名和属性名不一致的情况
    -->
   <select id="queryUser" resultType="UserDTO">
      select * from t_user where id = 4
   </select>

</mapper>
```


### step5 验证功能

通过 `junit` 单元测试验证数据插入功能

```java
@Test
public void testInsert() throws IOException {
  // 1. 获取核心配置文件的输入流
  InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
  // 2. 获取 SqlSessionFactoryBuilder 对象
  SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
  // 3. 获取 SqlSessionFactory 对象
  SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
  // 4. 获取 SqlSession 会话对象
  // sqlSessionFactory.openSession() 获取的 sqlSession 会话对象默认不会自动提交事务
//        SqlSession sqlSession = sqlSessionFactory.openSession();
  // sqlSessionFactory.openSession(true) 获取的 sqlSession 会话对象默认自动提交事务
  SqlSession sqlSession = sqlSessionFactory.openSession(true);
  // 5. 获取 UserMapper 的代理对象
  UserMapper mapper = sqlSession.getMapper(UserMapper.class);
  // 6. 执行 UserMapper 中的方法执行对应的sql
  int result = mapper.insertUser();
  System.out.println("MyBatisCaseTest: testInsert(): result=" + result);
  // 7. 如果获取的 sqlSession 会话没有主动提交事务时，需要手动提交事务
//        sqlSession.commit();
  // 8. 关闭会话
  sqlSession.close();
}
```

执行完成后，数据库中增加了一条新纪录，操作成功。

### step6 加入 log4j 日志功能

新增 log4j 依赖

```xml
<!-- 导入 log4j 日志库 -->
<dependency>
   <groupId>log4j</groupId>
   <artifactId>log4j</artifactId>
   <version>1.2.17</version>
</dependency>
```

log4j 的配置文件名为 `log4j.xml`，存放在 `src/main/resources/` 目录下


## MyBatis 获取参数值的两种方式

mybatis 获取参数值的两种方式: `${}` 和 `#{}`

- `${}` 的本质是字符串拼接，若为字符串类型或日期类型的字段进行赋值时，需要手动加单引号;
- `#{}` 的本质是占位符赋值，此时为字符串类型或日期类型的字段进行赋值时，可以自动添加单引号;

```text
// 使用 #{} 为占位符赋值
select * from t_user where username = #{username}

// 使用 ${} 为字符串拼接，如果参数是字符串，需要手动添加单引号
select * from t_user where username = '${username}'
```

mybatis 中使用最多的是通过设置实体类参数 以及使用 `@Param` 注解指定参数名称的方式传递参数

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.mapper.UserParamMapper">

    <!-- mybatis 参数使用 -->

    <!-- UserDTO queryUserById(Integer id); -->
    <select id="queryUserById" resultType="UserDTO">
        <!--
            mybatis 获取参数有两种方式 ${} 和 #{}
            #{} 的本质是占位符赋值
            ${} 的本质是字符串拼接，需要注意如果 ${} 传入的是字符串或者日期类型，需要手动添加单引号

            若 mapper 接口方法的参数为单个字面量类型，此时可以通过 ${} 和 #{} 以任意的内容获取参数值 ( 例如: #{abc} )
        -->
        <!--        select * from t_user where id = #{id}-->
        select * from t_user where id = ${id}
    </select>

    <!-- UserDTO queryUserByUsername(String username); -->
    <select id="queryUserByUsername" resultType="UserDTO">
        <!--        select * from t_user where username = #{username}-->
        <!-- username 为字符串类型，使用 ${} 接收参数时，需要手动添加单引号 -->
        select * from t_user where username = '${username}'
    </select>

    <!-- UserDTO checkLogin(String username, String password) -->
    <select id="checkLogin" resultType="UserDTO">
        <!--
            Cause: org.apache.ibatis.binding.BindingException: Parameter 'username' not found.
            Available parameters are [arg1, arg0, param1, param2]

            若 mapper 接口方法的参数为多个的字面量类型，此时 mybatis 会将参数放入 map 集合中，以两种方式存储
            1. 以 arg0, arg1, .... 方式存储
            2. 以 param1, param2, ... 方式存储
        -->
        <!--        select * from t_user where username = #{arg0} and password = #{arg1}-->
        select * from t_user where username = '${param1}' and password = '${param2}'
    </select>

    <!-- UserDTO checkLoginByMap(Map<String, Object> map); -->
    <select id="checkLoginByMap" resultType="UserDTO">
        <!--
            若 mapper 接口方法的参数为map结合类型的实体
            只需要通过 #{} 或 ${} 访问 map 集合的键
        -->
        select * from t_user where username = #{username} and password = #{password}
    </select>

    <!-- Integer insertUser(UserDTO user); -->
    <select id="insertUser">
        <!--
            若 mapper 接口方法的参数为实体类型的参数，只需要通过 #{} 或 ${} 访问实体类中的属性名即可
            属性名规则: public String getUsername() {} 方法得到属性名 username，即实体中存在 getXxx() 方法即可

            注意: 返回值类型使用 Integer ，而不是 int，范围的值为 null 使用 int 接收会报错
        -->
        insert into t_user values(null, #{username}, #{password}, #{age}, #{gender}, #{email})
    </select>

    <!-- UserDTO checkLoginByParam(@Param("username") String username, @Param("password") String password); -->
    <select id="checkLoginByParam" resultType="UserDTO">
        <!--
            可以在 mapper 接口方法的参数上设置 @Param 注解，此时 mybatis 会将这些参数存储到 map 中，有两种存储方式
            1. 以 @Param 注解的 value 值为键存储
            2. 以 param1, param2, ... 为键存储
            使用 #{} 或 ${} 读取对应的key即可
        -->
        <!--        select * from t_user where username=#{param1} and password=#{param2}-->
        select * from t_user where username=#{username} and password=#{password}
    </select>

</mapper>
```


















## MyBatis 常用的查询方式

### 查询一个实体类对象

若 sql 语句查询的结果为多条时，一定不能以实体类类型作为方法的返回值，否则会抛出异常 ( `TooManyResultsException` )，若 sql 语句查询的结果为 1 条时，此时可以使用实体类类型或 list 集合类型作为方法的返回值。

```xml
<!-- UserDTO queryUserById(@Param("id") Integer id); -->
<select id="queryUserById" resultType="UserDTO">
  select * from t_user where id = #{id}
</select>

<!-- List<UserDTO> queryAllUser(); -->
<select id="queryAllUser" resultType="UserDTO">
  select * from t_user
</select>
```

### 查询单个数据

在 mybatis 中对于 Java 中常用的类型都设置了类型别名，详见文档 https://mybatis.org/mybatis-3/zh_CN/configuration.html

```text
别名             类型
_byte           byte
_int            int
_integer        int
_double         double
_float          float
_boolean        boolean
string          String
byte            Byte
int             Integer
integer         Integer
double          Double
float           Float
boolean         Boolean
date            Date
object          Object
date[]          Date[]
object[]        Object[]
map             Map
hashmap         HashMap
list            List
arraylist       ArrayList
```

查询表中的数据量

```xml
<!-- Integer queryUserCount(); -->
<select id="queryUserCount" resultType="integer">
  <!--
      查询的结果为 Integer 类型，mybatis 中 Integer 类型的类型别名为 int 或 integer
  -->
  select count(*) from t_user
</select>
```

### 查询数据封装到 Map 集合中

mybatis 中，除了将一条数据映射为一个实体对象外，还可以将一条数据封装到 map 集合中

若查询的结果为多条，则会封装成多个map对象，最终会将所有map封装到一个 list 集合中。

此外，可以使用 `@MapKey` 注解设置每条数据封装的map对象的key值，然后将所有的map对象封装到一个 map 集合中。

```xml
<!-- Map<String, Object> queryUserByIdToMap(@Param("id") Integer id); -->
<select id="queryUserByIdToMap" resultType="map">
  select * from t_user where id = #{id}
</select>

<!--
  List<Map<String, Object>> queryAllUserToMap();

  查询结果: list=[
          {password=123456, gender=男, id=1, age=20, email=admin@126.com, username=admin},
          {password=8888, gender=男, id=3, age=20, email=admin@126.com, username=zhangsan}
          ]
-->
<select id="queryAllUserToMap" resultType="map">
  select * from t_user
</select>

<!--
  @MapKey("id")
  Map<String, Map<String, Object>> queryAllUserToKeyMap();
  此时，map中的key为 @MapKey 注解中 value 值指定的字段

  查询结果: map={
          1={password=123456, gender=男, id=1, age=20, email=admin@126.com, username=admin},
          3={password=8888, gender=男, id=3, age=20, email=admin@126.com, username=zhangsan}
          }
-->
<select id="queryAllUserToKeyMap" resultType="map">
  select * from t_user
</select>
```


## 特殊SQL执行

### 模糊查询

```xml
<!-- List<UserDTO> queryUsernameLike(@Param("keyword") String keyword); -->
<select id="queryUsernameLike" resultType="UserDTO">
  <!-- 方案1: 使用 ${} 方式，拼接字符串 -->
  <!--        select * from t_user where username like '%${keyword}%'-->
  <!-- 方案2: 使用 concat 拼接字符串，参数使用 #{占位符} -->
  <!--        select * from t_user where username like concat('%', #{keyword}, '%')-->
  <!-- 方案3: 使用 "%"#{keyword}"%" 方式 -->
  select * from t_user where username like "%"#{keyword}"%"
</select>
```

### 批量删除

```xml
<!-- Integer batchDeleteByIds(@Param("ids") String ids); -->
<select id="batchDeleteByIds" resultType="int">
  <!--
      注意，在sql拼接时，如果使用 #{} 方式会默认为字符串添加 '' 单引号，例如下面的sql会得到
      delete from t_user where id in('1,2')   // 错误
      若使用 ${} 方式拼接，得到如下sql
      delete from t_user where id in(1,2)
  -->
  delete from t_user where id in(${ids})
</select>
```

### 动态设置表名

```xml
<!-- UserDTO queryUserByIdFromTable(@Param("tableName") String tableName, @Param("id") Integer id); -->
<select id="queryUserByIdFromTable" resultType="UserDTO">
  <!-- 注意，这里的表名通过参数动态的设置，如果使用 #{} 占位符方式，会使表名增加单引号 -->
  select * from ${tableName} where id = #{id}
</select>
```




