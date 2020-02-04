package view;
import java.util.Objects;
import java.util.Scanner;

import model.*;

public class TextUI {
	// Artun lisäämä koodi, order luokan, ja orderDAO:n kokeilua lisätty perään
	static OrderAccessObject orderDao = new OrderAccessObject();
	// ------
	
	static FoodItemAccessObject foodItemDao = new FoodItemAccessObject();
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		String name;
		double price;
		boolean inMenu;
		System.out.println("Items name?");
		name = scanner.nextLine();
		System.out.println("Items price");
		price = Double.parseDouble(scanner.nextLine());
		System.out.println("Is item on active menu?(y/n)");
		String ans = scanner.nextLine();
		if (Objects.deepEquals(ans, new String("y"))){
			System.out.println("Adding to active menu!");
			inMenu = true;
		}else {
			System.out.println("Not adding to active menu!");
			inMenu = false;
		}
		FoodItem foodItem = new FoodItem(name, price, inMenu);
		System.out.println(foodItem.isInMenu());
		boolean success = foodItemDao.createFoodItem(foodItem);
		
		if (success) {
			System.out.println("Item added!");
		}else {
			System.out.println("Couldn't add the item!");
		}
		
		System.out.println(foodItemDao.readFoodItems().length);
		FoodItem[] list = foodItemDao.readFoodItems();
		for (FoodItem f : list) {
			System.out.println(f.getItemId() + ": " + f.getName());
		}
		
		// Arttu
		// order luokan, ja orderDAO:n kokeilua
		System.out.println("Creating order");
		Order order = new Order(69);
		order.setAdditionalInfo("5 extra packets of ketchup!");
		order.addItemToOrder(foodItem);
		order.addItemToOrder(foodItem);
		System.out.println("Items in order: " + order.getOrderSize());
		boolean orderSuccess = orderDao.createOrder(order);
		
		if(orderSuccess) {
			System.out.println("order created");
		}else {
			System.out.println("order failed");
		}
		
		// System.out.println(order);
		// ---------------------
		
		
		// TODO Auto-generated method stub

	}

}
