package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import model.Category;
import model.CategoryAccessObject;
import model.FoodItem;
import model.FoodItemAccessObject;
import model.Ingredient;
import model.IngredientAccessObject;
import model.Order;
import model.OrderAccessObject;
import model.ShoppingCart;
import view.IMenuView;

public class CustomerController implements ICustomerController {
	
	// AccessObjects for the database connections.
	private FoodItemAccessObject foodDao;
	private CategoryAccessObject categoryDao;
	private IngredientAccessObject ingredientDao;
	private OrderAccessObject orderDao;
	// Shopping cart object: contains the selected fooditems.
	private ShoppingCart shoppingCart;
	private IMenuView menuController;

	
	public CustomerController(IMenuView m) {
		this.menuController = m;
		this.foodDao = new FoodItemAccessObject();
		this.categoryDao = new CategoryAccessObject();
		this.orderDao = new OrderAccessObject();
		this.ingredientDao = new IngredientAccessObject();
		this.shoppingCart = new ShoppingCart();
	}

	@Override
	public void initMenu() {
		Category[] allCategories = categoryDao.readCategories();
		menuController.createCategoryList(allCategories);
		for (Category c : allCategories) {
			System.out.println(c.getName());
		}
		String categoryName = allCategories[0].getName();
		readCategories(categoryName);
		//menuController.categoryButtonHandler(categoryName);
		//menuController.setSum();
		// TODO: what if no categories?
		
	}

	@Override
	public void readCategories(String name) {
		FoodItem[] items = foodDao.readFoodItemsCategory(name);
		menuController.setItems(items);
		if (items.length != 0) {
			menuController.createMenu();
		}else {
			menuController.emptyCategory();
		}
		
	}

	@Override
	public void createOrder(int orderNumber, Map<FoodItem, Integer> shoppingCart, String additionalInfo) {
		Order order = new Order(orderNumber, shoppingCart);
		order.setAdditionalInfo(additionalInfo);
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
			ingredientsOfItem = null;
			return ingredientsOfItem;
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
	
	@Override
	public void emptyShoppingCart() {
		shoppingCart.emptyShoppingCart();
	}
	
	@Override
	public int[] getAllItemId() {
		return shoppingCart.getAllItemId();
	}

	@Override
	public void setAmount(int itemId, int amount) {
		shoppingCart.setAmount(itemId, amount);
	}

	@Override
	public int getAmount(int itemId) {
		return shoppingCart.getAmount(itemId);
	}

	@Override
	public void addToShoppingCart(FoodItem foodItem, int amount) {
		shoppingCart.addToShoppingCart(foodItem, amount);
	}

	@Override
	public void removeFromShoppingCart(FoodItem foodItem) {
		shoppingCart.removeFromShoppingCart(foodItem);
	}

	@Override
	public String shoppingCartToString() {
		return shoppingCart.toString();
	}

	@Override
	public Map<FoodItem, Integer> getShoppingCart() {
		return shoppingCart.getShoppingCart();
	}

	@Override
	public double getShoppingCartSum() {
		return shoppingCart.getSum();
	}

	@Override
	public FoodItem[] getFoodItems() {
		return shoppingCart.getFoodItems();
	}

}
