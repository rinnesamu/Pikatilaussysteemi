package controller;

import java.util.ArrayList;
import java.util.Map;

import model.FoodItem;

public interface ICustomerController {
	public void initMenu();
	public void createFoodItemObserver(FoodItem foodItem);
	public int addOneToShoppingCart(FoodItem foodItem);
	public int removeOneFromShoppingCart(FoodItem foodItem);
	public void readCategories(String name);
	public String[] getOriginalIngredients(FoodItem searchableFoodItem);
	public void setFoodItemsWithIngredients();
	public void createOrder(int orderNumber, Map<FoodItem, Integer> shoppingCart, String additionalInfo, boolean takeaway);
	public void emptyShoppingCart();
	public int[] getAllItemId();
	public void setAmount(int itemId, int amount);
	public void setAmount(String name, int amount);
	public int getAmount(int itemId);
	public int getAmount(String name);
	public void addToShoppingCart(FoodItem foodItem, int amount);
	public void removeFromShoppingCart(FoodItem foodItem);
	public String shoppingCartToString();
	public Map<FoodItem, Integer> getShoppingCart();
	public double getShoppingCartSum();
	public FoodItem[] getFoodItems();
	public void notifyShoppingcartObserver();
}
