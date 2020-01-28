package view;
import java.util.Objects;
import java.util.Scanner;

import model.*;

public class TextUI {
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
		
		
		// TODO Auto-generated method stub

	}

}
