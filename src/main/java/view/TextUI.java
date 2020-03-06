package view;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import javafx.scene.image.Image;
import model.*;

public class TextUI {
	
	static FoodItemAccessObject foodItemDao = new FoodItemAccessObject();
	static CategoryAccessObject categoryDao = new CategoryAccessObject();
	static IngredientAccessObject ingredientDao = new IngredientAccessObject();
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		FoodItem fo;
		String c;
		for (int a = 0; a < 10; a++) {
			if (a%4 == 0) {
				c = "Hampurilaiset";
			}else if (a%4 == 1) {
				c = "Ateriat";
			}else if (a%4 == 2) {
				c = "Juomat";
			}else {
				c = "Jälkiruuat";
			}
			fo = new FoodItem("Tuote " + (a+1), 2.5, c, true);
			foodItemDao.createFoodItem(fo);
		}
		Category category = new Category("Ateriat");
		categoryDao.createCategory(category);
		category = new Category("Hampurilaiset");
		categoryDao.createCategory(category);
		category = new Category("Juomat");
		categoryDao.createCategory(category);
		category = new Category("Jälkiruuat");
		categoryDao.createCategory(category);
		/*FoodItem f = new FoodItem("BicMac ateria", 7, "Ateriat", false);
		}
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
		System.out.println(foodItemDao.readFoodItems().length);
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
			foodItemDao.updateFoodItem(list[i]);
			
			
		}
		Ingredient newIngredient = new Ingredient("pihvi", true);
		ingredientDao.createIngredient(newIngredient);
		Ingredient newIngredient2 = new Ingredient("juusto", true);
		ingredientDao.createIngredient(newIngredient2);
		Ingredient newIngredient3 = new Ingredient("suolakurkku", true);
		ingredientDao.createIngredient(newIngredient3);
		Ingredient newIngredient4 = new Ingredient("tomaatti", true);
		ingredientDao.createIngredient(newIngredient4);
		Ingredient newIngredient5 = new Ingredient("sämpylä", false);
		ingredientDao.createIngredient(newIngredient5);
		Ingredient newIngredient6 = new Ingredient("kermavaahto", true);
		ingredientDao.createIngredient(newIngredient6);
		
		String[] ingredients= new String[4];
		for (int i = 0; i < list.length; i++) {
			
			if (i%4 == 0) {
				ingredients = new String[4];
				ingredients[0] = "pihvi";
				ingredients[1] = "juusto";
				ingredients[2] = "suolakurkku";
				ingredients[3] = "tomaatti";
			}else if (i%4 == 1) {
				ingredients= new String[0];
			}else if (i%4 == 2) {
				ingredients = new String[0];
			}else {
				ingredients = new String[0];
			}
			System.out.println("Inkut " + Arrays.toString(ingredients));
			list[i].setIngredients(ingredients);
			foodItemDao.updateFoodItem(list[i]);
			
		}
	}

}
