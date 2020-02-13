import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.FoodItem;
import model.FoodItemAccessObject;
import model.Ingredient;
import model.IngredientDao;

class IngredientDaoTest {
	
	
	private Ingredient ingredient;
	private IngredientDao ingredientDao = new IngredientDao();
	
	@AfterEach
	void afterEach() {
		ingredientDao.deleteAllIngredients();
	}

	@Test
	@DisplayName("Creating new ingredient")
	void testCreateIngredient() {
		ingredient = new Ingredient("Sipuli", true);
		assertEquals(true, ingredientDao.createIngredient(ingredient), "Couldn't create ingredeint!");
		assertEquals(1, ingredientDao.readIngredients().length, "Couldn't create new ingredient");
		ingredient = new Ingredient("Sipuli", true);
		assertEquals(false, ingredientDao.createIngredient(ingredient), "Couldn't create ingredeint!");
		assertEquals(1, ingredientDao.readIngredients().length, "Couldn't create new ingredient");
		ingredient = new Ingredient("Suolakurkku", true);
		assertEquals(true, ingredientDao.createIngredient(ingredient), "Couldn't create ingredeint!");
		assertEquals(2, ingredientDao.readIngredients().length, "Couldn't create new ingredient");
		
	}

	@Test
	@DisplayName("Readl all ingredients")
	void testReadIngredietns() {
		ingredient = new Ingredient("Sipuli", true);
		ingredientDao.createIngredient(ingredient);
		assertEquals("Sipuli", ingredientDao.readIngredients()[0].getName(), "Couldn't get all ingredients");
		ingredient = new Ingredient("Suolakurkku", true);
		ingredientDao.createIngredient(ingredient);
		assertEquals(2, ingredientDao.readIngredients().length, "Couldn't get all ingredients");
	}

	@Test
	@DisplayName("Update ingredient details")
	void testUpdateIngredient() {
		ingredient = new Ingredient("Sipuli", true);
		ingredientDao.createIngredient(ingredient);
		assertEquals("Sipuli", ingredientDao.readIngredientByName("Sipuli").getName(), "Couldn't add ingredient");
		assertEquals(true, ingredientDao.readIngredientByName("Sipuli").isRemovealbe(), "Couldn't add ingredient");
		ingredient.setName("Suolakurkku");
		ingredient.setRemovealbe(false);
		assertEquals(true, ingredientDao.updateIngredient(ingredientDao.readIngredientByName("Sipuli").getItemId(), ingredient));
		assertEquals(null, ingredientDao.readIngredientByName("Sipuli"), "Couldn't add ingredient");
		assertEquals("Suolakurkku", ingredientDao.readIngredientByName("Suolakurkku").getName(), "Couldn't update ingredient");
		assertEquals(false, ingredientDao.readIngredientByName("Suolakurkku").isRemovealbe(), "Couldn't update ingredient");
	}

	@Test
	@DisplayName("Delete item")
	void testDeleteIngredient() {
		ingredient = new Ingredient("Suolakurkku", true);
		assertEquals(true, ingredientDao.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals(true, ingredientDao.deleteIngredient(ingredientDao.readIngredientByName("Suolakurkku").getItemId()), "Couldn't delete item");
		assertEquals(0, ingredientDao.readIngredients().length, "Couldn't delete ingredient");
		ingredient = new Ingredient("Suolakurkku", true);
		assertEquals(true, ingredientDao.createIngredient(ingredient), "Couldn't add ingredient");
		ingredient = new Ingredient("Sipuli", true);
		assertEquals(true, ingredientDao.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals(true, ingredientDao.deleteIngredient(ingredientDao.readIngredientByName("Sipuli").getItemId()), "Couldn't delete item");
		assertEquals(1, ingredientDao.readIngredients().length, "Couldn't delete ingredient");
		assertEquals(false, ingredientDao.deleteIngredient(53234), "Couldn't delete item");
	}

	@Test
	@DisplayName("Read by name")
	void testReadIngredientByName() {
		ingredient = new Ingredient("Suolakurkku", true);
		assertEquals(true, ingredientDao.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals("Suolakurkku", ingredientDao.readIngredientByName("Suolakurkku").getName(), "Couldn't get item by name");
		ingredient = new Ingredient("Sipuli", true);
		assertEquals(true, ingredientDao.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals("Suolakurkku", ingredientDao.readIngredientByName("Suolakurkku").getName(), "Couldn't get item by name");
		assertEquals("Sipuli", ingredientDao.readIngredientByName("Sipuli").getName(), "Couldn't get item by name");
		assertEquals(null, ingredientDao.readIngredientByName("Eioo"), "Found something with wrong name");
	}

	@Test
	@DisplayName("Reading by id")
	void testReadIngredient() {
		ingredient = new Ingredient("Suolakurkku", true);
		assertEquals(true, ingredientDao.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals("Suolakurkku", ingredientDao.readIngredient(ingredientDao.readIngredientByName("Suolakurkku").getItemId()).getName(), "Couldn't read item by id");
		ingredient = new Ingredient("Sipuli", true);
		assertEquals(true, ingredientDao.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals("Suolakurkku", ingredientDao.readIngredient(ingredientDao.readIngredientByName("Suolakurkku").getItemId()).getName(), "Couldn't read item by id");
		assertEquals("Sipuli", ingredientDao.readIngredient(ingredientDao.readIngredientByName("Sipuli").getItemId()).getName(), "Couldn't read item by id");
		assertEquals(null, ingredientDao.readIngredient(234567876), "found item with wrong id");
	}
	
	@Test
	@DisplayName("Delete all ingredients")
	void testDeleteAllIngredients(){
		ingredient = new Ingredient("Sipuli", true);
		ingredientDao.createIngredient(ingredient);
		assertEquals(1, ingredientDao.readIngredients().length, "Couldn't add item");
		ingredient = new Ingredient("Suolakurkku", true);
		ingredientDao.createIngredient(ingredient);
		assertEquals(2, ingredientDao.readIngredients().length, "Couldn't add item");
		assertEquals(true, ingredientDao.deleteAllIngredients(), "Couldn't delete all items");
		assertEquals(0, ingredientDao.readIngredients().length, "Couldn't delete all items");
	}

}
