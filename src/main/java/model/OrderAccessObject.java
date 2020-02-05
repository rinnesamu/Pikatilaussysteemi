package model;

import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Order luokan "Data Access Object", jonka avulla Order oliot tallennetaan tietokantaan
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
	
	/*
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
	}*/

}
