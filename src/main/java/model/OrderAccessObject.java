package model;

import java.util.List;

import javax.persistence.*;

/**
 * Order luokan "Data Access Object", jonka avulla Order oliot tallennetaan tietokantaan.
 * Luokka käyttää JPA:n EntityManageria
 */
public class OrderAccessObject implements IOrderDao {

	@PersistenceContext
	protected EntityManagerFactory emf;
	protected EntityManager em;
	
	/**
	 * Konstruktori, jossa määritellään "EntityManagerFactory" ja "EntityManager"
	 */
	public OrderAccessObject() {
		
		emf = Persistence.createEntityManagerFactory("orderPersistenceUnit");
		em = emf.createEntityManager();
	}
	/**
	 * Metodi, jonka avulla order luokan olio tallenetaan tietokantaan.
	 * 
	 * @param order - olio, joka sisältää käyttäjän tilauksen tiedot
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
	 * Metodi, jonka avulla luetaan kaikki tilaukset tietokannasta
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
	 * Metodi, jolla voi päivittää yksittäisen tilauksen statuksen
	 * 
	 * @param order - Order olio, joka sisältää tilauksen tiedot
	 * @param status - päivittyvä status
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
