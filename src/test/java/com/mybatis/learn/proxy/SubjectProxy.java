package com.mybatis.learn.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SubjectProxy implements InvocationHandler {
	private Object target; // 真正的业务对象，需要传进来

	public SubjectProxy(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 预处理
		System.out.println("代理类 预处理");
		Object result = method.invoke(target, args);
		// 后处理
		System.out.println("代理类 后处理");
		return result;
	}

	public Object getProxy() {
		// 创建代理对象
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), // 加载动态生成的代理类的加载器
				target.getClass().getInterfaces(), // 业务类实现的接口
				this); // InvocationHandler对象
	}

	public static void main(String[] args) {
		Subject real = new RealSubject();
		SubjectProxy proxy = new SubjectProxy(real);
		// 获取代理对象
		Subject sub = (Subject) proxy.getProxy();
		// 调用代理对象的方法
		sub.operate();
	}


}

