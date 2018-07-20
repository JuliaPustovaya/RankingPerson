package chapter03.simple;

import org.junit.Test;

import chapter03.simple.Person;
import chapter03.simple.Ranking;
import chapter03.simple.Skill;

public class ModelTest {
@Test
	public void testModelCreation(){
	Person subject= new Person();
	subject.setName("Julia");

	Person observer= new Person();
	observer.setName("Anton");
	Skill skill = new Skill();
	skill.setName("Java");
	Ranking ranking = new Ranking();
	ranking.setSubject(subject);
	ranking.setObserver(observer);
	ranking.setSkill(skill);
	ranking.setRanking(8);
	System.out.println(ranking);

}
}
