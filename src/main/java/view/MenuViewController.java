package view;

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
	
	private FoodItem[] AllItems = foodItemAO.readFoodItems();

	
	public MenuViewController() {
		
	}
	
	int menuId;
	Image cola = new Image("../resources/imgs/coca-cola-443123_1280.png");
	Image fanta = new Image("../resources/imgs/aluminum-87987_1280.jpg");
	Image cheeseBurger = new Image("../resources/imgs/barbeque-1239407_1280.jpg");
	Image dcheeseBurger = new Image("../resources/imgs/appetite-1238459_1280.jpg");
	Image meal = new Image("../resources/imgs/cheeseburger-34314_1280.png");

	ImageView colaView = new ImageView(cola);
	ImageView fantaView = new ImageView(fanta);
	ImageView cheeseBurgerView = new ImageView(cheeseBurger);
	ImageView dcheeseBurgerView = new ImageView(dcheeseBurger);
	ImageView mealView = new ImageView(meal);
	
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

		colaView.setFitHeight(60);
		colaView.setFitWidth(60);
		fantaView.setFitHeight(60);
		fantaView.setFitWidth(60);
		cheeseBurgerView.setFitHeight(60);
		cheeseBurgerView.setFitWidth(60);
		dcheeseBurgerView.setFitHeight(60);
		dcheeseBurgerView.setFitWidth(60);
		mealView.setFitHeight(60);
		mealView.setFitWidth(60);
		
		for (int i = 0; i < items.length; i++) {
			if (items[i].isInMenu()) {
				Button menuItem = new Button();

				menuId = items[i].getItemId();
				menuItem.setId(Integer.toString(menuId));
				System.out.println("menuid on " + menuId);
				
				if (menuId == 1) {
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
				}
				//menuItem.setText(Integer.toString(menuId));
				menuItem.setText(AllItems[menuId-1].getName());
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
							int oldAmount = shoppingCart.getAmount(AllItems[id-1]);
							System.out.println("vanha määrä " + oldAmount);
							shoppingCart.setAmount(AllItems[id-1], (oldAmount+1));
							System.out.println(shoppingCart);
						}
						else {
							// Otherwise add to the shopping cart
							shoppingCart.addToShoppingCart(AllItems[id-1], 1);
							System.out.println(shoppingCart);
							sCartItem.setMinSize(170, 50);
							//sCartItem.setId(Integer.toString(id));
							shoppingCartList.getChildren().add(sCartItem);
						}
						sCartItem.setText(AllItems[id-1].getName() + " " + shoppingCart.getAmount(AllItems[id-1]));
					}
				});
				
			}
			
		}
	}
	
	/*
	@FXML
	private void initialize() {
		FoodItem[] fItems = items;
		int menuId;
		Image cola = new Image("view/Pictures/coca-cola-443123_1280.png");
		Image fanta = new Image("view/Pictures/aluminum-87987_1280.jpg");
		Image cheeseBurger = new Image("view/Pictures/barbeque-1239407_1280.jpg");
		Image dcheeseBurger = new Image("view/Pictures/appetite-1238459_1280.jpg");
		Image meal = new Image("view/Pictures/cheeseburger-34314_1280.png");

		ImageView colaView = new ImageView(cola);
		ImageView fantaView = new ImageView(fanta);
		ImageView cheeseBurgerView = new ImageView(cheeseBurger);
		ImageView dcheeseBurgerView = new ImageView(dcheeseBurger);
		ImageView mealView = new ImageView(meal);
		colaView.setFitHeight(60);
		colaView.setFitWidth(60);
		fantaView.setFitHeight(60);
		fantaView.setFitWidth(60);
		cheeseBurgerView.setFitHeight(60);
		cheeseBurgerView.setFitWidth(60);
		dcheeseBurgerView.setFitHeight(60);
		dcheeseBurgerView.setFitWidth(60);
		mealView.setFitHeight(60);
		mealView.setFitWidth(60);
		
		emptyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event)
			{
				shoppingCart.emptyShoppingCart();
				shoppingCartList.getChildren().clear();
				System.out.println(shoppingCart);
			}
		});
		
		for (int i = 0; i < fItems.length; i++) {
			if (fItems[i].isInMenu()) {
				Button menuItem = new Button();
				Button sCartItem = new Button();

				menuId = fItems[i].getItemId();
				menuItem.setId(Integer.toString(menuId));
				System.out.println(menuId);
				
				if (menuId == 1) {
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
				}
				// menuItem.setText(Integer.toString(menuId));
				menuItem.setText(fItems[i].getName());
				menuItem.setContentDisplay(ContentDisplay.BOTTOM);
				menuItem.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle (ActionEvent event)
					{
						// Get all the items in shopping cart
						Integer[] listOfItemIds= shoppingCart.getAllItemId();
						int id = Integer.parseInt(menuItem.getId());
						System.out.println(shoppingCart);
						
						// If item is already there, increase the amount
						if (Arrays.asList(listOfItemIds).contains(id)) {
							int oldAmount = shoppingCart.getAmount(fItems[id-1]);
							shoppingCart.setAmount(fItems[id-1], (oldAmount+1));
							System.out.println(shoppingCart);
						}
						else {
							// Otherwise add to the shopping cart
							shoppingCart.addToShoppingCart(fItems[id-1], 1);
							System.out.println(shoppingCart);
							sCartItem.setMinSize(170, 50);
							//sCartItem.setId(Integer.toString(id));
							shoppingCartList.getChildren().add(sCartItem);
						}
						sCartItem.setText(fItems[id-1].getName() + " " + shoppingCart.getAmount(fItems[id-1]));
					}
				});
				
				menu.getChildren().add(menuItem);
			}
			
		}
	}*/
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
