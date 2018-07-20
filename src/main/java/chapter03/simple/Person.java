package chapter03.simple;

public class Person {
	String name;

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				'}';
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person() {
	}
}
