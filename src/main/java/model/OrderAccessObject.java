package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * DataAccessObject for Order class. It is used for storing order information to a database.
 * 
 */
public class OrderAccessObject implements IOrderDao {
	
	private SessionFactory sessionFactory = null;
	/**
	 * Constructor where sessionFactory is defined
	 */
	public OrderAccessObject() {
		sessionFactory = util.HibernateUtil.buildSessionFactory();
		
	}
	/**
	 * Method for adding an order to the database
	 * 
	 * @param order order object that contains the information on the order
	 */
	@Override
	public boolean createOrder(Order order) {
		
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(order);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
			
		}
		return true;
		
	}
	/**
	 * Method for fetching an order from database based on its id
	 */
	@Override
	public Order readOrderById(int orderId) {
		Order order = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			order = session.get(Order.class, orderId);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return order;
	}
	
	/**
	 * Method for fetching all the orders from the database
	 */
	@Override
	public Order[] readOrders(){
		List<Order> orders = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			orders = session.createQuery("From Order").getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				throw e;
			}
		}
		Order[] returnOrders = new Order[orders.size()];
		return (Order[]) orders.toArray(returnOrders);
	}
	/**
	 * Method for updating the status of a single order
	 * 
	 * @param order Order object whose status is updated
	 */
	@Override
	public boolean updateOrderStatus(Order order) {
		
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(order);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		}
		return true;

	}

	/**
	 * Method for deleting an order from database
	 * 
	 * @param orderId the id of the order
	 */
	@Override
	public boolean deleteOrder(int orderId) {
		if (readOrderById(orderId) == null) {
			return false;
		}
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.delete(readOrderById(orderId));
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Method for deleting all orders from database
	 */
	@Override
	public boolean deleteAllOrders() {
		Order[] orders = readOrders();
		if (orders == null || orders.length == 0) {
			return true;
		} else {
			for (Order o : orders) {
				this.deleteOrder(o.getOrderId());
			}
			return true;
		}
	}
}
