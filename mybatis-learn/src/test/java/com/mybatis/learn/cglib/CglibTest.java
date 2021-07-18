package com.mybatis.learn.cglib;

public class CglibTest {
	public String method(String str) {
		System.out.println(str);
		return "cglib method: " + str;
	}

	public static void main(String[] args) {
		CglibProxy proxy = new CglibProxy();
		CglibTest proxyImpl = (CglibTest) proxy.getProxy(CglibTest.class);
		String result = proxyImpl.method("test");
		System.out.println(result);
	}
}
