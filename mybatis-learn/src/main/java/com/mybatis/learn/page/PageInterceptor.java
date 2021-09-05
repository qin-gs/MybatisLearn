package com.mybatis.learn.page;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 分页拦截器
 * 拦截 {@link StatementHandler#prepare} 方法
 */
// @Component
@Intercepts(
        @Signature(type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class})
)
public class PageInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 检测当前是否满足分页条件
        StatementHandler target = (StatementHandler) invocation.getTarget();
        // 包含 sql语句，参数
        BoundSql boundSql = target.getBoundSql();
        // 如果有一个参数直接就是参数，如果有多个参数是Map
        Object parameterObject = boundSql.getParameterObject();
        // 查询并设置总行数
        Page page = null;
        if (parameterObject instanceof Page) {
            page = ((Page) parameterObject);
        } else if (parameterObject instanceof Map) {
            page = (Page) ((Map<?, ?>) parameterObject).values()
                    .stream()
                    .filter(x -> x instanceof Page)
                    .findFirst()
                    .orElse(null);
        }
        if (Objects.isNull(page)) {
            return invocation.proceed();
        }
        page.setTotal(getCount(invocation));
        // 修改原有sql
        String newSql = String.format("%s limit %s offset %s", boundSql.getSql(), page.getSize(), page.getOffset());
        SystemMetaObject.forObject(boundSql).setValue("sql", newSql);
        return invocation.proceed();
        // 默认 也就是{@code Invocation#proceed}中的逻辑
        // return invocation.getMethod().invoke(invocation.getMethod(), invocation.getArgs());
    }

    private int getCount(Invocation invocation) throws SQLException {
        int count = 0;
        StatementHandler target = (StatementHandler) invocation.getTarget();
        String countSql = String.format("select count(*) from (%s) as _page", target.getBoundSql());
        Connection connection = (Connection) invocation.getArgs()[0];
        PreparedStatement preparedStatement = connection.prepareStatement(countSql);
        // 给sql设置参数
        target.getParameterHandler().setParameters(preparedStatement);
        // 或者使用
        // target.parameterize(preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        resultSet.close();
        preparedStatement.close();
        return count;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
