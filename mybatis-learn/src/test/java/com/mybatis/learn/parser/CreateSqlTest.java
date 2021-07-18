package com.mybatis.learn.parser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.values.ValuesStatement;

public class CreateSqlTest {
	public static void main(String[] args) throws JSQLParserException {
		String original = "select * from user";
		String[] items = {"name", "age", "email", "order_id", "sum"};
		String[] tables = {"user", "order"};
		String where = " user_id > 123";
		String[] groups = {" age "};
		String[] orders = {" name ", "age desc "};
		createSelect(original, items, tables, where, groups, orders);
	}

	private static void createSelect(String original, String[] columns, String[] tables, String where, String[] groups, String[] orders) throws JSQLParserException {
		Select select = (Select) CCJSqlParserUtil.parse(original);
		PlainSelect plain = ((PlainSelect) select.getSelectBody());
		createSelectColumns(plain, columns);
		select.setSelectBody(plain);

		System.out.println(select);
	}

	private static void createSelectColumns(PlainSelect plain, String... columns) throws JSQLParserException {
		plain.setSelectItems(null);
		for (String column : columns) {
			plain.addSelectItems(new SelectExpressionItem(CCJSqlParserUtil.parseExpression(column)));
		}
	}
}
