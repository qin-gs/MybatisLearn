package com.mybatis.learn.decorator;

/**
 * 装饰器模式(结构性) decorator
 * 向现有的对象中添加新的功能，同时不改变其解构
 */
public class Main {
	public static void main(String[] args) {
		Shape circle = new Circle();
		ShapeDecorator redCircle = new RedShapeDecorator(circle);

		circle.draw();

		redCircle.draw();
	}
}
