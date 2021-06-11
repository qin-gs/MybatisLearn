package com.mybatis.learn.builder;

public abstract class Builder {
	protected Product product = new Product();

	abstract void buildPartA();

	abstract void buildPartB();

	abstract void buildPartC();

	public Product getResult() {
		return product;
	}
}
