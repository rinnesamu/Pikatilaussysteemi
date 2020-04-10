package controller;

import java.util.ArrayList;
import java.util.Map;

import model.FoodItem;

public interface ICustomerController {
	public void initMenu();
	public void readCategories(String name);
	public void createOrder(int orderNumber, Map<FoodItem, Integer> shoppingCart, String additionalInfo);
	public ArrayList<String> getDatabaseIngredients(FoodItem foodItem);
}
