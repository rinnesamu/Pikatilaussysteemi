package controller;

import java.util.ArrayList;
import java.util.Map;

import model.FoodItem;

public interface ICustomerController {
	public void initMenu();
	public void readCategories(String name);
	public void createOrder(int orderNumber, Map<FoodItem, Integer> shoppingCart, String additionalInfo);
	public ArrayList<String> getDatabaseIngredients(FoodItem foodItem);
	public void emptyShoppingCart();
	public int[] getAllItemId();
	public String getFoodItemName(int itemId);
	public void setAmount(int itemId, int amount);
	public int getAmount(int itemId);
	public void addToShoppingCart(FoodItem foodItem, int amount);
	public void removeFromShoppingCart(FoodItem foodItem);
	public String shoppingCartToString();
	public Map<FoodItem, Integer> getShoppingCart();
	public double getShoppingCartSum();
	public FoodItem[] getFoodItems();
}
