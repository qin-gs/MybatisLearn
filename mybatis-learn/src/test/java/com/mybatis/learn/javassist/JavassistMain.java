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
		CtClass clazz = cp.makeClass("com.mybatis.learn.javassist.JavassistTest");
		StringBuffer body = null;
		CtField field = new CtField(cp.get("java.lang.String"), "prop", clazz);
		field.setModifiers(Modifier.PRIVATE);

		clazz.addMethod(CtNewMethod.setter("getProp", field));
		clazz.addMethod(CtNewMethod.getter("getProp", field));
		clazz.addField(field, CtField.Initializer.constant("MyName"));

		CtConstructor constructor = new CtConstructor(new CtClass[]{}, clazz);
		body = new StringBuffer();
		body.append("{\n prop=\"MyName\";\n}");
		constructor.setBody(body.toString());
		clazz.addConstructor(constructor);

		CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, clazz);
		ctMethod.setModifiers(Modifier.PUBLIC);
		body = new StringBuffer();
		body.append("{\nSystem.out.println(\"execute():\" + this.prop);}");
		body.append("\n");
		ctMethod.setBody(body.toString());
		clazz.addMethod(ctMethod);
		clazz.writeFile("F:\\IdeaProjects\\MybatisLearn\\target\\classes\\");

		Class<?> c = clazz.toClass();
		Object o = c.newInstance();
		Method method = o.getClass().getMethod("execute", new Class[]{});
		method.invoke(o, new Object[]{});


	}
}
