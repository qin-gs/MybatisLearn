package com.mybatis.learn.plugin.dialect;

public interface Dialect {
	boolean supportPage();

	String getPageSql(String sql, int offset, int limit);
}
