package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CategoryAccessObject implements ICategoryDAO {
	
	private SessionFactory sessionFactory = null;
	
	public CategoryAccessObject() {
		sessionFactory = util.HibernateUtil.buildSessionFactory();
	}

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
