package com.mybatis.learn.visitor;

public class Computer implements ComputerPart {

	ComputerPart[] parts;

	public Computer() {
		this.parts = new ComputerPart[]{new Mouse(), new Keyboard(), new Monitor(),};
	}

	@Override
	public void accept(ComputerPartVisitor visitor) {
		for (ComputerPart part : this.parts) {
			part.accept(visitor);
		}
		visitor.visit(this);
	}
}
