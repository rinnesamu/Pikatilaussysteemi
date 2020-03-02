package model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * AccessObject for Category
 * @author Samu Rinne
 *
 */

public class CategoryAccessObject implements ICategoryDao {
	
	private SessionFactory sessionFactory = null;
	/**
	 * Constructor. Creates SessionFactory or gets one if it already exists.
	 */
	public CategoryAccessObject() {
		sessionFactory = util.HibernateUtil.buildSessionFactory();
	}

	/**
	 * Adds category to database
	 * @param category Category object that you want to add to database
	 * @return true if successful, false otherwise
	 */
	@Override
	public boolean createCategory(Category category) {
		if (readCategoryByName(category.getName()) != null) {
			return false;
		}
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(category);
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
	 * Reads all categories from database.
	 * @return Category[] list of categories
	 */
	@Override
	public Category[] readCategories() {
		List<Category> categories = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			categories = session.createQuery("From Category").getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				throw e;
			}
		}
		Category[] retrunCategories = new Category[categories.size()];
		return (Category[]) categories.toArray(retrunCategories);
	}

	/**
	 * Get category from database by name.
	 * @param name categorys name.
	 * @return category  Null if didn't find anything.
	 */
	@Override
	public Category readCategoryByName(String name) {
		List<Category> categories = null;
		Category category = null;
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			categories = session.createQuery("from Category Where name = :nameParam").setParameter("nameParam", name)
					.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		if (categories.size() != 0) {
			category = categories.get(0);
		}
		return category;
	}

	/**
	 * Updates categorys information to database
	 * @param category category that you want to update
	 * @return true id successful, false otherwise.
	 */
	@Override
	public boolean updateCategory(Category category) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(category);
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
	 * Deletes all categories
	 * @return true
	 */
	@Override
	public boolean deleteAllCategories() {
		Category[] categories = readCategories();
		if (categories == null || categories.length == 0) {
			return true;
		} else {
			for (Category c : categories) {
				deleteCategoryByName(c.getName());
			}
			return true;
		}
	}

	/**
	 * Deletes one category with name
	 * @param name Categorys name
	 * @return true if category was found and deleted, false otherwise.
	 */
	@Override
	public boolean deleteCategoryByName(String name) {
		System.out.println(readCategoryByName(name));
		if (readCategoryByName(name) == null) {
			return false;
		}
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.delete(readCategoryByName(name));
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		}
		return true;
	}

}
