package com.mybatis.learn.javassist;

/**
 * 这是 JavasssistMain创建出来的类反编译的结果
 */
public class JavassistTest {
	private String prop = "MyName";

	public void getProp(String var1) {
		this.prop = var1;
	}

	public String getProp() {
		return this.prop;
	}

	public JavassistTest() {
		this.prop = "MyName";
	}

	public void execute() {
		System.out.println("execute():" + this.prop);
	}
}
