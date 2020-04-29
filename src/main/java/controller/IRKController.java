package controller;

import java.time.LocalDate;

import model.Category;
import model.FoodItem;
import model.Ingredient;
import model.Order;

/**
 * Interface for the restaurant keeper controller
 * 
 * @author Arttu Seuna
 *
 */
public interface IRKController {

	public Order[] searchOrdersByDate(LocalDate startDate, LocalDate endDate);
	public Order[] readOrders();
	public boolean updateOrderStatus(Order order);
	public FoodItem[] readFoodItems();
	public boolean deleteFoodItem(int itemId);
	public boolean updateFoodItem(FoodItem foodItem);
	public boolean createFoodItem(FoodItem foodItem);
	public Category[] readCategories();
	public boolean deleteCategoryByName(String name);
	public boolean createCategory(Category category);
	public Ingredient[] readIngredients();
	public boolean deleteIngredient(int itemId);
	public boolean createIngredient(Ingredient ingredient);
}
