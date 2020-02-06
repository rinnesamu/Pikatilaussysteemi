package model;
import java.sql.*;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class FoodItemAccessObject implements IFoodItemDao {
	
	private SessionFactory sessionFactory = null;
	private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
	
	public FoodItemAccessObject() {
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
	public boolean createFoodItem(FoodItem foodItem) {
		Transaction transaction = null;
		try(Session session = sessionFactory.openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(foodItem);
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
	public FoodItem[] readFoodItems() {
		List<FoodItem> foodItems = null;
		Transaction transaction = null;  
		try(Session session = sessionFactory.openSession()){
			transaction = session.beginTransaction();
			foodItems = session.createQuery("From FoodItem").getResultList();
			transaction.commit();
		}catch(Exception e) {
			if (transaction != null) {
				transaction.rollback();
				throw e;
			}
		}
		FoodItem[] retrunFoodItems = new FoodItem[foodItems.size()];
		return (FoodItem[])foodItems.toArray(retrunFoodItems);
	}

	@Override
	public FoodItem readFoodItem(int itemId) {
		FoodItem foodItem = null;
		Transaction transaction = null;
		try(Session session = sessionFactory.openSession()){
			transaction = session.beginTransaction();
			foodItem = session.get(FoodItem.class, itemId);
			transaction.commit();
		}catch(Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return foodItem;
	}

	@Override
	public boolean updateFoodItem(int index, FoodItem foodItem) {
		Transaction transaction = null;
		try(Session session = sessionFactory.openSession()){
			transaction = session.beginTransaction();
			session.saveOrUpdate(foodItem);
			transaction.commit();
		}catch(Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		}
		return true;
	}
	
	
	public FoodItem[] readFoodItemsCategory(String category){
		List<FoodItem> foodItems = null;
		Transaction transaction = null;  
		try(Session session = sessionFactory.openSession()){
			transaction = session.beginTransaction();
			foodItems = session.createQuery("from FoodItem Where category = :categoryParam").setParameter("categoryParam", category).getResultList();
			transaction.commit();
		}catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				throw e;
			}
		}
		FoodItem[] retrunFoodItems = new FoodItem[foodItems.size()];
		return (FoodItem[])foodItems.toArray(retrunFoodItems);
	}

	@Override
	public boolean deleteFoodItem(int itemId) {
		if (readFoodItem(itemId) == null) {
			return false;
		}
		Transaction transaction = null;
		try(Session session = sessionFactory.openSession()){
			transaction = session.beginTransaction();
			session.delete(readFoodItem(itemId));
			transaction.commit();
		}catch(Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		}
		return true;
	}

}
