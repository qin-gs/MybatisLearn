package com.mybatis.learn.decorator;

public class Rectangle implements Shape {

	@Override
	public void draw() {
		System.out.println("shape rectangle");
	}
}
