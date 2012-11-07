package org.redtroy.study.pattern.factory.simple.abst;

public abstract class AbstractClass {
	static {
		System.out.println("This is AbstractClass");
	}

	public static void echo() {
		System.out.println("Echoing AbstractClass");
	}

	public static void main(String args[]) {
		ConcreteClass.echo();
	}

}
