package view;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import model.FoodItem;
import model.FoodItemAccessObject;
import model.ShoppingCart;

/**
 * 
 * @author Kimmo Perälä
 *
 */

public class MenuViewController {

	@FXML
	private Button meals;
	
	@FXML
	private Button drinks;

	@FXML
	private Button hamburgers;

	@FXML
	private Button desserts;
	
	@FXML
	private FlowPane menu;
	
	@FXML
	private VBox shoppingCartList;
	
	@FXML
	private Button emptyButton;
	
	private MainApp mainApp;
	
	private FoodItemAccessObject foodItemAO = new FoodItemAccessObject();
	
	private ShoppingCart shoppingCart = new ShoppingCart();
	
	private FoodItem[] items;
	
	private FoodItem[] allItems = foodItemAO.readFoodItems();

	
	public MenuViewController() {
		
	}
	
	int menuId;
	/*File colaFile = new File("./src/main/resources/imgs/coca-cola-443123_1280.png");
	Image cola = new Image(colaFile.toURI().toString());
	File fantaFile = new File("./src/main/resources/imgs/aluminum-87987_1280.jpg");
	Image fanta = new Image(fantaFile.toURI().toString());
	File cheeseFile = new File("./src/main/resources/imgs/barbeque-1239407_1280.jpg");
	Image cheeseBurger = new Image(cheeseFile.toURI().toString());
	File dcheeseFile = new File("./src/main/resources/imgs/appetite-1238459_1280.jpg");
	Image dcheeseBurger = new Image(dcheeseFile.toURI().toString());
	File mealFile = new File("./src/main/resources/imgs/cheeseburger-34314_1280.png");
	Image meal = new Image(mealFile.toURI().toString());

	ImageView colaView = new ImageView(cola);
	ImageView fantaView = new ImageView(fanta);
	ImageView cheeseBurgerView = new ImageView(cheeseBurger);
	ImageView dcheeseBurgerView = new ImageView(dcheeseBurger);
	ImageView mealView = new ImageView(meal);*/
	
	@FXML
	private void emptyShoppingCart() {
		shoppingCart.emptyShoppingCart();
		shoppingCartList.getChildren().clear();
		System.out.println(shoppingCart);
	}
	
	@FXML
	private void selectMeals() {
		FoodItem[] meals = foodItemAO.readFoodItemsCategory("Ateriat");
		items = meals;
		createMenu();
	}
	
	@FXML
	private void selectDrinks() {
		FoodItem[] drinks = foodItemAO.readFoodItemsCategory("Juomat");
		items = drinks;
		createMenu();
	}
	
	@FXML
	private void selectBurgers() {
		FoodItem[] burgers = foodItemAO.readFoodItemsCategory("Hampurilaiset");
		items = burgers;
		createMenu();
	}
	
	@FXML
	private void selectDesserts() {
		FoodItem[] desserts = foodItemAO.readFoodItemsCategory("Jälkiruuat");
		items = desserts;
		createMenu();
	}
	
	private void createMenu() {
		menu.getChildren().clear();

		/*colaView.setFitHeight(60);
		colaView.setFitWidth(60);
		fantaView.setFitHeight(60);
		fantaView.setFitWidth(60);
		cheeseBurgerView.setFitHeight(60);
		cheeseBurgerView.setFitWidth(60);
		dcheeseBurgerView.setFitHeight(60);
		dcheeseBurgerView.setFitWidth(60);
		mealView.setFitHeight(60);
		mealView.setFitWidth(60);*/
		
		for (int i = 0; i < items.length; i++) {
			if (items[i].isInMenu()) {
				Button menuItem = new Button();

				menuId = items[i].getItemId();
				menuItem.setId(Integer.toString(menuId));
				System.out.println("menuid on " + menuId);
				
				/*if (menuId == 1) {
					menuItem.setGraphic(colaView);
				}
				else if (menuId == 2) {
					menuItem.setGraphic(dcheeseBurgerView);
				}
				else if (menuId == 3) {
					menuItem.setGraphic(dcheeseBurgerView);
				}
				else if (menuId == 4) {
					menuItem.setGraphic(cheeseBurgerView);
				}
				else if (menuId == 5 || menuId == 6|| menuId == 10) {
					menuItem.setGraphic(fantaView);
				}
				else if (menuId == 7 || menuId == 8|| menuId == 9 ) {
					menuItem.setGraphic(mealView);
				}*/
				File file = new File(items[i].getPath());
				Image image = new Image(file.toURI().toString());
				ImageView iv = new ImageView(image);
				iv.setFitHeight(60);
				iv.setFitWidth(60);
				menuItem.setGraphic(iv);
				//menuItem.setText(Integer.toString(menuId));
				menuItem.setText(allItems[menuId-1].getName());
				menuItem.setContentDisplay(ContentDisplay.BOTTOM);
				menu.getChildren().add(menuItem);
				menuItem.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle (ActionEvent event)
					{
						Button sCartItem = new Button("aea");
						// Get all the items in shopping cart
						Integer[] listOfItemIds= shoppingCart.getAllItemId();
						int id = Integer.parseInt(menuItem.getId());
						System.out.println(shoppingCart);
						
						// If item is already there, increase the amount
						if (Arrays.asList(listOfItemIds).contains(id)) {
							int oldAmount = shoppingCart.getAmount(allItems[id-1]);
							System.out.println("vanha määrä " + oldAmount);
							shoppingCart.setAmount(allItems[id-1], (oldAmount+1));
							System.out.println(shoppingCart);
						}
						else {
							// Otherwise add to the shopping cart
							shoppingCart.addToShoppingCart(allItems[id-1], 1);
							System.out.println(shoppingCart);
							sCartItem.setMinSize(170, 50);
							//sCartItem.setId(Integer.toString(id));
							shoppingCartList.getChildren().add(sCartItem);
						}
						sCartItem.setText(allItems[id-1].getName() + " " + shoppingCart.getAmount(allItems[id-1]));
					}
				});
				
			}
			
		}
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
