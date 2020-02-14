package view;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

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
	
	private FoodItem[] allItems = foodItemAO.readFoodItems();

	
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
				//menuItem.setText(Integer.toString(menuId));
				menuItem.setText(fItem.getName());
				menuItem.setContentDisplay(ContentDisplay.BOTTOM);
				menu.getChildren().add(menuItem);
				
				EventHandler<ActionEvent> menuButtonHandler = new EventHandler<ActionEvent>() {
					
					@Override
					public void handle (ActionEvent event)
					{
						// Getting the id back
						Button sCartItem = new Button("");
						int id = Integer.parseInt(menuItem.getId());
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
							shoppingCart.setAmount(fItem, (oldAmount+1));
							sCartItem.setText(fItem.getName() + " " + shoppingCart.getAmount(id));
						}
						// Otherwise add to the shopping cart
						else {
							shoppingCart.addToShoppingCart(fItem, 1);
							System.out.println(shoppingCart);
							sCartItem.setMinSize(170, 50);
							sCartItem.setText(fItem.getName() + " " + shoppingCart.getAmount(id));
							shoppingCartList.getChildren().add(sCartItem);
						}
						System.out.println(shoppingCart);
						// Pressing the items on the shoppingCartList
						EventHandler<ActionEvent> sCartButtonHandler = new EventHandler<ActionEvent>() {
							
							@Override
							public void handle (ActionEvent event)
							{
								Alert options = new Alert(AlertType.CONFIRMATION);
								options.setTitle("Valitse " + fItem.getName() + " määrä");
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
									shoppingCart.setAmount(fItem, amountNow);
								}
								if (result.get() == decrease) {
									amountNow -= 1;
									shoppingCart.setAmount(fItem, amountNow);
								}
								if (result.get() == okay) {
									
								}
								if (result.get() == cancel){
									
								}
							}
						};
						sCartItem.setOnAction (sCartButtonHandler);
						if (shoppingCart.getAmount(id) == 0) {
							// then is removed from shoppingCartList
						};
					}
				};
				// Setting a mouse event
				menuItem.setOnAction(menuButtonHandler);
				
			}
			
		}
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
