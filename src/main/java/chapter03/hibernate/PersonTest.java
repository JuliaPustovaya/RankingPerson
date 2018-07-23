package chapter03.hibernate;

import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PersonTest {
	SessionFactory factory;

	@BeforeMethod
	public void setup() {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
		srBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}

	@AfterMethod
	public void shutdown() {
		factory.close();
	}

	@Test
	public void testSaveRanking() {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		populateRankingData();
		tx.commit();
		session.close();
	}

	private static void load(SessionFactory sessionFactory) {
		System.out.println("-- loading persons --");
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<Person> persons = session.createQuery("FROM Person").list();
		persons.forEach(System.out::println);
		session.close();
	}

	private Person findPerson(Session session, String name) {
		Query query = session.createQuery("from Person p where p.name=:name");
		query.setParameter("name", name);
		Person person = (Person) query.uniqueResult();
		return person;
	}

	private Skill findSkill(Session session, String name) {
		Query query = session.createQuery("from Skill s where s.name=:name");
		query.setParameter("name", name);
		Skill skill = (Skill) query.uniqueResult();
		return skill;
	}

	private Ranking findRanking(Session session, int ranking, String subject, String observer, String skill) {
		Query query = session.createQuery("from Ranking r where r.ranking=:ranking and r.subject.name=:subject and r.observer.name=:observer and r.skill.name=:skill");
		query.setParameter("ranking",ranking);
		query.setParameter("subject", subject);
		query.setParameter("observer", observer);
		query.setParameter("skill", skill);
		//		for (Ranking r : (List<Ranking>) query.list()) {
		//			System.out.println(r);
		//		}
		Ranking resultRanking = (Ranking) query.uniqueResult();
		assertNotNull(resultRanking, "Could not find matching ranking");
		return resultRanking;
	}

	private Person savePerson(Session session, String name) {
		Person person = findPerson(session, name);
		if (person == null) {
			person = new Person();
			person.setName(name);
			session.save(person);
		}
		return person;
	}

	private Skill saveSkill(Session session, String name) {
		Skill skill = findSkill(session, name);
		if (skill == null) {
			skill = new Skill();
			skill.setName(name);
			session.save(skill);
		}
		return skill;
	}

	private void populateRankingData() {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		createData(session, "J. C. Smell", "Gene Showrama", "Java", 6);
		createData(session, "J. C. Smell", "Scottball Most", "Java", 7);
		createData(session, "J. C. Smell", "Drew Lombardo", "Java", 8);
		createData(session, "J. C. Smell", "Jimm Kube", "Java", 4);
		createData(session, "J. C. Smell", "Jimm Kube", "C#", 3);
		createData(session, "Janet", "J. C. Smell", "Scala", 9);
		createData(session, "Janet", "J. C. Smell", "Scala", 22);
		tx.commit();
		session.close();
	}

	private void createData(Session session, String subject, String observer, String skill, int score) {
		Ranking ranking = new Ranking();
		ranking.setSubject(savePerson(session, subject));
		ranking.setObserver(savePerson(session, observer));
		ranking.setSkill(saveSkill(session, skill));
		ranking.setRanking(score);
		session.save(ranking);
	}

	private void removeData(Session session,int ranking,  String subject, String obser, String skill) {
		Ranking resultRanking = findRanking(session,ranking, subject, obser, skill);
		assertNotNull(resultRanking, "Ranking not found");
		session.delete(resultRanking);
	}

	@Test
	public void RemoveRanking() {
		populateRankingData();

		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		removeData(session,9, "Janet", "J. C. Smell", "Scala");
		tx.commit();
		session.close();
	}
}
