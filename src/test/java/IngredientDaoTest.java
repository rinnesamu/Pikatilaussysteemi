import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.FoodItem;
import model.FoodItemAccessObject;
import model.Ingredient;
import model.IngredientAccessObject;

class IngredientDaoTest {
	
	
	private Ingredient ingredient;
	private IngredientAccessObject ingredientAccessObject = new IngredientAccessObject();
	
	@AfterEach
	void afterEach() {
		ingredientAccessObject.deleteAllIngredients();
	}

	@Test
	@DisplayName("Creating new ingredient")
	void testCreateIngredient() {
		ingredient = new Ingredient("Sipuli", true);
		assertEquals(true, ingredientAccessObject.createIngredient(ingredient), "Couldn't create ingredeint!");
		assertEquals(1, ingredientAccessObject.readIngredients().length, "Couldn't create new ingredient");
		ingredient = new Ingredient("Sipuli", true);
		assertEquals(false, ingredientAccessObject.createIngredient(ingredient), "Couldn't create ingredeint!");
		assertEquals(1, ingredientAccessObject.readIngredients().length, "Couldn't create new ingredient");
		ingredient = new Ingredient("Suolakurkku", true);
		assertEquals(true, ingredientAccessObject.createIngredient(ingredient), "Couldn't create ingredeint!");
		assertEquals(2, ingredientAccessObject.readIngredients().length, "Couldn't create new ingredient");
		
	}

	@Test
	@DisplayName("Readl all ingredients")
	void testReadIngredietns() {
		ingredient = new Ingredient("Sipuli", true);
		ingredientAccessObject.createIngredient(ingredient);
		assertEquals("Sipuli", ingredientAccessObject.readIngredients()[0].getName(), "Couldn't get all ingredients");
		ingredient = new Ingredient("Suolakurkku", true);
		ingredientAccessObject.createIngredient(ingredient);
		assertEquals(2, ingredientAccessObject.readIngredients().length, "Couldn't get all ingredients");
	}

	@Test
	@DisplayName("Update ingredient details")
	void testUpdateIngredient() {
		ingredient = new Ingredient("Sipuli", true);
		ingredientAccessObject.createIngredient(ingredient);
		assertEquals("Sipuli", ingredientAccessObject.readIngredientByName("Sipuli").getName(), "Couldn't add ingredient");
		assertEquals(true, ingredientAccessObject.readIngredientByName("Sipuli").isRemoveable(), "Couldn't reaD ingredient");
		ingredient.setName("Suolakurkku");
		ingredient.setRemoveable(false);
		assertEquals(true, ingredientAccessObject.updateIngredient(ingredient));
		assertEquals(null, ingredientAccessObject.readIngredientByName("Sipuli"), "Couldn't update ingredient");
		assertEquals(1, ingredientAccessObject.readIngredients().length, "Accidentally added instead of update");
		assertEquals("Suolakurkku", ingredientAccessObject.readIngredientByName("Suolakurkku").getName(), "Couldn't update ingredient");
		assertEquals(false, ingredientAccessObject.readIngredientByName("Suolakurkku").isRemoveable(), "Couldn't update ingredient");
	}

	@Test
	@DisplayName("Delete item")
	void testDeleteIngredient() {
		ingredient = new Ingredient("Suolakurkku", true);
		assertEquals(true, ingredientAccessObject.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals(true, ingredientAccessObject.deleteIngredient(ingredientAccessObject.readIngredientByName("Suolakurkku").getItemId()), "Couldn't delete item");
		assertEquals(0, ingredientAccessObject.readIngredients().length, "Couldn't delete ingredient");
		ingredient = new Ingredient("Suolakurkku", true);
		assertEquals(true, ingredientAccessObject.createIngredient(ingredient), "Couldn't add ingredient");
		ingredient = new Ingredient("Sipuli", true);
		assertEquals(true, ingredientAccessObject.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals(true, ingredientAccessObject.deleteIngredient(ingredientAccessObject.readIngredientByName("Sipuli").getItemId()), "Couldn't delete item");
		assertEquals(1, ingredientAccessObject.readIngredients().length, "Couldn't delete ingredient");
		assertEquals(false, ingredientAccessObject.deleteIngredient(53234), "Couldn't delete item");
	}

	@Test
	@DisplayName("Read by name")
	void testReadIngredientByName() {
		ingredient = new Ingredient("Suolakurkku", true);
		assertEquals(true, ingredientAccessObject.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals("Suolakurkku", ingredientAccessObject.readIngredientByName("Suolakurkku").getName(), "Couldn't get item by name");
		ingredient = new Ingredient("Sipuli", true);
		assertEquals(true, ingredientAccessObject.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals("Suolakurkku", ingredientAccessObject.readIngredientByName("Suolakurkku").getName(), "Couldn't get item by name");
		assertEquals("Sipuli", ingredientAccessObject.readIngredientByName("Sipuli").getName(), "Couldn't get item by name");
		assertEquals(null, ingredientAccessObject.readIngredientByName("Eioo"), "Found something with wrong name");
	}

	@Test
	@DisplayName("Reading by id")
	void testReadIngredient() {
		ingredient = new Ingredient("Suolakurkku", true);
		assertEquals(true, ingredientAccessObject.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals("Suolakurkku", ingredientAccessObject.readIngredient(ingredientAccessObject.readIngredientByName("Suolakurkku").getItemId()).getName(), "Couldn't read item by id");
		ingredient = new Ingredient("Sipuli", true);
		assertEquals(true, ingredientAccessObject.createIngredient(ingredient), "Couldn't add ingredient");
		assertEquals("Suolakurkku", ingredientAccessObject.readIngredient(ingredientAccessObject.readIngredientByName("Suolakurkku").getItemId()).getName(), "Couldn't read item by id");
		assertEquals("Sipuli", ingredientAccessObject.readIngredient(ingredientAccessObject.readIngredientByName("Sipuli").getItemId()).getName(), "Couldn't read item by id");
		assertEquals(null, ingredientAccessObject.readIngredient(234567876), "found item with wrong id");
	}
	
	@Test
	@DisplayName("Delete all ingredients")
	void testDeleteAllIngredients(){
		ingredient = new Ingredient("Sipuli", true);
		ingredientAccessObject.createIngredient(ingredient);
		assertEquals(1, ingredientAccessObject.readIngredients().length, "Couldn't add item");
		ingredient = new Ingredient("Suolakurkku", true);
		ingredientAccessObject.createIngredient(ingredient);
		assertEquals(2, ingredientAccessObject.readIngredients().length, "Couldn't add item");
		assertEquals(true, ingredientAccessObject.deleteAllIngredients(), "Couldn't delete all items");
		assertEquals(0, ingredientAccessObject.readIngredients().length, "Couldn't delete all items");
	}

}
