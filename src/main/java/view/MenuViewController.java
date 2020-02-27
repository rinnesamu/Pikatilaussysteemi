package view;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
	private void readyToPayShoppingCart() {
		Stage readyToPay = new Stage();
		ScrollPane sPane = new ScrollPane();
		VBox readyList = new VBox(20);
		readyList.setPadding(new Insets(30,0,0,30));
		double price;
		double priceSum = 0;
		int amount;
		
		FoodItem[] items = shoppingCart.getFoodItems();
		for (int i=0; i<items.length; i++) {
			HBox readySingleItem = new HBox();
			amount = shoppingCart.getAmount(items[i].getItemId());
			price = items[i].getPrice();
			Label payItem = new Label(items[i].getName() + ", " + amount + " kpl, hinta yhteensä: " + amount*price + " e");
			priceSum += amount*price;
			File file = new File(items[i].getPath());
			Image image = new Image(file.toURI().toString());
			ImageView iv = new ImageView(image);
			iv.setFitHeight(50);
			iv.setFitWidth(50);
			readySingleItem.getChildren().addAll(payItem, iv);
			readyList.getChildren().add(readySingleItem);
		}
		Label sumText = new Label("Summa: " + priceSum + " euroa");
		sumText.setFont(new Font(30));
		Button payButton = new Button("Maksa ostokset");
		readyList.getChildren().addAll(sumText, payButton);
		sPane.setContent(readyList);

		Scene payScene = new Scene(sPane, 600, 500);
		readyToPay.setScene(payScene);
		readyToPay.show();
	}

	@FXML
	private void emptyShoppingCart() {
		Alert options = new Alert(AlertType.CONFIRMATION);
		options.setTitle("Lopetus");
		options.setHeaderText("Haluatko varmasti lopettaa tilauksesi?");
		options.setContentText("Valitse OK tai Cancel");
	
		ButtonType okayDel = new ButtonType("OK");
		ButtonType cancelDel = new ButtonType("Cancel");
		
		options.getButtonTypes().setAll(okayDel, cancelDel);
		Optional<ButtonType> result = options.showAndWait();
		
		if (result.get() == okayDel) {
			shoppingCart.emptyShoppingCart();
			shoppingCartList.getChildren().clear();
			System.out.println(shoppingCart);
		}
		else if (result.get() == cancelDel) {
		}

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
				// Creating new menuItem.
				GridPane menuItem = new GridPane();

				// FoodItem from the category
				FoodItem fItem = items[i];
				
				// Taking item id of the foodItem and setting that as "menuId".
				menuId = fItem.getItemId();
				menuItem.getStyleClass().add("menubutton");
				
				// Adding the menubutton (with the picture, text, size, handler) to the menulist.
				File file = new File(fItem.getPath());
				Image image = new Image(file.toURI().toString());
				ImageView iv = new ImageView(image);
				iv.setFitHeight(150);
				iv.setFitWidth(150);
				iv.setId(Integer.toString(menuId));
				Label itemName = new Label(fItem.getName());
				
				Label priceTag = new Label(Double.toString(fItem.getPrice()) + " e");
				GridPane.setHalignment(itemName, HPos.CENTER);
				GridPane.setHalignment(priceTag, HPos.CENTER);
				menuItem.add(itemName, 0, 0);
				menuItem.add(iv, 0, 1);
				menuItem.add(priceTag, 0, 2);
				
				menu.getChildren().add(menuItem);
				EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						menuButtonHandler(fItem, iv);
					}
				};
				menuItem.addEventHandler(MouseEvent.MOUSE_PRESSED, eventHandler);
			}
		}
	}
	
	/**
	 * Button handler for the menubuttons.
	 * 
	 * @param foodItem The fooditem tied to the particular button.
	 * @param button The created menubutton.
	 */
	
	private void menuButtonHandler(FoodItem foodItem, ImageView imageView) {
		//HBox sCartRow = new HBox();
		Button sCartItem = new Button("");
		//Button deleteButton = new Button("DELETE");
		//sCartRow.getChildren().addAll(sCartItem, deleteButton);
		
		int id = Integer.parseInt(imageView.getId());
		sCartItem.setId(Integer.toString(id));
		sCartItem.setFont(new Font(25));
		sCartItem.setMinSize(375, 60);
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
		//sCartItem.setOnAction(event -> sCartButtonHandler(sCartItem, foodItem, id));
		sCartItem.setOnAction(event -> showPopUp(sCartItem, foodItem));
	}
	
	
	/**
	 * Shopping cart item menu for editing shopping list
	 * 
	 * @param button Button of the item in the shopping cart.
	 * @param foodItem The foodItem connected to that particular button.
	 */
	
	private void showPopUp(Button button, FoodItem foodItem) {
		Stage popUp = new Stage();
		int amountNow = shoppingCart.getAmount(foodItem.getItemId());
		int originalAmount = amountNow;
		
		Label nameAndAmount = new Label("Valitse " + foodItem.getName() + " määrä: ");
		nameAndAmount.setFont(new Font(25));
		nameAndAmount.setPadding(new Insets(0,0,0,20));
		Label pick = new Label(Integer.toString(amountNow));
		pick.setFont(new Font(30));
		pick.setPadding(new Insets(0,0,0,20));
		VBox boxWhole = new VBox(20);
		HBox boxButtons = new HBox(20);
		boxButtons.setPadding(new Insets(10,0,0,10));
		HBox boxOkCancel = new HBox(20);
		boxOkCancel.setPadding(new Insets(10,0,0,10));
		Button increase = new Button("+");
		increase.setFont(new Font(20));
		increase.setMinSize(80, 80);
		Button decrease = new Button("-");
		decrease.setFont(new Font(20));
		decrease.setMinSize(80, 80);
		Button delete = new Button("POISTA");
		delete.setStyle("-fx-background-color: #ff0000;");
		delete.setFont(new Font(20));
		delete.setMinSize(80, 80);
		Button okay = new Button("OK");
		okay.setFont(new Font(20));
		okay.setMinSize(80, 80);
		Button cancel = new Button("Peruuta");
		cancel.setFont(new Font(20));
		cancel.setMinSize(80, 80);
		
		increase.setOnAction(event -> {
			int amount = shoppingCart.getAmount(foodItem.getItemId());
			amount += 1;
			pick.setText(Integer.toString(amount));
			shoppingCart.setAmount(foodItem.getItemId(), amount);
		});
		decrease.setOnAction(event -> {
			int amount = shoppingCart.getAmount(foodItem.getItemId());
			
			if (amount != 1) {
				amount -= 1;
			}
			pick.setText(Integer.toString(amount));
			shoppingCart.setAmount(foodItem.getItemId(), amount);
		});
		delete.setOnAction(event -> {
			Alert options = new Alert(AlertType.CONFIRMATION);
			options.setTitle("Poisto");
			options.setHeaderText("Haluatko varmasti poistaa tuotteen " + foodItem.getName() + " ostoskorista?");
		
			ButtonType okayDel = new ButtonType("OK");
			ButtonType cancelDel = new ButtonType("Cancel");
			
			options.getButtonTypes().setAll(okayDel, cancelDel);
			Optional<ButtonType> result = options.showAndWait();
			
			if (result.get() == okayDel) {
				shoppingCart.removeFromShoppingCart(foodItem);
				nameAndAmount.setText(foodItem.getName() + " poistettu!");
				
				for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
					if (foodItem.getItemId() == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
						shoppingCartList.getChildren().remove(i);
					}
				}
				popUp.close();
			}
			else if (result.get() == cancelDel) {
			}

		});
		okay.setOnAction(event -> {
			button.setText(foodItem.getName() + " " + shoppingCart.getAmount(foodItem.getItemId()));

			for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
				if (foodItem.getItemId() == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
					System.out.println("ITEMI ON " + foodItem.getItemId());
					shoppingCartList.getChildren().set(i, button);
				}
			}
			popUp.close();
		});
		cancel.setOnAction(event -> {
			shoppingCart.setAmount(foodItem.getItemId(), originalAmount);
			popUp.close();
		});
		
		boxButtons.getChildren().addAll(increase, decrease, delete);
		boxOkCancel.getChildren().addAll(okay, cancel);
		
		boxWhole.getChildren().addAll(nameAndAmount, pick, boxButtons, boxOkCancel);
		Scene popUpScene = new Scene(boxWhole, 600, 350);
		popUp.setScene(popUpScene);
		popUp.show();
		
		
		System.out.println(shoppingCart);
	}
	
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
