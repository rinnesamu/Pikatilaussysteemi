package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Class for creating and returning SessionFactory for access objects.
 * @author Samu
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory = null;
	private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

	/**
	 * Constructor. Creates new SessionFactory if it isn't created yet. If it already exists, just returns it.
	 * @return sessionFactory SessionFactory
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
	 * Closes SessionFactroy.
	 */
	public static void closeSessionFactory() {
		sessionFactory.close();
	}

}
