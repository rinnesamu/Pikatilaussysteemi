package model;

import java.sql.*;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * 
 * @author Samu Rinne
 *
 */
public class FoodItemAccessObject implements IFoodItemDao {

	/**
	 * Access object for FoodItems
	 */

	private SessionFactory sessionFactory = null;
	// private final StandardServiceRegistry registry = new
	// StandardServiceRegistryBuilder().configure().build();

	public FoodItemAccessObject() {
		sessionFactory = util.HibernateUtil.buildSessionFactory();
		/*
		 * try { sessionFactory = new
		 * MetadataSources(registry).buildMetadata().buildSessionFactory(); }
		 * catch(Exception e) { System.out.println("Failed to create session factory");
		 * StandardServiceRegistryBuilder.destroy(registry); e.printStackTrace();
		 * System.exit(-1); }
		 */
	}

	/**
	 * Stores new food item to db
	 * 
	 * @param foodItem Food item that you want to store to your db
	 * @return true if successful, false if something went wrong
	 */

	@Override
	public boolean createFoodItem(FoodItem foodItem) {
		if (readFoodItemByName(foodItem.getName()) != null) {
			return false;
		}
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(foodItem);
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
	 * Gets all stored food items.
	 * 
	 * @return list of all food items stored in db
	 */

	@Override
	public FoodItem[] readFoodItems() {
		List<FoodItem> foodItems = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			foodItems = session.createQuery("From FoodItem").getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				throw e;
			}
		}
		FoodItem[] retrunFoodItems = new FoodItem[foodItems.size()];
		return (FoodItem[]) foodItems.toArray(retrunFoodItems);
	}

	/**
	 * Gets item from db with id
	 * 
	 * @param itemId Id of item you want to get
	 * @return FoodItem
	 */
	@Override
	public FoodItem readFoodItem(int itemId) {
		FoodItem foodItem = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			foodItem = session.get(FoodItem.class, itemId);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return foodItem;
	}

	/**
	 * Updates item in db with specific id.
	 * 
	 * @param index    index of item that you want to update
	 * @param foodItem New data that you want to store
	 * @return true if successful, false if not
	 */

	@Override
	public boolean updateFoodItem(int index, FoodItem foodItem) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(foodItem);
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
	 * Gets list of food items with specific category
	 * 
	 * @param category Category that you want to search for
	 * @return list of food items with specific category
	 */

	public FoodItem[] readFoodItemsCategory(String category) {
		List<FoodItem> foodItems = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			foodItems = session.createQuery("from FoodItem Where category = :categoryParam")
					.setParameter("categoryParam", category).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				throw e;
			}
		}
		FoodItem[] retrunFoodItems = new FoodItem[foodItems.size()];
		return (FoodItem[]) foodItems.toArray(retrunFoodItems);
	}

	/**
	 * Gets one FoodItem with name
	 * @param name Items name
	 * @return FoodItem
	 */

	public FoodItem readFoodItemByName(String name) {
		List<FoodItem> foodItems = null;
		FoodItem foodItem = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			foodItems = session.createQuery("from FoodItem Where name = :nameParam").setParameter("nameParam", name)
					.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		if (foodItems.size() != 0) {
			foodItem = foodItems.get(0);
		}
		return foodItem;
	}
	/*
	 * public FoodItem readFoodItemByName(String name) { FoodItem foodItem = null;
	 * Transaction transaction = null; try(Session session =
	 * sessionFactory.openSession()){ transaction = session.beginTransaction();
	 * //foodItem = session.get(FoodItem.class, name); foodItem =
	 * (FoodItem)session.createQuery("from FoodItem Where name = :nameParam").
	 * setParameter("nameParam", name).getResultList().get(0); transaction.commit();
	 * }catch (Exception e) { if (transaction != null) { transaction.rollback();
	 * throw e; } } return foodItem; }
	 */
	
	/**
	 * Gets all food items that include name in them
	 * 
	 * @param name Name that you want to search
	 * @return list of Food items that includes name
	 */
	public FoodItem[] readFoodItemsByName(String name) {
		List<FoodItem> foodItems = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			foodItems = session.createQuery("from FoodItem Where name like :nameParam")
					.setParameter("nameParam", '%' + name + '%').getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				throw e;
			}
		}
		FoodItem[] retrunFoodItems = new FoodItem[foodItems.size()];
		return (FoodItem[]) foodItems.toArray(retrunFoodItems);
	}

	/**
	 * Deletes item with specific id.
	 * 
	 * @param itemId id of the item that you want to delete
	 * @return true if successful, false otherwise
	 */
	@Override
	public boolean deleteFoodItem(int itemId) {
		if (readFoodItem(itemId) == null) {
			return false;
		}
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.delete(readFoodItem(itemId));
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		}
		return true;
	}

	public boolean deleteAllFoodItems() {
		FoodItem[] foodItems = readFoodItems();
		if (foodItems == null || foodItems.length == 0) {
			return true;
		} else {
			for (FoodItem f : foodItems) {
				this.deleteFoodItem(f.getItemId());
			}
			return true;
		}
	}

}
