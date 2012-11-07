package org.redtroy.study.pattern.factory.simple.abst;

public class ConcreteClass extends AbstractClass {
	static {
		System.out.println("This is ConcreteClass");
	}

	public static void main(String args[]) {
		ConcreteClass.echo();
	}
}
