package com.mybatis.learn.decorator;

public abstract class ShapeDecorator implements Shape{
	protected Shape delegate;

	public ShapeDecorator(Shape delegate) {
		this.delegate = delegate;
	}

	@Override
	public void draw() {
		delegate.draw();
	}
}
