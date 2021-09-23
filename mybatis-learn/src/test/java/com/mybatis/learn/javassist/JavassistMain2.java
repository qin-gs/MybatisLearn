package com.mybatis.learn.javassist;

import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * 为JavassistMain创建出来的类创建目标类实现动态代理功能
 */
public class JavassistMain2 {
	public static void main(String[] args) throws Exception {
		ProxyFactory factory = new ProxyFactory();
		// 指定父类
		factory.setSuperclass(JavassistTest.class);
		// 设置过滤器，判断哪些方法需要被拦截
		factory.setFilter(m -> m.getName().equals("execute"));
		// 拦截后处理
		factory.setHandler((Object self, Method thisMethod, Method proceed, Object[] args1) -> {
			System.out.println("前置处理");
			Object result = proceed.invoke(self, args1);
			System.out.println("执行结果 " + result);
			System.out.println("后置处理");
			return result;
		});

		// 创建代理类，代理对象
		Class<?> c = factory.createClass();
		JavassistTest test = ((JavassistTest) c.getDeclaredConstructor().newInstance());
		// 执行方法，会被拦截
		test.execute();
		System.out.println(test.getProp());
	}
}
