

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.FoodItem;
import model.ShoppingCart;

/**
 * 
 * @author Kimmo Perälä
 *
 */

class ShoppingCartTest {
		
	private ShoppingCart sCart = new ShoppingCart();
	private FoodItem foodItem, foodItem2, foodItem3;
	private final double DELTA = 0.001;
	
	@BeforeEach
    void testResetShoppingCart() {
    	sCart.emptyShoppingCart();
		foodItem = new FoodItem("Big Mac", 6, true, 1);
		sCart.addToShoppingCart(foodItem, 4);
    }
	
	@Test
	@DisplayName("Getting all ItemIds")
	void testGetAllItemId() {
		foodItem2 = new FoodItem("Pieni kahvi", 2, true, 2);
		sCart.addToShoppingCart(foodItem2, 2);
		int[] itemIds = sCart.getAllItemId();
		
		assertEquals(1, itemIds[0], "Id of the first item not found");
		assertEquals(2, itemIds[1], "Id of the second item not found");
	}

	@Test
	@DisplayName("Adding one product to shopping cart")
	void testAddOneToShoppingCart() {
		assertEquals(1, sCart.sizeShoppingCart(), "Wrong size");
	}
	
	@Test
	@DisplayName("Adding two products to shopping cart")
	void testAddTwoToShoppingCart() {
		foodItem2 = new FoodItem("Coca-Cola", 3, true, 2);
		sCart.addToShoppingCart(foodItem2, 2);
		assertEquals(2, sCart.sizeShoppingCart(), "Wrong size");
	}
	
	@Test
	@DisplayName("Adding same product again")
	void testSameProductToShoppingCart() {
		sCart.addToShoppingCart(foodItem, 5);
		assertEquals(9, sCart.getAmount(foodItem.getItemId()), "Wrong amount");
	}

	@Test
	@DisplayName("Removing products from shopping cart")
	void testRemoveFromShoppingCart() {
		foodItem2 = new FoodItem("Pieni kahvi", 2, true);
		sCart.addToShoppingCart(foodItem2, 2);
		foodItem3 = new FoodItem("Jaffa", 3.5, true);
		sCart.addToShoppingCart(foodItem3, 2);
		assertEquals(3, sCart.sizeShoppingCart(), "Wrong size");
		
		sCart.removeFromShoppingCart(foodItem);
		sCart.removeFromShoppingCart(foodItem3);
		assertEquals(1, sCart.sizeShoppingCart(), "Wrong size");
	}

	@Test
	@DisplayName("Emptying shopping cart")
	void testEmptyShoppingCart() {
		foodItem2 = new FoodItem("Pieni kahvi", 2, true);
		sCart.addToShoppingCart(foodItem2, 2);	
		sCart.emptyShoppingCart();
		assertEquals(0, sCart.sizeShoppingCart(), "Wrong size");
	}

	@Test
	@DisplayName("Getting the FoodItems of the shopping cart")
	void testGetFoodItems() {
		FoodItem[] allItems = sCart.getFoodItems();
		FoodItem firstItem = allItems[0];
		assertEquals("Big Mac", firstItem.getName(), "Wrong foodItem name");
		assertEquals(6, firstItem.getPrice(), DELTA, "Wrong foodItem price");
		assertEquals(1, firstItem.getItemId(), "Wrong foodItem itemId");
		assertEquals(true, firstItem.isInMenu(), "Wrong foodItem inMenu");
	}
	
	@Test
	@DisplayName("Getting the amount of a product")
	void testGetAmount() {
		assertEquals(4, sCart.getAmount(foodItem.getItemId()), "Getting the wrong amount");
	}
	
	@Test
	@DisplayName("Setting the amount of a product")
	void testSetAmount() {
		foodItem2 = new FoodItem("Pieni kahvi", 2, true);
		sCart.addToShoppingCart(foodItem2, 2);
		foodItem3 = new FoodItem("Jaffa", 3.5, true);
		sCart.addToShoppingCart(foodItem3, 2);
		sCart.setAmount(foodItem.getItemId(), 12);
		assertEquals(12, sCart.getAmount(foodItem.getItemId()), "Wrong amount");
		sCart.setAmount(foodItem2.getItemId(), 27);
		assertEquals(27, sCart.getAmount(foodItem2.getItemId()), "Wrong amount");
		sCart.setAmount(foodItem3.getItemId(), 31);
		assertEquals(31, sCart.getAmount(foodItem3.getItemId()), "Wrong amount");
	}

	@Test
	@DisplayName("Deleting item by setting the amount of a product to zero")
	void testSetAmountToZero() {
		sCart.setAmount(foodItem.getItemId(), 0);
		assertEquals(0, sCart.sizeShoppingCart(), "Wrong size");
	}

	@Test
	@DisplayName("Getting the sum of the shopping cart")
	void testgetSum() {
		assertEquals(24, sCart.getSum(), DELTA, "Wrong sum of the cart, one item");
		foodItem2 = new FoodItem("Pieni kahvi", 3, true, 2);
		sCart.addToShoppingCart(foodItem2, 5);
		System.out.println("scartti on" + sCart);
		assertEquals(39, sCart.getSum(), DELTA, "Wrong sum of the cart, two items");
		foodItem3 = new FoodItem("Pieni cola", 3, true, 3);
		sCart.addToShoppingCart(foodItem3, 1);
		System.out.println("scartti on" + sCart);
		assertEquals(42, sCart.getSum(), DELTA, "Wrong sum of the cart, three items");
	}
	
	@Test
	@DisplayName("Getting the size of the shopping cart")
	void testSizeShoppingCart() {
		assertEquals(1, sCart.sizeShoppingCart(), "Wrong original size");
		foodItem2 = new FoodItem("Pieni kahvi", 2, true);
		foodItem3 = new FoodItem("Pieni cola", 3, true);
		sCart.addToShoppingCart(foodItem2, 5);
		sCart.addToShoppingCart(foodItem3, 1);
		assertEquals(3, sCart.sizeShoppingCart(), "Wrong size");
	}
}
