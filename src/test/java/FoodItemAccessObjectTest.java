import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
	private FoodItemAccessObject foodItemDao;
	
	@BeforeEach
	void init() {
		foodItemDao = new FoodItemAccessObject(); // drops table and creates new.-
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
		foodItem.setInMenu(false);
		assertEquals(true, foodItemDao.updateFoodItem(0, foodItem), "couldn't update fooditem");
		assertEquals("Iso Kokis", foodItemDao.readFoodItems()[0].getName(), "Fooditems name did not update correctyl!");
		assertEquals(3.5, foodItemDao.readFoodItems()[0].getPrice(), "Fooditems price did not update correctyl!");
		assertEquals(false, foodItemDao.readFoodItems()[0].isInMenu(), "Fooditems menu acitivity did not update correctyl!");
		assertEquals("None", foodItemDao.readFoodItems()[0].getCategory(), "Fooditems category did not update correctyl!");
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

}
