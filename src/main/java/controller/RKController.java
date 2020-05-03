package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.Category;
import model.CategoryAccessObject;
import model.FoodItem;
import model.FoodItemAccessObject;
import model.IOrderDao;
import model.Ingredient;
import model.IngredientAccessObject;
import model.Order;
import model.OrderAccessObject;
import view.RestaurantKeeper;

/**
 * Controller class for handling the logic for restaurant keeper view
 * 
 * @author Arttu Seuna
 *
 */

public class RKController implements IRKController {
	
	// DAOs
	private IOrderDao orderDao;
	private FoodItemAccessObject foodItemDao;
	private CategoryAccessObject categoryDao;
	private IngredientAccessObject ingredientDao;
	
	private RestaurantKeeper restaurantKeeper;
	
	/**
	 * Constructor for the restaurant keeper controller -class. Constructor defines the data access objects for database operations.
	 * 
	 * @param restaurantKeeper instance of the RestaurantKeeper -class that handles viewing the information
	 */
	public RKController(RestaurantKeeper restaurantKeeper) {
		// init data access objects needed
		this.orderDao = new OrderAccessObject();
		categoryDao = new CategoryAccessObject();
		foodItemDao = new FoodItemAccessObject();
		ingredientDao = new IngredientAccessObject();
		orderDao = new OrderAccessObject();
		
		this.restaurantKeeper = restaurantKeeper;
	}
	
	/**
	 * Method for searching the database for orders based on passed dates
	 * 
	 * @param startDate lower date limit for the search
	 * @param endDate higher date limit for the search
	 * 
	 * @return array of order objects limited by dates
	 */
	@Override
	public Order[] searchOrdersByDate(LocalDate startDate, LocalDate endDate) {
		System.out.println("In controller: search button pressed");
		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atTime(23,59, 59);
		
		return orderDao.readOrdersByDate(startDateTime, endDateTime);
	}

	/**
	 * Method for reading all the orders from the database
	 * 
	 * @return Array of Order objects
	 */
	@Override
	public Order[] readOrders() {
		return orderDao.readOrders();
	}
	
	/**
	 * Method for updating the status of an order
	 * 
	 * @param order the order object which status is updated
	 * @return return value is boolean which indicates the success of the status update
	 */
	@Override
	public boolean updateOrderStatus(Order order) {
		return orderDao.updateOrderStatus(order);
	}
	
	/**
	 * Method for fetching all foodItems from the database
	 * 
	 * @return returns array of FoodItem objects
	 */
	@Override
	public FoodItem[] readFoodItems() {
		return foodItemDao.readFoodItems();
	}

	/**
	 * Method for deleting a specific food item
	 * 
	 * @param itemId the id of the item to be deleted
	 * @return boolean indicating success of foodItem deletion
	 */
	@Override
	public boolean deleteFoodItem(int itemId) {
		return foodItemDao.deleteFoodItem(itemId);
	}

	/**
	 * Method for updating a foodItem object
	 * 
	 * @param foodItem FoodItem object to be updated
	 * @return boolean indicating success of foodItem update
	 */
	@Override
	public boolean updateFoodItem(FoodItem foodItem) {
		return foodItemDao.updateFoodItem(foodItem);
	}

	/**
	 * Method for adding a new food item in the database
	 * 
	 * @param foodItem object to be added to the database
	 * @return boolean indicating success of foodItem addition
	 */
	@Override
	public boolean createFoodItem(FoodItem foodItem) {
		return foodItemDao.createFoodItem(foodItem);
	}

	/**
	 * Method for fetching all categories from database
	 * 
	 * @return Array of Category objects
	 */
	@Override
	public Category[] readCategories() {
		return categoryDao.readCategories();
	}

	/**
	 * Method for deleting a category from database based on it's name
	 * 
	 * @param name name of the category to be deleted
	 * @return boolean indicating success of category deletion
	 */
	@Override
	public boolean deleteCategoryByName(String name) {
		return categoryDao.deleteCategoryByName(name);
	}
	/**
	 * Method for adding a category to the database
	 * 
	 * @param category category to be added to the database
	 * @return boolean indicating success of category addition
	 */
	@Override
	public boolean createCategory(Category category) {
		return categoryDao.createCategory(category);
	}

	/**
	 * Method for reading all the ingredients from the database
	 * 
	 * @return Array of Ingredient objects
	 */
	@Override
	public Ingredient[] readIngredients() {
		return ingredientDao.readIngredients();
	}
	
	/**
	 * Method for deleting a specific ingredient
	 * 
	 * @param itemId the id of the ingredient to be deleted
	 * @return boolean indicating success of ingredient deletion
	 */
	@Override
	public boolean deleteIngredient(int itemId) {
		return ingredientDao.deleteIngredient(itemId);
	}
	
	/**
	 * Method for adding an ingredient to the database
	 * 
	 * @param ingredient ingredient to be added to the database
	 * @return boolean indicating success of ingredient addition
	 */
	@Override
	public boolean createIngredient(Ingredient ingredient) {
		return ingredientDao.createIngredient(ingredient);
	}

}
