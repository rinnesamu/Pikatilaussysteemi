package model;

import org.hibernate.SessionFactory;

public class CategoryAccessObject implements ICategoryDAO {
	
	private SessionFactory sessionFactory = null;
	
	public CategoryAccessObject() {
		sessionFactory = util.HibernateUtil.buildSessionFactory();
	}

	@Override
	public boolean createCategory(Category category) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Category[] readCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category readCategoryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateCategory(Category category) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAllCategories() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCategoryByName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

}
