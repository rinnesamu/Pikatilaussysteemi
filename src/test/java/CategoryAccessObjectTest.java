import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Category;
import model.CategoryAccessObject;

class CategoryAccessObjectTest {
	
	private Category category;
	private CategoryAccessObject categoryDao = new CategoryAccessObject();
	
	@AfterEach
	void after() {
		categoryDao.deleteAllCategories();
	}

	@Test
	@DisplayName("Create category")
	void testCreateCategory() {
		category = new Category("Juomat");
		assertEquals(true, categoryDao.createCategory(category), "Couldn't add category to db");
		assertEquals(1, categoryDao.readCategories().length, "Couldn't add category to db");
		category = new Category("Juomat");
		assertEquals(false, categoryDao.createCategory(category), "Added same category twice");
		assertEquals(1, categoryDao.readCategories().length, "Length is wrong");
	}

	@Test
	@DisplayName("Read all categories")
	void testReadCategories() {
		category = new Category("Juomat");
		assertEquals(true, categoryDao.createCategory(category), "Couldn't add category to db");
		assertEquals(1, categoryDao.readCategories().length, "Couldn't read all items");
		category = new Category("Hampurilaiset");
		assertEquals(true, categoryDao.createCategory(category), "Couldn't add category to db");
		assertEquals(1, categoryDao.readCategories().length, "Couldn't read all items");
		assertEquals("Juomat", categoryDao.readCategories()[0].getName(), "Couldn't read all items");
		assertEquals("Hampurilaiset", categoryDao.readCategories()[1].getName(), "Couldn't read all items");

	}

	@Test
	@DisplayName("Read one category")
	void testReadCategoryByName() {
		category = new Category("Juomat");
		assertEquals(null, categoryDao.readCategoryByName("Juomat"), "Found category that does not exist");
		assertEquals(true, categoryDao.createCategory(category), "Couldn't add category to db");
		assertEquals("Juomat", categoryDao.readCategoryByName("Juomat").getName(), "Couldn't find existing category");
		assertEquals(null, categoryDao.readCategoryByName("eioo"), "Found category that does not exist");
		
	}

	@Test
	@DisplayName("Update category")
	void testUpdateCategory() {
		category = new Category("Juomat");
		assertEquals(true, categoryDao.createCategory(category), "Couldn't add category to db");
		assertEquals("Juomat", categoryDao.readCategoryByName("Juomat").getName(), "Couldn't find existing category");
		category = categoryDao.readCategoryByName("Juomat");
		category.setName("Isot juomat");
		assertEquals(true, categoryDao.updateCategory(category), "Couldn't update category");
		assertEquals(null, categoryDao.readCategoryByName("Juomat"), "Couldn't find existing category");
		assertEquals("Isot juomat", categoryDao.readCategoryByName("Isot juomat").getName(), "Couldn't find existing category");
		
	}

	@Test
	@DisplayName("Delete all")
	void testDeleteAllCategories() {
		category = new Category("Juomat");
		assertEquals(true, categoryDao.createCategory(category), "Couldn't add category to db");
		assertEquals(1, categoryDao.readCategories().length, "Couldn't read all");
		assertEquals(true, categoryDao.deleteAllCategories(), "Couldn't delete all");
		assertEquals(0, categoryDao.readCategories().length, "Couldn't delete all");
		assertEquals(true, categoryDao.createCategory(category), "Couldn't add category to db");
		category = new Category("Hampurilaiset");
		assertEquals(true, categoryDao.createCategory(category), "Couldn't add category to db");
		assertEquals(2, categoryDao.readCategories().length, "Couldn't read all");
		assertEquals(true, categoryDao.deleteAllCategories(), "Couldn't delete all");
		assertEquals(0, categoryDao.readCategories().length, "Couldn't delete all");
		
	}

	@Test
	@DisplayName("Delete by name")
	void testDeleteCategoryByName() {
		assertEquals(true, categoryDao.deleteCategoryByName("Juomat"), "Deleted non existing category");
		category = new Category("Juomat");
		assertEquals(true, categoryDao.createCategory(category), "Couldn't add category to db");
		assertEquals("Juomat", categoryDao.readCategoryByName("Juomat"), "Couldnt read one");
		assertEquals(true, categoryDao.deleteCategoryByName("Juomat"), "Couldn't delete one");
		assertEquals(false, categoryDao.deleteCategoryByName("Juomat"), "Deleted same category twice");
		
	}

}
