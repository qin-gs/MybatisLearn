package com.mybatis.learn.sql;

import org.apache.ibatis.jdbc.SQL;

public class GenerateSql {
	public String selectSql(String gender) {
		return new SQL() {{
			SELECT("id", "name");
			FROM("user u");
			FROM("account a");
			if (gender != null) {
				WHERE("i.gender = #{gender}");
			}
			INNER_JOIN("department d on d.id=u.id");
			WHERE("u.id = a.id");
			OR();
			WHERE("pu.last_name like ?");
			GROUP_BY("u.id");
			HAVING("u.last_name like ?");
			ORDER_BY("u.full_name");
		}}.toString();
	}
}
