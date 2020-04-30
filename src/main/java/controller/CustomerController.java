package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import model.Category;
import model.CategoryAccessObject;
import model.FoodItem;
import model.FoodItemAccessObject;
import model.ICategoryDao;
import model.IFoodItemDao;
import model.IIngredientDao;
import model.IOrderDao;
import model.Ingredient;
import model.IngredientAccessObject;
import model.Order;
import model.OrderAccessObject;
import model.ShoppingCart;
import view.IMenuView;

public class CustomerController implements ICustomerController {
	
	// AccessObjects for the database connections.
	private IFoodItemDao foodDao;
	private ICategoryDao categoryDao;
	private IIngredientDao ingredientDao;
	private IOrderDao orderDao;
	// Shopping cart object: contains the selected fooditems.
	private ShoppingCart shoppingCart;
	// View layer
	private IMenuView menuView;

	/**
	 * Initial actions: creating DAO-objects and ShoppingCart object
	 * @param m View layer to be set
	 */
	public CustomerController(IMenuView m) {
		this.menuView = m;
		this.foodDao = new FoodItemAccessObject();
		this.categoryDao = new CategoryAccessObject();
		this.orderDao = new OrderAccessObject();
		this.ingredientDao = new IngredientAccessObject();
		this.shoppingCart = new ShoppingCart();
		ShopCartObserver shopCartObserver = new ShopCartObserver();
		this.shoppingCart.registerObserver(shopCartObserver);
	}

	/**
	 * Method for initializing the category list for the customer UI.
	 */
	@Override
	public void initMenu() {
		Category[] allCategories = categoryDao.readCategories();
		menuView.createCategoryList(allCategories);
		for (Category c : allCategories) {
			System.out.println(c.getName());
		}
		String categoryName="";
		try {
			categoryName = allCategories[0].getName();
			readCategories(categoryName);
			menuView.setSum(0);
		} catch (Exception e) {
			e.printStackTrace();
			menuView.noCategories();
		}
	}
	
	public void createFoodItemObserver(FoodItem foodItem) {
		FoodItemObserver fItemObserver = new FoodItemObserver();
		foodItem.registerObserver(fItemObserver);
	}
	
	/**
	 * Method for adding one item in the shopping cart.
	 * @param foodItem Fooditem of which amount is added.
	 * @return The updated amount of the fooditem.
	 */
	public int addOneToShoppingCart(FoodItem foodItem) {
		int amount = getAmount(foodItem.getItemId());
		amount += 1;
		setAmount(foodItem.getItemId(), amount);
		return amount;
	}

	/**
	 * Method for removing one item in the shopping cart.
	 * @param foodItem Fooditem of which amount is reduced.
	 * @return The updated amount of the fooditem.
	 */
	public int removeOneFromShoppingCart(FoodItem foodItem) {
		int amount = getAmount(foodItem.getItemId());
		if (amount != 1) {
			amount -= 1;
		}
		setAmount(foodItem.getItemId(), amount);
		return amount;
	}
	
	/**
	 * Inner class of the food item observer which updates the user view of removed ingredients.
	 */
	private class FoodItemObserver implements Observer {
		@Override
		public void update(Observable o, Object arg) {
			menuView.setElementRemovedIngredients(o, (String) arg);
		}
		
	}

	/**
	 * Inner class of the shopping cart observer which updates the sum element.
	 */
	private class ShopCartObserver implements Observer {
		@Override
		public void update(Observable o, Object arg) {
			menuView.setSum(getShoppingCartSum());
		}
		
	}
	
	/**
	 * Method for initializing the creating of the customer menu in a category.
	 */
	@Override
	public void readCategories(String name) {
		FoodItem[] items = foodDao.readFoodItemsCategory(name);
		menuView.setItems(items);
		if (items.length != 0) {
			menuView.createMenu();
		}else {
			menuView.emptyCategory();
		}
		
	}

