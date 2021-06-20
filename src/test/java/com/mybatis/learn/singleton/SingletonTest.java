package com.mybatis.learn.singleton;

public class SingletonTest {

}

class SingleTon1 {

	// 禁止指令重排序
	private volatile static SingleTon1 instance = null;

	private SingleTon1() {

	}

	public static SingleTon1 getInstance() {
		if (instance == null) {
			synchronized (SingleTon1.class) {
				if (instance == null) {
					instance = new SingleTon1();
				}
			}
		}
		return instance;
	}
}

class Singleton2 {
	private static class SingletonHolder {
		public static Singleton2 instance = new Singleton2();
	}

	/**
	 * 第一次访问及港台字段时，会触发类加载
	 */
	public static Singleton2 getInstance() {
		return SingletonHolder.instance;
	}
}