package chapter03.hibernate;

import java.util.List;

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
	public void testSavePerson() {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Person person = new Person();
		person.setName("Demidovich");
		session.save(person);
		Person person1 = new Person();
		person1.setName("Pustovaya");
		session.save(person1);
		tx.commit();
		load(factory);
		session.close();
	}
	private static void load(SessionFactory sessionFactory) {
		System.out.println("-- loading persons --");
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<Person> persons = session.createQuery("FROM Person").list();
		persons.forEach(System.out::println);

	}

	public static void main(String[] args) {
		SessionFactory factory;
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
		srBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
		factory = configuration.buildSessionFactory(serviceRegistry);
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Person person = new Person();
		person.setName("Demidovich");
		session.save(person);
		Person person1 = new Person();
		person1.setName("Pustovaya");
		session.save(person1);
		tx.commit();
		load(factory);
		session.close();

	}
}
