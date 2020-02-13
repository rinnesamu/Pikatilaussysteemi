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
	public FoodItem readFoodItemByName(String name);
	public FoodItem[] readFoodItemsCategory(String category);
	

}
