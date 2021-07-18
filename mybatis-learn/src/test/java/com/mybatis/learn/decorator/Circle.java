package com.mybatis.learn.decorator;

public class Circle implements Shape {
	@Override
	public void draw() {
		System.out.println("shape circle");
	}
}
