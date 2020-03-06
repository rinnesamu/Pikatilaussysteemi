package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
/**
 * Creates and handles SessionFactory
 * @author Samu
 *
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory = null;
	private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

	/**
	 * Constructor, creates new SessionFactory if none exists. If one is already made, just returns it. 
	 * @return sessionFactroy SessionFactory
	 */
	public static SessionFactory buildSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			} catch (Exception e) {
				System.out.println("Failed to create session factory");
				StandardServiceRegistryBuilder.destroy(registry);
				e.printStackTrace();
				System.exit(-1);
			}

		} else {
		}
		return sessionFactory;
	}

	/**
	 * Closes SessionFactory
	 */
	public static void closeSessionFactory() {
		sessionFactory.close();
	}

}
