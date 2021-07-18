package com.mybatis.learn.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes({Date.class})
public class DateStringTypeHandler extends BaseTypeHandler<Date> {
	/**
	 * 将时间戳字符串存入数据库
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, String.valueOf(parameter.getTime()));
	}

	/**
	 * 从数据库中取出时间戳转换成Date
	 */
	@Override
	public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return new Date(rs.getLong(columnName));
	}

	/**
	 * 从数据库中取出时间戳转换成Date
	 */
	@Override
	public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return new Date(rs.getLong(columnIndex));
	}

	/**
	 * 从数据库中取出时间戳转换成Date
	 */
	@Override
	public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getDate(columnIndex);
	}
}
