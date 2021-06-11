package com.mybatis.learn.builder;

public class ConcreteBulder extends Builder {
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
