package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
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
	
	@FXML
	private Button buyButton;
	
	private MainApp mainApp;
	
	private FoodItemAccessObject foodItemAO = new FoodItemAccessObject();
	
	private ShoppingCart shoppingCart = new ShoppingCart();
	
	private FoodItem[] items;
		
	public MenuViewController() {
		
	}
	
	int menuId;

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
		System.out.println("desserts on " + items);
		createMenu();
	}
	
	@FXML
	private void initialize() {
		selectMeals();
	}
	
	private void createMenu() {
		menu.getChildren().clear();
		/*for (FoodItem item: allItems) {
			System.out.println("menuid on " + item.toString());
			System.out.println("itemid on " + item.getItemId());
		}*/
		for (int i = 0; i < items.length; i++) {
			if (items[i].isInMenu()) {
				Button menuItem = new Button();

				// FoodItem from the category
				FoodItem fItem = items[i];
				//System.out.println("fItem on " + fItem);

				// Taking item id of that FoodItem and setting that as "menuId"
				menuId = fItem.getItemId();
				menuItem.setId(Integer.toString(menuId));
				
				// Adding that item to the menulist
				File file = new File(fItem.getPath());
				Image image = new Image(file.toURI().toString());
				ImageView iv = new ImageView(image);
				iv.setFitHeight(60);
				iv.setFitWidth(60);
				menuItem.setGraphic(iv);
				menuItem.setText(Integer.toString(menuId));
				//menuItem.setText(fItem.getName());
				menuItem.setContentDisplay(ContentDisplay.BOTTOM);
				menu.getChildren().add(menuItem);
				menuItem.setOnAction(event -> menuButtonHandler(fItem, menuItem));
			}
		}
	}
	
	private void menuButtonHandler(FoodItem foodItem, Button button) {
		
			Button sCartItem = new Button("");
			int id = Integer.parseInt(button.getId());
			sCartItem.setId(Integer.toString(id));
			
			// Get all the item numbers in shopping cart
			int[] listOfItemIds= shoppingCart.getAllItemId();
			System.out.println("listOfItemIds " + Arrays.toString(listOfItemIds));
			boolean found = false;
			for (int i = 0; i < listOfItemIds.length; i++) {
				if (id == listOfItemIds[i]) {
					found = true;
				}	
			}
			// If item is already there, increase the amount
			if (found) {
				int oldAmount = shoppingCart.getAmount(id);
				shoppingCart.setAmount(foodItem, (oldAmount+1));
				shoppingCartList.getChildren().remove(sCartItem);
				System.out.println("scl on " + shoppingCartList.getChildren());
				// korvaa id -> fItem.getName()
				System.out.println("getID ON " + sCartItem.getId());
				for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
					if (id == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
						shoppingCartList.getChildren().remove(i);
					}
				}				
				sCartItem.setText(id + " " + shoppingCart.getAmount(id));
				shoppingCartList.getChildren().add(sCartItem);
			}
			// Otherwise add to the shopping cart
			else {
				shoppingCart.addToShoppingCart(foodItem, 1);
				System.out.println(shoppingCart);
				sCartItem.setMinSize(170, 50);
				sCartItem.setText(id + " " + shoppingCart.getAmount(id));
				shoppingCartList.getChildren().add(sCartItem);
			}
			System.out.println(shoppingCart);
			sCartItem.setOnAction(event -> sCartButtonHandler(foodItem, id));
	}
	
	private void sCartButtonHandler (FoodItem foodItem, int id) {

		Alert options = new Alert(AlertType.CONFIRMATION);
		options.setTitle("Valitse " + foodItem.getName() + " määrä");
		int amountNow = shoppingCart.getAmount(id);
		options.setHeaderText("Määrä:  " + amountNow);
		options.setContentText("Valitse lisäys tai vähennys");
	
		ButtonType increase = new ButtonType("Lisää");
		ButtonType decrease = new ButtonType("Vähennä");
		ButtonType okay = new ButtonType("OK",ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("Peruuta", ButtonData.CANCEL_CLOSE);
		
		options.getButtonTypes().setAll(increase, decrease, okay, cancel);
		Optional<ButtonType> result = options.showAndWait();
		
		if (result.get() == increase) {
			amountNow += 1;
			shoppingCart.setAmount(foodItem, amountNow);
		}
		if (result.get() == decrease) {
			amountNow -= 1;
			shoppingCart.setAmount(foodItem, amountNow);
		}
		if (result.get() == okay) {
			
		}
		if (result.get() == cancel){
			
		}
		
		if (shoppingCart.getAmount(id) == 0) {
			// then is removed from shoppingCartList
		}
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
