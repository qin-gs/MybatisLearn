package com.mybatis.learn.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;

import java.lang.reflect.Method;

public class JavassistMain {
	public static void main(String[] args) throws Exception {
		ClassPool cp = ClassPool.getDefault();
		// 要生成的类名
		CtClass clazz = cp.makeClass("com.mybatis.learn.javassist.JavassistTest");
		// 创建字段(字段类型，字段名，字段所属类)
		CtField field = new CtField(cp.get("java.lang.String"), "prop", clazz);
		// 设置字段修饰符
		field.setModifiers(Modifier.PRIVATE);

		// 设置 getter/setter 方法
		clazz.addMethod(CtNewMethod.setter("getProp", field));
		clazz.addMethod(CtNewMethod.getter("getProp", field));
		// 初始化字段
		clazz.addField(field, CtField.Initializer.constant("MyName"));

		StringBuffer body = null;
		// 创建构造方法
		CtConstructor constructor = new CtConstructor(new CtClass[]{}, clazz);
		// 设置否则方法的方法体
		body = new StringBuffer();
		body.append("{\n prop=\"MyName\";\n}");
		constructor.setBody(body.toString());
		clazz.addConstructor(constructor);

		// 创建一个方法(返回值类型，方法名称，方法参数列表，所属类)
		CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, clazz);
		// 设置方法修饰符
		ctMethod.setModifiers(Modifier.PUBLIC);
		// 设置方法体
		body = new StringBuffer();
		body.append("{\nSystem.out.println(\"execute():\" + this.prop);}");
		body.append("\n");
		ctMethod.setBody(body.toString());
		// 将方法添加到类中
		clazz.addMethod(ctMethod);
		// 将生成的类保存到指定目录
		clazz.writeFile("F:\\IdeaProjects\\MybatisLearn\\target\\classes\\");

		// 加载创建的类，并调用其中的方法
		Class<?> c = clazz.toClass();
		Object o = c.getDeclaredConstructor().newInstance();
		Method method = o.getClass().getMethod("execute");
		method.invoke(o);


	}
}
