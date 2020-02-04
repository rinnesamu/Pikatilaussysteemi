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
		assertEquals(1, foodItemDao.readFoodItems().length, "read all food items does not work!");
		foodItem = new FoodItem("Iso kokis", 3.5, false);
		foodItemDao.createFoodItem(foodItem);
		assertEquals(2, foodItemDao.readFoodItems().length, "read all food items does not work!");
		
	}

	@Test
	void testReadFoodItem() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateFoodItem() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteFoodItem() {
		fail("Not yet implemented");
	}

}
