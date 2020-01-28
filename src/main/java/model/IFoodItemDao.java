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
	boolean deleteFoodItem(int itemId);
	

}
