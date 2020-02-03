import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.FoodItem;

class FoodItemTest {
	private FoodItem foodItem;
	private final double DELTA = 0.001;
	
    
	@BeforeEach
	 public void initTestSystem() {
		foodItem = new FoodItem("kokis", 2.5, true);
    }
	
	
	@Test
	@DisplayName("Getting price")
	void testGetPrice() {	
		assertEquals(2.5, foodItem.getPrice(), DELTA, "Couldn't get correct price");
	}

	@Test
	@DisplayName("Change price: Correct input")
	void testSetPrice() {
		foodItem.setPrice(3.5);
		assertEquals(3.5, foodItem.getPrice(), DELTA, "Couldn't chnage price");
	}
	
	@Test
	@DisplayName("Getting name")
	void testGetName() {
		assertEquals("kokis", foodItem.getName(), "Couldn't get correct name");
	}

	@Test
	@DisplayName("Change name: Correct input")
	void testSetName() {
		foodItem.setName("Iso kokis");
		assertEquals("Iso kokis", foodItem.getName(), "Couldn't change name");
	}


	@Test
	@DisplayName("Checking if product is in menu")
	void testIsInMenu() {
		assertEquals(true, foodItem.isInMenu(), "Couldn't decide if product is in menu");
	}

	@Test
	@DisplayName("Change menu asctivity: true -> false")
	void testSetInMenu() {
		foodItem.setInMenu(false);
		assertEquals(false, foodItem.isInMenu(), "Couldn't take away from menu");
	}
	
	@DisplayName("Change menu asctivity: true -> false")
	void testSetInMenuFalse() {
		foodItem.setInMenu(true);
		assertEquals(true, foodItem.isInMenu(), "Couldn't take away from menu");
	}

}
