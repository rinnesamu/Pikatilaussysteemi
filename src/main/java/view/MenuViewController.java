package view;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

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
 * Controller for the customer user interface.
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

	private int menuId;

			
	public MenuViewController() {
		
	}
	

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
	
	@FXML
	private void initialize() {
		selectMeals();
	}
	
	/**
	 * Method for creating the menu of the selected category.
	 */
	
	private void createMenu() {
		menu.getChildren().clear();
		for (int i = 0; i < items.length; i++) {
			if (items[i].isInMenu()) {
				// Creating new menubutton.
				Button menuItem = new Button();

				// FoodItem from the category
				FoodItem fItem = items[i];
				
				// Taking item id of the foodItem and setting that as "menuId".
				menuId = fItem.getItemId();
				menuItem.setId(Integer.toString(menuId));
				menuItem.getStyleClass().add("menubutton");
				
				// Adding the menubutton (with the picture, text, size, handler) to the menulist.
				File file = new File(fItem.getPath());
				Image image = new Image(file.toURI().toString());
				ImageView iv = new ImageView(image);
				iv.setFitHeight(120);
				iv.setFitWidth(120);
				menuItem.setGraphic(iv);
				//menuItem.setText(Integer.toString(menuId));
				menuItem.setText(fItem.getName());
				menuItem.setContentDisplay(ContentDisplay.BOTTOM);
				menu.getChildren().add(menuItem);
				menuItem.setOnAction(event -> menuButtonHandler(fItem, menuItem));
			}
		}
	}
	
	/**
	 * Button handler for the menubuttons.
	 * 
	 * @param foodItem The fooditem tied to the particular button.
	 * @param button The created menubutton.
	 */
	
	private void menuButtonHandler(FoodItem foodItem, Button button) {
		
			Button sCartItem = new Button("");
			int id = Integer.parseInt(button.getId());
			sCartItem.setId(Integer.toString(id));
			sCartItem.setMinSize(240, 40);
			sCartItem.getStyleClass().add("cartbutton");
			
			// Get all the item numbers of the shopping cart and check whether the item already exists in the shopping cart.
			int[] listOfItemIds= shoppingCart.getAllItemId();
			//System.out.println("listOfItemIds " + Arrays.toString(listOfItemIds));
			boolean found = false;
			for (int i = 0; i < listOfItemIds.length; i++) {
				if (id == listOfItemIds[i]) {
					found = true;
				}	
			}
			// If item is already there, increase the amount in the shopping cart.
			if (found) {
				int oldAmount = shoppingCart.getAmount(id);
				shoppingCart.setAmount(foodItem.getItemId(), (oldAmount+1));
				//System.out.println("scl on " + shoppingCartList.getChildren());
				for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
					if (id == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
						shoppingCartList.getChildren().set(i, sCartItem);
						sCartItem.setText(foodItem.getName() + " " + shoppingCart.getAmount(id));
					}
				}	
			}
			// Otherwise add the item to the shopping cart.
			else {
				shoppingCart.addToShoppingCart(foodItem, 1);
				sCartItem.setText(foodItem.getName() + " " + shoppingCart.getAmount(id));
				shoppingCartList.getChildren().add(sCartItem);
			}
			
			// Add a handler for the shopping cart item buttons.
			System.out.println(shoppingCart);
			sCartItem.setOnAction(event -> sCartButtonHandler(sCartItem, foodItem, id));
	}
	
	/**
	 * Button Handler for the shopping cart item buttons.
	 * 
	 * @param button The created shopping cart item button.
	 * @param foodItem The foodItem tied to that particular button.
	 * @param id The item id of the foodItem.
	 */
	
	private void sCartButtonHandler (Button button, FoodItem foodItem, int id) {

		Alert options = new Alert(AlertType.INFORMATION);
		options.setTitle("Valitse " + foodItem.getName() + " määrä");
		int amountNow = shoppingCart.getAmount(id);
		options.setHeaderText(foodItem.getName() + ", määrä:  " + amountNow);
		options.setContentText("Valitse lisäys, vähennys tai poisto");
	
		ButtonType increase = new ButtonType("+");
		ButtonType decrease = new ButtonType("-");
		ButtonType remove = new ButtonType("Poista");
		ButtonType cancel = new ButtonType("Peruuta", ButtonData.CANCEL_CLOSE);
		
		options.getButtonTypes().setAll(increase, decrease, remove, cancel);
		Optional<ButtonType> result = options.showAndWait();
		
		if (result.get() == increase) {
			amountNow += 1;
			shoppingCart.setAmount(foodItem.getItemId(), amountNow);
		}
		else if (result.get() == decrease) {
			amountNow -= 1;
			shoppingCart.setAmount(foodItem.getItemId(), amountNow);
		}
		else if (result.get() == remove) {
			shoppingCart.setAmount(foodItem.getItemId(), 0);
		}
		else if (result.get() == cancel){
		}
		
		if (shoppingCart.getAmount(id) != 0) {
			button.setText(foodItem.getName() + " " + shoppingCart.getAmount(id));

			for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
				if (id == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
					shoppingCartList.getChildren().set(i, button);
				}
			}
		}
		else {
			for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
				if (id == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
					shoppingCartList.getChildren().remove(i);
				}
			}
		}
		System.out.println(shoppingCart);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
