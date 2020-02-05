import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.FoodItem;
import model.FoodItemAccessObject;

class FoodItemAccessObjectTest {
	
	private FoodItem foodItem;
	private FoodItemAccessObject foodItemDao;
	
	@BeforeEach
	void init() {
		foodItemDao = new FoodItemAccessObject(); // drops table and creates new.-
	}
	@Test
	void testFoodItemAccessObject() {
		fail("Not yet implemented");
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
	void testUpdateFoodItem() {
		fail("Not yet implemented");
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

}
