package com.mybatis.learn.decorator;

public class RedShapeDecorator extends ShapeDecorator {
	public RedShapeDecorator(Shape delegate) {
		super(delegate);
	}

	@Override
	public void draw() {
		delegate.draw();
		setRed(delegate);
	}

	private void setRed(Shape delegate) {
		System.out.println("color red");
	}
}
