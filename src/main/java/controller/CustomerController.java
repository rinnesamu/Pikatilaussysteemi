package controller;

import java.util.Map;

import model.Category;
import model.CategoryAccessObject;
import model.FoodItem;
import model.FoodItemAccessObject;
import model.IngredientAccessObject;
import model.Order;
import model.OrderAccessObject;
import view.IMenuView;
import view.MenuViewController;

public class CustomerController implements ICustomerController {
	
	private FoodItemAccessObject foodDao;
	private CategoryAccessObject categoryDao;
	private IngredientAccessObject ingredientDao;
	private OrderAccessObject orderDao;
	private IMenuView menuController;
	
	public CustomerController(IMenuView m) {
		this.menuController = m;
		this.foodDao = new FoodItemAccessObject();
		this.categoryDao = new CategoryAccessObject();
		this.orderDao = new OrderAccessObject();
		this.ingredientDao = new IngredientAccessObject();
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


	
	

}
