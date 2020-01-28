package model;
import java.sql.*;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodItem readFoodItem(int itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateFoodItem(FoodItem foodItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteFoodItem(int itemId) {
		// TODO Auto-generated method stub
		return false;
	}

}
