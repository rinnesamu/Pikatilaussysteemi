

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.FoodItem;
import model.FoodItemAccessObject;
import model.ShoppingCart;

/**
 * 
 * @author Kimmo Perälä
 *
 */

class ShoppingCartTest {
	
	FoodItemAccessObject foodItemAO = new FoodItemAccessObject();
	
	private static ShoppingCart sCart = new ShoppingCart();
	FoodItem foodItem, foodItem2, foodItem3;

	@BeforeEach
    public void testEmpty() {
    	sCart.emptyShoppingCart();
		foodItem = new FoodItem("Juustohampurilainen", 4.5, true);
		sCart.addToShoppingCart(foodItem, 4);
    }
	
	@AfterEach
    public void printShoppingCart() {
		System.out.println(sCart);
	}
	
	
	// KESKEN ##################################
	@Test
	@DisplayName("Shopping cart getter")
	void testGetShoppingCart() {
		sCart.getShoppingCart().toString();
	}

	@Test
	@DisplayName("Adding one product to shopping cart")
	void testAddOneToShoppingCart() {
		assertEquals(1, sCart.sizeShoppingCart(), "Wrong size");
	}
	
	@Test
	@DisplayName("Adding two products to shopping cart")
	void testAddTwoToShoppingCart() {
		foodItem2 = new FoodItem("Coca-Cola", 3, true);
		sCart.addToShoppingCart(foodItem2, 2);
		assertEquals(2, sCart.sizeShoppingCart(), "Wrong size");
	}
	
	// KESKEN ##################################
	@Test
	@DisplayName("Adding same product again")
	void testSameProductToShoppingCart() {
		sCart.addToShoppingCart(foodItem, 5);
		assertEquals(9, sCart.getAmount(foodItem.getItemId()), "Wrong size");
	}

	@Test
	@DisplayName("Removing products from shopping cart")
	void testRemoveFromShoppingCart() {
		foodItem2 = new FoodItem("Pieni kahvi", 2, true);
		sCart.addToShoppingCart(foodItem2, 2);
		foodItem3 = new FoodItem("Jaffa", 3.5, true);
		sCart.addToShoppingCart(foodItem3, 2);
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

	// KESKEN ##################################
	@Test
	@DisplayName("Getting the amount of a product")
	void testGetAmount() {
		assertEquals(true, foodItemAO.createFoodItem(foodItem), "couldn't create food item");
		System.out.println("itemId on" + foodItemAO.readFoodItem(foodItemAO.readFoodItemByName("Juustohampurilainen").getItemId()));
		assertEquals(4, sCart.getAmount(foodItem.getItemId()), "Amount is wrong");
	}
	
	// KESKEN ##################################
	@Test
	@DisplayName("Changing the amount of a product")
	void testChangeAmount() {
		foodItem2 = new FoodItem("Pieni kahvi", 2, true);
		sCart.addToShoppingCart(foodItem2, 2);
		sCart.setAmount(foodItem2.getItemId(), 5);
		assertEquals(5, sCart.getAmount(foodItem.getItemId()), "Wrong amount");
	}
	
	@Test
	@DisplayName("Getting the size of the shopping cart")
	void testSizeShoppingCart() {
		sCart.addToShoppingCart(foodItem, 4);
		sCart.addToShoppingCart(foodItem, 4);
		assertEquals(1, sCart.sizeShoppingCart(), "Wrong size");
		
	}
	
	// KESKEN ##################################
	@Test
	@DisplayName("Changing the amount of a product to zero")
	void testChangeAmountToZero() {
		foodItem2 = new FoodItem("Iso kahvi", 2.5, true);
		sCart.addToShoppingCart(foodItem2, 4);
		sCart.setAmount(foodItem2.getItemId(), 0);
		assertEquals(1, sCart.sizeShoppingCart(), "Wrong size");
	}
}
