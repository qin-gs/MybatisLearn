package com.mybatis.learn.plugin.dialect;

/**
 * oracle数据库的分页功能
 */
public class OracleDialect implements Dialect {

    @Override
    public boolean supportPage() {
        return true;
    }

    /**
     * 拼接sql完成分页功能
     * <pre>
     *  select * from (
     *      select u.*, rownum rn from (select * from table) u where rownum <=20
     *  ) where rn > 10
     * </pre>
     */
    @Override
    public String getPageSql(String sql, int offset, int limit) {
        sql = sql.trim();
        // for update 会对数据库加锁
        boolean hasForUpdate = false;
        String forUpdatePart = " for update";
        if (sql.toLowerCase().endsWith(forUpdatePart)) {
            // 将当前语句的 for update 片段删除
            sql = sql.substring(0, sql.length() - forUpdatePart.length());
            hasForUpdate = true;
        }
        // 记录拼接完成支持分页的sql语句
        StringBuffer result = new StringBuffer(sql.length() + 100);
        if (offset > 0) {
            result.append("select * from ( select row_.*, rownum rownum_ from ( ");
        } else {
            result.append("select * from ( ");
        }
        result.append(sql);
        if (offset > 0) {
            String endOffset = offset + "+" + limit;
            result.append(" ) row_ ) where rownum_ <= ").append(endOffset).append(" and rownum_ > ").append(offset);
        } else {
            result.append(" ) where rownum <= ").append(limit);
        }
        // 将 for update 重新拼接上
        if (hasForUpdate) {
            result.append(forUpdatePart);
        }
        return result.toString();
    }
}
