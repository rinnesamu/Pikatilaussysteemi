package view;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import javafx.scene.image.Image;
import model.*;

public class TextUI {
	
	static FoodItemAccessObject foodItemDao = new FoodItemAccessObject();
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		/*FoodItem f = new FoodItem("BicMac ateria", 7, "Ateriat", false);
		f.setPath("./src/main/resources/imgs/coca-cola-443123_1280.png");
		foodItemDao.createFoodItem(f);
		f = new FoodItem("Päärynä pirtelö", 7, "Jälkiruuat", true);
		f.setPath("./src/main/resources/imgs/appetite-1238459_1280.jpg");
		foodItemDao.createFoodItem(f);*/
		/*String name;
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
		*/
		/*System.out.println(foodItemDao.readFoodItems().length);
		FoodItem[] list = foodItemDao.readFoodItems();
		list[0].setPath("./src/main/resources/imgs/coca-cola-443123_1280.png");
		for (FoodItem f : list) {
			System.out.println(f.getItemId() + ": " + f.getName());
		}
		String path;
		for (int i = 0; i < list.length; i++) {
			if (i%5 == 0) {
				path = "./src/main/resources/imgs/coca-cola-443123_1280.png";
			} else if (i%5 == 1) {
				path = "./src/main/resources/imgs/aluminum-87987_1280.jpg";
			} else if (i%5 == 2) {
				path = "./src/main/resources/imgs/barbeque-1239407_1280.jpg";
			} else if (i%5 == 3) {
				path = "./src/main/resources/imgs/appetite-1238459_1280.jpg";
			} else {
				path = "./src/main/resources/imgs/cheeseburger-34314_1280.png";
			}
			list[i].setPath(path);
			foodItemDao.updateFoodItem(i, list[i]);
			
			
		}*/
	}

}
