import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.FoodItem;
import model.FoodItemAccessObject;

class FoodItemAccessObjectTest {
	
	private FoodItem foodItem;
	private FoodItemAccessObject foodItemDao = new FoodItemAccessObject();
	

	@Test
	void testFoodItemAccessObject() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateFoodItem() {
		foodItem = new FoodItem("kokis", 2.5, true);
		assertEquals(true, foodItemDao.createFoodItem(foodItem), "couldn't create food item");
	}

	@Test
	void testReadFoodItems() {
		fail("Not yet implemented");
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
