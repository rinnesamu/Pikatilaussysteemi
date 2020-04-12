package model;

/**
 * 
 * @author Samu Rinne
 *
 */

public interface IFoodItemDao {
	boolean createFoodItem(FoodItem foodItem);
	FoodItem[] readFoodItems();
	FoodItem readFoodItem(int itemId);
	boolean updateFoodItem(FoodItem foodItem);
	boolean deleteAllFoodItems();
	boolean deleteFoodItem(int itemId);
	FoodItem[] readFoodItemsByName(String name);
	FoodItem readFoodItemByName(String name);
	FoodItem[] readFoodItemsCategory(String category);
	

}
