package com.mybatis.learn.visitor;

/**
 * 访问者设计模式
 * 将数据结构 和 数据操作 分离
 * 应用场景：对一个对象结果中的对象进行很多不同且不相干的操作，而不像让这些操作放到对应的类中，因此将操作抽取出来封装成一个类
 * 缺点：如果新增元素会导致
 */
public class Main {
	public static void main(String[] args) {
		ComputerPart computer = new Computer();
		computer.accept(new ComputerPartVisitorImpl());
	}
}
