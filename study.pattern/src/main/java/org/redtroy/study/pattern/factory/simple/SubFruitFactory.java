package org.redtroy.study.pattern.factory.simple;

public class SubFruitFactory extends FruitFactory {

	public static void main(String args[]) {
		SubFruitFactory sff = new SubFruitFactory();
		sff.produce("123");
		SubFruitFactory.produce("abc");
	}
}
