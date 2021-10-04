package com.mybatis.learn.plugin;

import com.mybatis.learn.plugin.dialect.Dialect;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 拦截方法 Executor#query(MappedStatement, Object, RowBounds, ResultHandler)
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
})})
public class PageInterceptor implements Interceptor {

    // 记录MappedStatement对象在参数列表中的索引位置
    private static final int MAPPED_STATEMENT_INDEX = 0;

    // 记录用户传入的实参对象在参数列表中的索引位置
    private static final int PARAMETER_OBJECT_INDEX = 1;

    // RowBounds类型参数在参数列表中的索引位置
    private static final int ROWBOUNDS_INDEX = 2;

    // 数据库方言
    private Dialect dialect;

    /**
     * 具体的拦截逻辑
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取参数列表 [MappedStatement, Object, RowBounds, ResultHandler]
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        // 用户传入的实参
        final Object parameter = queryArgs[PARAMETER_OBJECT_INDEX];
        final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
        // 拿到分页参数
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        // 这里是包含占位符的sql语句
        BoundSql boundSql = ms.getBoundSql(parameter);
        final StringBuffer bufferSql = new StringBuffer(boundSql.getSql().trim());
        // TODO 格式化sql语句
        String sql = bufferSql.toString().trim();
        if (dialect.supportPage()) {
            // 拼接成包含分页功能的sql
            sql = dialect.getPageSql(sql, offset, limit);
            // 因为sql语句已经有分页功能了，所以将RowBounds参数设为空
            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
        }
        // 根据新的sql语句创建新的MappedStatement对象，并更新Invocation对象的参数列表
        queryArgs[MAPPED_STATEMENT_INDEX] = createMappedStatement(ms, boundSql, sql);
        return invocation.proceed();
    }

    /**
     * 处理完拦截到的sql语句之后，根据当前的sql语句创建新的MappedStatement对象，
     */
    private MappedStatement createMappedStatement(MappedStatement ms, BoundSql boundSql, String sql) {
        // 为拼接好的sql创建新的BoundSql对象
        // BoundSql newBoundSql = createBoundSql(ms, boundSql, sql);
        // 为拼接好的sql创建新的MappedStatement对象
        // return createMappedStatement(ms, new BoundSqlSource(newBoundSql));
        return null;
    }

    /**
     * 返回Executor的代理对象
     */
    @Override
    public Object plugin(Object target) {
        // 解析当前类的注解信息，确定要拦截的方法，创建代理对象
        return Plugin.wrap(target, this);
    }

    /**
     * 根据mybatis-config.xml中的配置完成初始化
     */
    @Override
    public void setProperties(Properties properties) {
        String dbName = properties.getProperty("dbName");
        String prefix = "dialect.";
        Map<String, String> result = new HashMap<>();
        properties.forEach((key, value) -> {
            if (Objects.nonNull(key) && key.toString().startsWith(prefix)) {
                result.put(key.toString().substring(prefix.length()), ((String) value));
            }
        });

        // 获取当前使用数据库的Dialect对象
        String dialectClass = result.get(dbName);
        try {
            // 通过反射创建一个Dialect对象
            Dialect dialect = (Dialect) Class.forName(dialectClass).getDeclaredConstructor().newInstance();
            this.setDialect(dialect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }
}
