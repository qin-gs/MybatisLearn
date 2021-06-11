package com.mybatis.learn.builder;

import lombok.Data;

@Data
public class Product {
	private String partA;
	private String partB;
	private String partC;

	public void show() {
		System.out.println("Product{" +
				"partA='" + partA + '\'' +
				", partB='" + partB + '\'' +
				", partC='" + partC + '\'' +
				'}');
	}

}
