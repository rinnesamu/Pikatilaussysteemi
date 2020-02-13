package model;

import java.util.List;

import javax.persistence.*;

/**
 * DataAccessObject for Order class. It is used for storing order information to a database.
 * The class uses JPA EntityManager
 * 
 */
public class OrderAccessObject implements IOrderDao {

	@PersistenceContext
	protected EntityManagerFactory emf;
	protected EntityManager em;
	
	/**
	 * Constructor where EntityMAnager and EntityManagerFactory are defined
	 */
	public OrderAccessObject() {
		
		emf = Persistence.createEntityManagerFactory("orderPersistenceUnit");
		em = emf.createEntityManager();
	}
	/**
	 * Method for adding an order to the database
	 * 
	 * @param order - order object that contains the information on the order
	 */
	@Override
	public boolean createOrder(Order order) {
		try {
			em.getTransaction().begin();
			em.persist(order);
			em.getTransaction().commit();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Method for fetching all the orders from the database
	 */
	@Override
	public List<Order> readOrders(){
		try {
			List<Order> allOrders = em.createQuery("SELECT o FROM Order o", Order.class)
			.getResultList();
			return allOrders;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Method for updating the status of a single order
	 * 
	 * @param order - Order object whose status is updated
	 * @param status - the status
	 */
	@Override
	public boolean updateOrderStatus(Order order, boolean status) {
		try {
			  em.getTransaction().begin();
			  order.setStatus(status);
			  em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Method for fetching orders from the database based on its status
	 */
	@Override
	public List<Order> readOrdersByStatus(boolean status){
		try {
			List<Order> allOrders = em.createQuery("SELECT o FROM Order o WHERE o.status=:status", Order.class)
			.setParameter("status", status)
			.getResultList();
			return allOrders;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
