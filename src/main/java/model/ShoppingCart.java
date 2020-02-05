package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** Represents a shopping cart.
 *
 * @author Kimmo Perälä
 *
 **/

public class ShoppingCart {
	private Map<FoodItem, Integer> cartList;

	/** Creates a new shopping cart object.
	 */
	public ShoppingCart() {
		cartList = new HashMap<FoodItem, Integer>();
	}

	/** Adds a new product to the shopping cart.
	 * 
	 * @param foodItem Product object containing various information about the product.
	 * @param amount Amount of the product.
	 */
	public void addToShoppingCart(FoodItem foodItem, int amount) {
		if (cartList.containsKey(foodItem)) {
			int amountOriginal = cartList.get(foodItem);
			amountOriginal += amount;
			cartList.put(foodItem, amountOriginal);
		}
		else {
			cartList.put(foodItem, amount);
		}
	}

	/** Removes a product from the shopping cart.
	 * 
	 * @param foodItem Product to be removed.
	 */
	public void removeFromShoppingCart(FoodItem foodItem) {
		cartList.entrySet().removeIf(e -> e.getKey().equals(foodItem));
	}
	
	/** Empties the shopping cart
	 *
	 */
	public void emptyShoppingCart() {
		cartList.clear();
	}
	
	/** Returns the amount of a certain product in the shopping cart.
	 * 
	 * @param foodItem Product of which amount will be return.
	 * @return An integer presenting the quantity of the product.
	 */
	public int getAmount(FoodItem foodItem) {
		int amount = cartList.get(foodItem);
		return amount;
	}
	
	/** Increases of decreases the amount of a certain product in the shopping cart.
	 * 
	 * @param foodItem Product of which amount will be changed.
	 * @param newAmount The new amount of the product.
	 */
	public void setAmount(FoodItem foodItem, int newAmount) {
		cartList.put(foodItem, newAmount);
		if (newAmount == 0) {
			cartList.entrySet().removeIf(e -> e.getKey().equals(foodItem));
		}
	}
	
	/** Gets the amount of separate products in the shopping cart.
	 * 
	 * @return The number of different products in the shopping cart.
	 */
	public int sizeShoppingCart() {
		return cartList.size();
	}
	
	
	/** New implementation of toString method
	 * 
	 * @return The shopping cart hashmap in a readable form for the console testing.
	 */
	@Override
	public String toString() {
		return cartList.entrySet().toString();
	}
}
