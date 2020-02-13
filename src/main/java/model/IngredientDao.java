package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class IngredientDao implements IIngredientDao {

	private SessionFactory sessionFactory = null;
	private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

	public IngredientDao() {
		// sessionFactory = util.HibernateUtil.buildSessionFactory();

		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Failed to create session factory");
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
			System.exit(-1);
		}

	}

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

	@Override
	public boolean updateIngredient(int id, Ingredient ingredient) {
		// TODO Auto-generated method stub
		return false;
	}

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
		return false;
	}

	@Override
	public Ingredient readIngredientByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

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
