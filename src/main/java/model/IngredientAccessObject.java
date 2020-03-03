package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Access object for ingredients.
 * @author Samu Rinne
 *
 */
public class IngredientAccessObject implements IIngredientDao {

	private SessionFactory sessionFactory = null;

	/**
	 * Constructor. Creates SessionFactory or gets one if it already exists.
	 */
	public IngredientAccessObject() {
		sessionFactory = util.HibernateUtil.buildSessionFactory();


	}

	/**
	 * Adds new Ingredient to database
	 * @param ingredient Ingredient that you want to add
	 * @return true if successful, false otherwise.
	 */
	@Override
	public boolean createIngredient(Ingredient ingredient) {
		if (readIngredientByName(ingredient.getName()) != null) {
			return false;
		}
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(ingredient);
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
	 * Reads all ingredients from database.
	 * @return list of ingredients, null if nothing found.
	 */
	@Override
	public Ingredient[] readIngredients() {
		List<Ingredient> ingredients = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			ingredients = session.createQuery("From Ingredient").getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				throw e;
			}
		}
		Ingredient[] retrunIngredients = new Ingredient[ingredients.size()];
		return (Ingredient[]) ingredients.toArray(retrunIngredients);
	}

	/**
	 * Updates ingredients information to database
	 * @param ingredient Ingredient you want to update
	 * @return true if successful, false otherwise.
	 */
	@Override
	public boolean updateIngredient(Ingredient ingredient) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(ingredient);
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
	 * Deletes ingredient by id.
	 * @param id Ingredients id
	 * @return true if ingredient found and deleted, false otherwise.
	 */
	@Override
	public boolean deleteIngredient(int id) {
		if (readIngredient(id) == null) {
			return false;
		}
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.delete(readIngredient(id));
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
	 * Reads ingredient from database by its name.
	 * @param name Ingredients name
	 * @return ingredient if found. null otherwise.
	 */
	@Override
	public Ingredient readIngredientByName(String name) {
		Transaction transaction = null;
		Ingredient ingredient = null;
		List<Ingredient> ingredients = null;
		try (Session session = sessionFactory.openSession()){
			transaction = session.beginTransaction();
			ingredients = session.createQuery("From Ingredient Where name = :nameParam").setParameter("nameParam", name).getResultList();
			transaction.commit();
		}catch(Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		if (ingredients.size() != 0) {
			ingredient = ingredients.get(0);
		}
		return ingredient;
	}

	/**
	 * Reads ingredient from database by its id.
	 * @param itemId ingredients id
	 * @return ingredient if found. null otherwise.
	 */
	@Override
	public Ingredient readIngredient(int itemId) {
		Ingredient ingredient = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			ingredient = session.get(Ingredient.class, itemId);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return ingredient;
	}

	/**
	 * Deletes all ingredients form database.
	 * @return true
	 */
	@Override
	public boolean deleteAllIngredients() {
		Ingredient[] ingredients = readIngredients();
		if (ingredients == null || ingredients.length == 0) {
			return true;
		} else {
			for (Ingredient f : ingredients) {
				deleteIngredient(f.getItemId());
			}
			return true;
		}
	}

}
