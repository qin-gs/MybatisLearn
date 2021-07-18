package com.mybatis.learn.parser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.values.ValuesStatement;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.List;

public class ParserTest {
	public static void main(String[] args) throws JSQLParserException {
		String select = "select name, id from user where id > 12 group by age order by gender";
		String insert = "insert into user(id, name, age) values ('2', 'qqq', '12')";
		// parseSql(select);
		parseSql(insert);

	}

	public static void parseSql(String sql) throws JSQLParserException {
		Statement parse = CCJSqlParserUtil.parse(sql);
		System.out.println(sql);
		if (parse instanceof Select) {
			parseSelect((Select) parse);
		} else if (parse instanceof Insert) {
			parseInsert(((Insert) parse));
		}

	}

	private static void parseInsert(Insert insert) {
		System.out.print("\n列名: ");
		List<Column> columns = insert.getColumns();
		if (columns != null) {
			columns.stream().map(x -> x + " ").forEach(System.out::print);
		}

		String name = insert.getTable().getName();
		System.out.print("\n表名: " + name);

		List<Expression> expressions = ((ExpressionList) insert.getItemsList()).getExpressions();
		System.out.print("\n列值: ");
		expressions.stream().map(x -> x + " ").forEach(System.out::print);

		System.out.print("\n");

	}

	private static void parseSelect(Select select) {
		System.out.print("\n列名: ");
		PlainSelect plain = (PlainSelect) select.getSelectBody();
		List<SelectItem> selectItems = plain.getSelectItems();
		if (selectItems != null) {
			selectItems.stream().map(selectItem -> selectItem + "   ").forEach(System.out::print);
		}

		System.out.print("\n表名: ");
		TablesNamesFinder finder = new TablesNamesFinder();
		List<String> tableList = finder.getTableList(select);
		tableList.forEach(System.out::print);

		Expression expression = plain.getWhere();
		System.out.print("\nwhere 部分: " + expression);

		GroupByElement groupBy = plain.getGroupBy();
		System.out.print("\ngroup by 列名: " + groupBy);

		List<OrderByElement> orderByElements = plain.getOrderByElements();
		System.out.print("\norder 部分: ");
		orderByElements.stream().map(x -> x + " ").forEach(System.out::print);
	}



}
