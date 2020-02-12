import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.FoodItem;
import model.FoodItemAccessObject;

/**
 * 
 * @author Samu Rinne
 *
 */


class FoodItemAccessObjectTest {
	
	private FoodItem foodItem;
	private FoodItemAccessObject foodItemDao = new FoodItemAccessObject();
	
	/*@BeforeEach
	void init() {
		foodItemDao = new FoodItemAccessObject(); // drops table and creates new.-
	}
	*/
	@AfterEach
	void after() {
		foodItemDao.deleteAllFoodItems();
	}

	@Test
	@DisplayName("Adding food item to database")
	void testCreateFoodItem() {
		foodItem = new FoodItem("kokis", 2.5, true);
		assertEquals(true, foodItemDao.createFoodItem(foodItem), "couldn't create food item");
		foodItem = new FoodItem("Iso Kokis", 3.5, false);
		assertEquals(true, foodItemDao.createFoodItem(foodItem), "couldn't create food item");
		assertEquals(2, foodItemDao.readFoodItems().length, "createFoodItem does not work!");
		
	}

	@Test
	@DisplayName("Test reading all food elelments from db")
	void testReadFoodItems() {
		foodItem = new FoodItem("kokis", 2.5, true);
		foodItemDao.createFoodItem(foodItem);
		assertEquals(1, foodItemDao.readFoodItems().length, "read all food items does not work!(wrong size)");
		assertEquals("kokis", foodItemDao.readFoodItems()[0].getName(), "read all food items does not work! (wrong name)");
		foodItem = new FoodItem("Iso kokis", 3.5, false);
		foodItemDao.createFoodItem(foodItem);
		assertEquals(2, foodItemDao.readFoodItems().length, "read all food items does not work!(wrong size)");
		assertEquals("kokis", foodItemDao.readFoodItems()[0].getName(), "read all food items does not work! (wrong name)");
		assertEquals("Iso kokis", foodItemDao.readFoodItems()[1].getName(), "read all food items does not work! (wrong name)");
		
	}

	@Disabled("Broken after util.HibernateUtil. Cant drop and create new table anymore, so ids wont reset")
	// TODO Fix this
	@Test
	@DisplayName("Reading only one item")
	void testReadFoodItem() {
		foodItem = new FoodItem("kokis", 2.5, true);
		foodItemDao.createFoodItem(foodItem);
		foodItem = new FoodItem("Iso kokis", 3.5, false);
		foodItemDao.createFoodItem(foodItem);
		assertEquals("kokis", foodItemDao.readFoodItem(1).getName(), "Couldn't read first item from list!");
		assertEquals("Iso kokis", foodItemDao.readFoodItem(2).getName(), "Couldn't read last item from list!");
		assertEquals(null, foodItemDao.readFoodItem(3), "Found item with wrong id!");
	}

	@Test
	@DisplayName("Testing updating food item")
	void testUpdateFoodItem() {
		foodItem = new FoodItem("kokis", 2.5, "juomat", true);
		foodItemDao.createFoodItem(foodItem);
		foodItem.setCategory("None");
		foodItem.setName("Iso Kokis");
		foodItem.setPrice(3.5);
		foodItem.setPath("Joku/Kiva/Path");
		foodItem.setInMenu(false);
		assertEquals(true, foodItemDao.updateFoodItem(0, foodItem), "couldn't update fooditem");
		assertEquals("Iso Kokis", foodItemDao.readFoodItems()[0].getName(), "Fooditems name did not update correctyl!");
		assertEquals(3.5, foodItemDao.readFoodItems()[0].getPrice(), "Fooditems price did not update correctyl!");
		assertEquals(false, foodItemDao.readFoodItems()[0].isInMenu(), "Fooditems menu acitivity did not update correctyl!");
		assertEquals("None", foodItemDao.readFoodItems()[0].getCategory(), "Fooditems category did not update correctyl!");
		assertEquals("Joku/Kiva/Path", foodItem.getPath(), "Path didn't update correctly");
	}
	
	@Test
	@DisplayName("Getting item with certain category")
	void testGetByCategory() {
		foodItem = new FoodItem("kokis", 2.5, "juomat", true);
		foodItemDao.createFoodItem(foodItem);
		foodItem = new FoodItem("Iso kokis", 2.5, "juomat", true);
		foodItemDao.createFoodItem(foodItem);
		foodItem = new FoodItem("hamppari", 2.5, "hampurilaiset", true);
		foodItemDao.createFoodItem(foodItem);
		foodItem = new FoodItem("ranskalaiset", 2.5, true);
		foodItemDao.createFoodItem(foodItem);
		assertEquals(2, foodItemDao.readFoodItemsCategory("juomat").length, "Couldn't find all items from category juomat");
		assertEquals(1, foodItemDao.readFoodItemsCategory("hampurilaiset").length, "Couldn't find all items from category hampurilaiset");
		assertEquals(1, foodItemDao.readFoodItemsCategory("None").length, "Couldn't find all items from category None");
		assertEquals(0, foodItemDao.readFoodItemsCategory("Isot Juomat").length, "Found something with incorrect category");
	}
	
