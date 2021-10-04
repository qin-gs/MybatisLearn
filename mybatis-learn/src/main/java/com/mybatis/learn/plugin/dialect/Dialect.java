package com.mybatis.learn.plugin.dialect;

/**
 * 分页方言
 */
public interface Dialect {

    /**
     * 检测当前数据库是否支持分页
     */
    boolean supportPage();

    /**
     * 为sqk语句添加分页功能
     */
    String getPageSql(String sql, int offset, int limit);
}
