package model;

public interface ICategoryDao {
	boolean createCategory(Category category);
	Category[] readCategories();
	Category readCategoryByName(String name);
	boolean updateCategory(Category category);
	boolean deleteAllCategories();
	boolean deleteCategoryByName(String name);
}
