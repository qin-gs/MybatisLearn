package com.mybatis.learn.builder;

/**
 * 提供两类方法
 * 1. 构造方法
 * 2. 获取构造好的产品
 */
public class ConcreteBuilder extends Builder {
	@Override
	void buildPartA() {
		product.setPartA("partA ");
	}

	@Override
	void buildPartB() {
		product.setPartB("partB ");
	}

	@Override
	void buildPartC() {
		product.setPartC("partC");
	}
}
