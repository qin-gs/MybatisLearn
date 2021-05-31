package com.mybatis.learn.bean;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

public class MetaObjectTest {

	@Test
	public void test() {
		// 使用MetaObject操作对象属性
		// 操作子属性
		// 自动创建属性对象
		// 自动插座属性名，支持下划线转驼峰
		// 基于索引访问数组
		Object user = new Blog();
		Configuration configuration = new Configuration();
		MetaObject metaObject = configuration.newMetaObject(user);
		metaObject.setValue("title", "qqq");
		metaObject.setValue("author.name", "Name");
		System.out.println(metaObject.getValue("title"));
		System.out.println(metaObject.getValue("author.name"));

		System.out.println(metaObject.findProperty("author.phone_num", true));

	}
}
