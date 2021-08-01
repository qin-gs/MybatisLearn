package com.mybatis.learn.builder;

/**
 * 定义构造者构建产品对象各部分的行为
 */
public abstract class Builder {
	protected Product product = new Product();

	abstract void buildPartA();

	abstract void buildPartB();

	abstract void buildPartC();

	public Product getResult() {
		return product;
	}
}
