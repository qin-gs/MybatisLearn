package com.mybatis.learn.builder;

public class Client {
	public static void main(String[] args) {
		// 创建构造者
		Builder builder = new ConcreteBuilder();
		// 将构造者传递给director
		Director director = new Director(builder);
		// director直接创建对象
		Product product = director.construct();
		product.show();
	}
}
