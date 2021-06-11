package com.mybatis.learn.builder;

public class Client {
	public static void main(String[] args) {
		Builder builder = new ConcreteBulder();
		Director director = new Director(builder);
		Product product = director.construct();
		product.show();
	}
}
