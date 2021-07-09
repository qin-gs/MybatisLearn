package com.mybatis.learn.visitor;

public interface ComputerPart {

	void accept(ComputerPartVisitor visitor);

}
