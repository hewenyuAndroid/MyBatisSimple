package com.example.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 自定义一个 SQL 查询耗时的拦截器
 */
@Intercepts(
        @Signature(
                // 作用的目标类是 Executor
                type = Executor.class,
                // 作用的方法是 query 方法
                method = "query",
                // Executor 内部有多个重载的 query 方法，这里声明具体是哪个重载的方法
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
)
public class SQLCostTimeLogInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        long beginTime = System.currentTimeMillis();
        System.out.println("SQLLogInterceptor: before invoke method=" + method.getName() + ", beginTime=" + beginTime);
        Object result = invocation.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("SQLLogInterceptor: after invoke method=" + method.getName() + ", endTime=" + endTime + ", costTime=" + (endTime - beginTime) + "ms");
        return result;
    }

    @Override
    public Object plugin(Object target) {
        // 这里是创建动态代理对象执行的方法，target 为原始的 Executor 对象，返回动态代理对象
        // Executor: 在 SqlSession 构建 Executor 后，判断是否配置了插件，如果配置了插件会执行该方法
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 读取 自定义插件 配置的属性，在 插件对象 创建后执行 (XMLConfigBuilder解析后执行)
        System.out.println("SQLLogInterceptor: setProperties, " + properties);
        Interceptor.super.setProperties(properties);
    }
}