	/**
	 * Method for creating a new order to the database. Creates a new shopping cart copy without duplicate foodItems to be added to the database.
	 */
	@Override
	public void createOrder(int orderNumber, Map<FoodItem, Integer> shoppingCart, String additionalInfo, boolean takeaway) {
		//Empty copy of the shopping cart.
		Map<FoodItem, Integer> orderContent = new HashMap<FoodItem, Integer>();
		// ArrayList including the names of fooditems already in the new copy of the shopping cart
		List<String> setAlready = new ArrayList<String>();
		
		// Iterate through the shopping cart
		for(Map.Entry<FoodItem, Integer> entry : shoppingCart.entrySet()) {
			// Get the amount of fooditem of the same name (also the fooditems with different ingredients)
			int realAmount = getAmount(entry.getKey().getName());
			//System.out.println("realAmount on " + realAmount);
			if (!setAlready.contains(entry.getKey().getName())) {
				orderContent.put(entry.getKey(), realAmount);
				setAlready.add(entry.getKey().getName());
			}
		}
		Order order = new Order(orderNumber, orderContent);
		order.setAdditionalInfo(additionalInfo);
		order.setTakeaway(takeaway);
		orderDao.createOrder(order);
	}

	/**
	 * Removable ingredients of an item are retrieved from the database ie. original ingredients.
	 * @param foodItem Fooditem of which ingredients are retrieved.
	 * @return Ingredients of the database object.
	 */
	@Override
	public ArrayList<String> getDatabaseIngredients(FoodItem foodItem) {
		
		ArrayList<String> ingredientsOfItem = new ArrayList<String>();
		String[] ingredientsNames;

		// If foodItem has no ingredients in the database return null.
		if (foodDao.readFoodItemByName(foodItem.getName()).getIngredientsAsList() == null ) {
			return null;
		}
		else {
			ingredientsNames = foodDao.readFoodItemByName(foodItem.getName()).getIngredientsAsList();

			// Checks which ingredients are removable
			for (int i = 0; i < ingredientsNames.length; i++) {
				Ingredient ingredientsAsIngredients= ingredientDao.readIngredientByName(ingredientsNames[i]);
				if (ingredientsAsIngredients.isRemoveable()) {
					ingredientsOfItem.add(ingredientsNames[i]);
				}
			}
			Collections.sort(ingredientsOfItem);
			return ingredientsOfItem;
		}
	}
	
	/**
	 * Method for emptying the shopping cart object.
	 */
	@Override
	public void emptyShoppingCart() {
		shoppingCart.emptyShoppingCart();
	}
	
	/**
	 * Getter for all the item ids of the shopping cart fooditems.
	 */
	@Override
	public int[] getAllItemId() {
		return shoppingCart.getAllItemId();
	}

	/**
	 * Setter for the amount of a certain fooditem in the shopping cart with itemId parameter.
	 */
	@Override
	public void setAmount(int itemId, int amount) {
		shoppingCart.setAmount(itemId, amount);
	}
	
	/**
	 * Setter for the amount of a certain fooditem in the shopping cart with name parameter.
	 */
	@Override
	public void setAmount(String name, int amount) {
		shoppingCart.setAmount(name, amount);
	}

	/**
	 * Getter for the amount of a certain fooditem in the shopping cart with itemId parameter.
	 * @return amount of specific food item in shopping cart
	 */
	@Override
	public int getAmount(int itemId) {
		return shoppingCart.getAmount(itemId);
	}

	/**
	 * Getter for the amount of a certain fooditem in the shopping cart with name parameter.
	 * @return amount of specific food item in shopping cart
	 */
	@Override
	public int getAmount(String name) {
		return shoppingCart.getAmount(name);
	}
	
	/**
	 * Method for adding an item of certain amount to the shopping cart.
	 */
	@Override
	public void addToShoppingCart(FoodItem foodItem, int amount) {
		shoppingCart.addToShoppingCart(foodItem, amount);
	}

	/**
	 * Method for removing an item from the shopping cart.
	 */
	@Override
	public void removeFromShoppingCart(FoodItem foodItem) {
		shoppingCart.removeFromShoppingCart(foodItem);
	}

	/**
	 * Shopping cart toString-method.
	 * @return shopping cart as a string
	 */
	@Override
	public String shoppingCartToString() {
		return shoppingCart.toString();
	}

	/**
	 * Getter for the shopping cart object.
	 * @return shopping cart object
	 */
	@Override
	public Map<FoodItem, Integer> getShoppingCart() {
		return shoppingCart.getShoppingCart();
	}

	/**
	 * Getter for the sum of the shopping cart.
	 * @return sum of the shopping cart
	 */
	@Override
	public double getShoppingCartSum() {
		return shoppingCart.getSum();
	}

	/**
	 * Getter for foodItems in the shopping cart.
	 * @return list of items in shopping cart
	 */
	@Override
	public FoodItem[] getFoodItems() {
		return shoppingCart.getFoodItems();
	}


}