	@Disabled("Broken after util.HibernateUtil. Cant drop and create new table anymore, so ids wont reset")
	// TODO Fix this
	@Test
	@DisplayName("Deleting from database")
	void testDeleteFoodItem() {
		foodItem = new FoodItem("kokis", 2.5, true);
		foodItemDao.createFoodItem(foodItem);
		foodItem = new FoodItem("Iso kokis", 3.5, false);
		foodItemDao.createFoodItem(foodItem);
		assertEquals(2, foodItemDao.readFoodItems().length, "read all food items does not work!(wrong size)");
		assertEquals(true, foodItemDao.deleteFoodItem(1), "Deleting item doesn't return true!");
		assertEquals(1, foodItemDao.readFoodItems().length, "Deleting first item doesn't work");
		assertEquals("Iso kokis", foodItemDao.readFoodItems()[0].getName(), "Deleted worng object!");
		foodItemDao.deleteFoodItem(2);
		assertEquals(0, foodItemDao.readFoodItems().length, "Deleting only item doesn't work");
		foodItem = new FoodItem("kokis", 2.5, true);
		foodItemDao.createFoodItem(foodItem);
		foodItem = new FoodItem("Iso kokis", 3.5, false);
		foodItemDao.createFoodItem(foodItem);
		assertEquals(2, foodItemDao.readFoodItems().length, "read all food items does not work!(wrong size)");
		foodItemDao.deleteFoodItem(4);
		assertEquals(1, foodItemDao.readFoodItems().length, "Deleting second and last item doesn't work");
		assertEquals(false, foodItemDao.deleteFoodItem(1), "Deleting non existent id return true!");
		assertEquals(1, foodItemDao.readFoodItems().length, "Deleted with wrong id!");
		
	}
	
	 @Test
	 @DisplayName("Getting item from db with name")
	 void testGetByName() {
		foodItem = new FoodItem("kokis", 2.5, true);
		foodItemDao.createFoodItem(foodItem);
		foodItem = new FoodItem("Iso kokis", 3.5, false);
		foodItemDao.createFoodItem(foodItem);
		foodItem = new FoodItem("hamppari", 2.5, true);
		foodItemDao.createFoodItem(foodItem);
		assertEquals(2, foodItemDao.readFoodItemsByName("kokis").length, "Read by name is not working");
		assertEquals(1, foodItemDao.readFoodItemsByName("hamppari").length, "Read by name is not working");
		assertEquals(0, foodItemDao.readFoodItemsByName("Bic Mac").length, "Read by name is not working");
	 }
	 
	 //Used to search by id, but since db wont reset before each test, ids wont reset
	 @Test
	 @DisplayName("Getting and setting ingredient list from db")
	 void testIngredients() {
		 String[] ingredients = {"Sämpylä", "Pihvi", "juusto", "ketsuppi"};
		 foodItem = new FoodItem("juustohampurilainen", 1, true);
		 foodItem.setIngredients(ingredients);
		 foodItemDao.createFoodItem(foodItem);
		 assertEquals(4, foodItemDao.readFoodItemByName("juustohampurilainen").getIngredientsAsList().length, "Couldn't get or set ingredients to db");
		 assertEquals("Sämpylä", foodItemDao.readFoodItemByName("juustohampurilainen").getIngredientsAsList()[0], "Couldn't get or set ingredients to db");
		 assertEquals("Pihvi", foodItemDao.readFoodItemByName("juustohampurilainen").getIngredientsAsList()[1], "Couldn't get or set ingredients to db");
		 assertEquals("juusto", foodItemDao.readFoodItemByName("juustohampurilainen").getIngredientsAsList()[2], "Couldn't get or set ingredients to db");
		 assertEquals("ketsuppi", foodItemDao.readFoodItemByName("juustohampurilainen").getIngredientsAsList()[3], "Couldn't get or set ingredients to db");
	 }
	 
	 @Test
	 @DisplayName("Read from db with name")
	 void testReadByName() {
		 foodItem = new FoodItem("Superhampurilainen", 5, true);
		 foodItemDao.createFoodItem(foodItem);
		 assertEquals(null, foodItemDao.readFoodItemByName("Somethingstupid"), "Found something with nonsense name");
		 assertEquals(5, foodItemDao.readFoodItemByName("Superhampurilainen").getPrice(), "Couldn't find superhampurilainen");
		 
		 
	 }
	
	 @Test
	 @DisplayName("Test to empty table")
	 void testEmptying() {
		foodItem = new FoodItem("kokis", 2.5, true);
		foodItemDao.createFoodItem(foodItem);
		foodItem = new FoodItem("Iso kokis", 3.5, false);
		foodItemDao.createFoodItem(foodItem);
		foodItem = new FoodItem("hamppari", 2.5, true);
		foodItemDao.createFoodItem(foodItem);
		assertEquals(true, foodItemDao.deleteAllFoodItems(), "Couldn't delete all food items");
		assertEquals(0, foodItemDao.readFoodItems().length, "Couldn't delete all items");
	 }

}
