package com.mybatis.learn.plugin.dialect;

public class OracleDialect implements Dialect {
	@Override
	public boolean supportPage() {
		return true;
	}

	/**
	 *
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Override
	public String getPageSql(String sql, int offset, int limit) {
		sql = sql.trim();
		boolean hasForUpdate = false;
		String forUpdatePart = " for update";
		if (sql.toLowerCase().endsWith(forUpdatePart)) {
			// 将当前语句的 for update 片段删除
			sql = sql.substring(0, sql.length() - forUpdatePart.length());
			hasForUpdate = true;
		}
		StringBuffer result = new StringBuffer(sql.length() + 100);
		if (offset > 0) {
			result.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			result.append("select * from ( ");
		}
		result.append(sql);
		if (offset > 0) {
			String endOffset = offset + "+" + limit;
			result.append(") row_ ) where rownum_ <= ").append(endOffset).append(" and rownum_ > ").append(offset);
		} else {
			result.append(" ) where rownum <= ").append(limit);
		}
		if (hasForUpdate) {
			result.append(forUpdatePart);
		}
		return result.toString();
	}
}
