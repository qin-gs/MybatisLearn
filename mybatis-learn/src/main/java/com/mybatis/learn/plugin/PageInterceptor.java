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
import java.util.Properties;

// @Intercepts({@Signature(type = Executor.class, method = "query", args = {
// 		MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
// })})
public class PageInterceptor implements Interceptor {

	// 记录MappedStatement对象在参数列表中的索引位置
	private static int MAPPEDSTATEMENT_INDEX = 0;

	// 记录用户传入的实参对象在参数列表中的索引位置
	private static int PARAMETEROBJECT_INDEX = 1;

	// RowBounds类型参数在参数列表中的索引位置
	private static int ROWBOUNDS_INDEX = 2;

	// 数据库方言
	private Dialect dialect;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		final Object[] queryArgs = invocation.getArgs();
		final MappedStatement ms = (MappedStatement) queryArgs[MAPPEDSTATEMENT_INDEX];
		final Object parameter = queryArgs[PARAMETEROBJECT_INDEX];
		final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();
		BoundSql boundSql = ms.getBoundSql(parameter);
		final StringBuffer bufferSql = new StringBuffer(boundSql.getSql().trim());
		String sql = bufferSql.toString().trim();
		if (dialect.supportPage()) {
			sql = dialect.getPageSql(sql, offset, limit);
			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
		}
		queryArgs[MAPPEDSTATEMENT_INDEX] = createMappedStatement(ms, boundSql, sql);
		return invocation.proceed();
	}

	/**
	 * 处理完拦截到的sql语句之后，根据当前的sql语句创建新的MappedStatement对象，更新到Invocation对象记录的参数列表中
	 */
	private MappedStatement createMappedStatement(MappedStatement ms, BoundSql boundSql, String sql) {
		// BoundSql newBoundSql = createBoundSql(ms, boundSql, sql);
		// return createMappedStatement(ms, new BoundSqlSource(newBoundSql));
		return null;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		String dbName = properties.getProperty("dbName");
		String prefix = "dialect.";
		Map<String, String> result = new HashMap<>();
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			if (key != null && key.startsWith(prefix)) {
				result.put(key.substring(prefix.length()), ((String) entry.getValue()));
			}
		}
		String dialectClass = result.get(dbName);
		try {
			Dialect dialect = (Dialect) Class.forName(dialectClass).newInstance();
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
