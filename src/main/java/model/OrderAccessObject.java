package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class OrderAccessObject implements IOrderDao {

	private SessionFactory sessionFactory = null;
	private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
	
	public OrderAccessObject() {
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch(Exception e) {
			System.out.println("Failed to create session factory");
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	
	@Override
	public boolean createOrder(Order order) {
		Transaction transaction = null;
		try(Session session = sessionFactory.openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(order);
			transaction.commit();	
		}catch(Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		}
		return true;
	}

	@Override
	public Order[] readOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order readOrder(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean updateOrder(Order order) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean deleteOrder(int orderId) {
		// TODO Auto-generated method stub
		return false;
	}

}
