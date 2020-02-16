package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** 
 * Represents a shopping cart.
 *
 * @author Kimmo Perälä
 *
 **/

public class ShoppingCart {
	private Map<FoodItem, Integer> cartList;

	/**
	 * Creates a new shopping cart object.
	 */
	public ShoppingCart() {
		cartList = new HashMap<FoodItem, Integer>();
	}
	
	/**
	 * Getter for the shopping cart.
	 * 
	 * @return The shopping cart object.
	 */
	public Map<FoodItem, Integer> getShoppingCart() {
		return cartList;
	}
	
	/**
	 *  Getter for all the item id numbers in the shopping cart.
	 *  
	 * @return All the item id numbers.
	 */
	public int[] getAllItemId() {
		Set<FoodItem> foodItems = cartList.keySet();
		FoodItem[] fItemsArray = foodItems.toArray(new FoodItem[foodItems.size()]);
		int[] allItemId = new int[fItemsArray.length];
		
		for(int i = 0; i < fItemsArray.length; i++) {
			allItemId[i]= fItemsArray[i].getItemId();
		}
		return allItemId;
	}

	/**
	 * Adds a new product to the shopping cart.
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

	/** 
	 * Removes a product from the shopping cart.
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
	
	/**
	 * Returns the amount of a certain product in the shopping cart.
	 * 
	 * @param itemId ItemId of the product of which amount will be returned.
	 * @return An integer presenting the quantity of the product.
	 */
	public int getAmount(int itemId) {
		try {
			FoodItem fItem = new FoodItem();
			Set<FoodItem> foodItems = cartList.keySet();
			FoodItem[] fItemsArray = foodItems.toArray(new FoodItem[foodItems.size()]);
			
			for(int i = 0; i < fItemsArray.length; i++) {
				if (itemId == fItemsArray[i].getItemId()) {
					fItem = fItemsArray[i];
				}
			}
			int amount = (int) cartList.get(fItem);
			return amount;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	/** 
	 * Increases or decreases the amount of a certain product in the shopping cart.
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
	
	/** 
	 * Gets the amount of separate products in the shopping cart.
	 * 
	 * @return The number of different products in the shopping cart.
	 */
	public int sizeShoppingCart() {
		return cartList.size();
	}
	
	
	/** 
	 * New implementation of toString method
	 * 
	 * @return The shopping cart hashmap in a readable form for the console testing.
	 */
	@Override
	public String toString() {
		return cartList.entrySet().toString();
	}
}
