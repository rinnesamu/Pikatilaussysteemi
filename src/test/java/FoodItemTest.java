import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.FoodItem;

class FoodItemTest {
	private FoodItem foodItem;
	private final double DELTA = 0.001;
	
    
	@BeforeEach
	void initTest() {
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
	
	@Test
	@DisplayName("Change menu asctivity: true -> false")
	void testSetInMenuFalse() {
		foodItem.setInMenu(true);
		assertEquals(true, foodItem.isInMenu(), "Couldn't take away from menu");
	}
	
	@Test
	@DisplayName("Test getting ready ammount")
	void testGetReady() {
		assertEquals(0, foodItem.getReady(), "Couldn't get correct ready ammount.");
	}
	
	@Test
	@DisplayName("Test setting ready ammount")
	void testSetReady() {
		foodItem.setReady(5);
		assertEquals(5, foodItem.getReady(), "Couldn't set correct ready ammount.");
		foodItem.setReady(-5);
		assertEquals(0, foodItem.getReady(), "Negative number should result 0");
	}
	
	@Test
	@DisplayName("Test getting category")
	void testGetCategory() {
		assertEquals("None", foodItem.getCategory(), "Couldn't get correct category.");
	}
	
	@Test
	@DisplayName("Test setting category")
	void testSetCategory() {
		foodItem.setCategory("Burger");
		assertEquals("Burger", foodItem.getCategory(), "Couldn't set correct category.");
	}
	
	@Test
	@DisplayName("Test getting sold ammount")
	void testGetSold() {
		assertEquals(0, foodItem.getSold(), "Couldn't get correct sold ammount.");
	}
	
	@Test
	@DisplayName("Test setting sold ammount")
	void testSetSold() {
		foodItem.setSold(5);
		assertEquals(5, foodItem.getSold(), "Couldn't set correct sold ammount.");
		foodItem.setSold(-5);
		assertEquals(0, foodItem.getSold(), "Negative number should result 0");
	}
	
	@Test
	@DisplayName("Test increase sold ammount")
	void testIncreaseSold() {
		foodItem.increaseSold();
		assertEquals(1, foodItem.getSold(), "Couldn't get correct sold ammount.");
		foodItem.increaseSold();
		foodItem.increaseSold();
		assertEquals(3, foodItem.getSold(), "Couldn't get correct sold ammount.");
	}
	
	@Test
	@DisplayName("Test increase and decrease ready ammount")
	void testIncreaseReady() {
		foodItem.increaseReady();
		assertEquals(1, foodItem.getReady(), "Couldn't get correct ready ammount.");
		foodItem.decreaseReady();
		assertEquals(0, foodItem.getReady(), "Couldn't get correct ready ammount.");
		foodItem.decreaseReady();
		assertEquals(0, foodItem.getReady(), "Couldn't get correct ready ammount.");
	}
	
	@Test
	@DisplayName("Test getter and setter for path")
	void testGetPath() {
		foodItem.setPath("joku/kiva/path.jpg");
		assertEquals("joku/kiva/path.jpg", foodItem.getPath(), "Couldnt get correct path!");
		foodItem.setPath("");
		assertEquals("", foodItem.getPath(), "Couldnt get correct path!");
		foodItem.setPath(".joku/kiva/path.jpg");
		assertEquals(".joku/kiva/path.jpg", foodItem.getPath(), "Couldnt get correct path!");
	}
	
	@Test
	@DisplayName("Test getting and setting incredients")
	void testIncredients() {
		assertEquals(null, foodItem.getIngredientsAsList(), "Couldn't get correct incredient list");
		String[] list = {"Tomaatti", "kurkku", "pihvi", "sämpylä", "juusto"};
		foodItem.setIngredients(list);
		assertEquals("kurkku", foodItem.getIngredientsAsList()[1], "Couldn't get correct incredient list");
		assertEquals("sämpylä", foodItem.getIngredientsAsList()[3], "Couldn't get correct incredient list");
		String[] list2 = {};
		foodItem.setIngredients(list2);
		assertEquals(null, foodItem.getIngredientsAsList(), "Couldn't get correct incredient list");
		String[] list3 = {"", null, "kurkku", "   ", "", "juusto", "", "kanapihvi", ""};
		foodItem.setIngredients(list3);
		assertEquals(3, foodItem.getIngredientsAsList().length, "Couldn't get correct incredient list");
		assertEquals("kurkku", foodItem.getIngredientsAsList()[0], "Couldn't get correct incredient list");
		assertEquals("juusto", foodItem.getIngredientsAsList()[1], "Couldn't get correct incredient list");
		assertEquals("kanapihvi", foodItem.getIngredientsAsList()[2], "Couldn't get correct incredient list");
		String[] list4 = {"", "", "kurkku", "", "", "juusto", "", "kana pihvi", ""};
		foodItem.setIngredients(list4);
		assertEquals("kana pihvi", foodItem.getIngredientsAsList()[2], "Couldn't get correct incredient list");
		assertEquals(3, foodItem.getIngredientsAsList().length, "Couldn't get correct incredient list");
	}
	@Test
	@DisplayName("Test getting and setting removed ingredients")
	void testRemoved() {
		String[] list = {"Tomaatti", "kurkku", "pihvi", "sämpylä", "juusto"};
		foodItem.setRemovedIngredients(list);
		assertEquals(5, foodItem.getRemovedIngredientsAsList().length, "Couldnt get or set removed ingredients");
		String[] list2 = {};
		foodItem.setRemovedIngredients(list2);
		assertEquals(null, foodItem.getRemovedIngredientsAsList(), "Couldn't get correct removed incredient list");
		String[] list3 = {"", null, "kurkku", "   ", "", "juusto", "", "kanapihvi", ""};
		foodItem.setRemovedIngredients(list3);
		assertEquals(3, foodItem.getRemovedIngredientsAsList().length, "Couldn't get correct removed incredient list");
		String[] list4 = {"", "", "kurkku", "", "", "juusto", "", "kana pihvi", ""};
		foodItem.setRemovedIngredients(list4);
		assertEquals(3, foodItem.getRemovedIngredientsAsList().length, "Couldn't get correct incredient list");
	}
	
	@Test
	@DisplayName("Test getting removed ingredients as string")
	void testRemoved2() {
		assertEquals(null, foodItem.getRemovedIngredientsAsString(), "Couldnt get the right removed ingredients string (empty)");
		String[] list = {"tomaatti", "kurkku", "pihvi", "sämpylä", "juusto"};
		foodItem.setRemovedIngredients(list);
		assertEquals("tomaatti,kurkku,pihvi,sämpylä,juusto", foodItem.getRemovedIngredientsAsString(), "Couldnt get the right removed ingredients string");
		String[] list2 = {""};
		foodItem.setRemovedIngredients(list2);
		assertEquals("", foodItem.getRemovedIngredientsAsString(), "Couldnt get the right removed ingredients string (emptied)");
	}
	
	@Test
	@DisplayName("Test for toString")
	void testToString() {
		assertEquals("kokis, price: 2.5", foodItem.toString(), "Couldnt read toString correctly.");
	}


}
