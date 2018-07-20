package chapter03.simple;

public class Skill {
	String name;
	public Skill(){}

	@Override
	public String toString() {
		return "Skill{" +
				"name='" + name + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
