package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.lang.Object;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Category;
import model.CategoryAccessObject;
import model.FoodItem;
import model.FoodItemAccessObject;
import model.Ingredient;
import model.IngredientAccessObject;
import model.Order;
import model.OrderAccessObject;
import model.ShoppingCart;

/**
 * Controller for the customer user interface.
 * 
 * @author Kimmo Perälä
 *
 */

public class MenuViewController {
	
	// Element where the menu is located.
	@FXML
	private FlowPane menu;

	// Category list element.
	@FXML
	private VBox categoryList;
	
	// Shopping cart list element.
	@FXML
	private VBox shoppingCartList;
	
	// Button for emptying the shopping cart.
	@FXML
	private Button emptyButton;
	
	// Button for paying the shopping cart.
	@FXML
	private Button buyButton;
	
	// Sum element of the shopping cart list.
	@FXML
	private Label sumShoppingCart;
	
	
	// AccessObjects for the database connections.
	
	private FoodItemAccessObject foodItemAO = new FoodItemAccessObject();
	private CategoryAccessObject categoryAO = new CategoryAccessObject();
	private OrderAccessObject orderAO = new OrderAccessObject();
	private IngredientAccessObject ingredientAO = new IngredientAccessObject();
	
	// Shopping cart object: contains the selected fooditems.
	private ShoppingCart shoppingCart = new ShoppingCart();

	// All the fooditems in a category.
	private FoodItem[] items;
	
	// A flag to show removing of an item from shopping cart.
	boolean removed;
	
	// Order number reset.
	private static int orderNumber = 1;

			
	public MenuViewController() {
		
	}
	
	
	/**
	 * Initial actions: starting the creation of the menus.
	 */
	@FXML
	private void initialize() {
		Category[] allCategories = categoryAO.readCategories();
		createCategoryList(allCategories);
		String categoryName = allCategories[0].getName();
		categoryButtonHandler(categoryName);
		setSum();
	}
	
	/**
	 * Method for updating the sum of the shopping cart on the shopping cart list item.
	 * 
	 */
	private void setSum() {
		sumShoppingCart.setText("Summa: " + shoppingCart.getSum() + "0 euroa");
	}

	
	/**
	 * Method for creating the category list menu.
	 * @param categories Categories for creating the categories list menu.
	 */
	private void createCategoryList(Category[] categories) {

		for (int i = 0; i < categories.length; i++) {
			String categoryName = categories[i].getName();
			Button categoryButton = new Button(categoryName);
			int categoryButtonSize = 500 / categories.length;
			categoryButton.setMinSize(250, categoryButtonSize);
			categoryButton.setFont(new Font(25));
			categoryButton.getStyleClass().add("categorybutton");
			
			EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					categoryButtonHandler(categoryName);
				}
			};
			categoryButton.addEventHandler(MouseEvent.MOUSE_PRESSED, eventHandler);
			categoryList.getChildren().add(categoryButton);
		}
	}
	
	/**
	 * Method for reading the food items of the selected category.
	 * @param name Name of the category.
	 */
	private void categoryButtonHandler(String name) {
		items = foodItemAO.readFoodItemsCategory(name);
		if (items.length != 0) {
			createMenu();
		}
		else {
			menu.getChildren().clear();
			Label emptyText = new Label("Pahoittelut! Kategoria on tyhjä!");
			menu.getChildren().add(emptyText);
			emptyText.setFont(new Font(25));
		}
	}
	
	/**
	 * Popup stage for paying the shopping cart.
	 */
	@FXML
	private void readyToPayShoppingCart() {
		Stage readyToPay = new Stage();
		ScrollPane sPane = new ScrollPane();
		VBox readyList = new VBox(10);
		readyList.setPadding(new Insets(10,0,0,10));

		double price;
		int amount;

		Label header = new Label("Valitsemasi tuotteet:");
		header.setFont(new Font(25));
		header.setUnderline(true);
		readyList.getChildren().add(header);

		FoodItem[] items = shoppingCart.getFoodItems();
		for (int i=0; i<items.length; i++) {
			HBox readySingleItem = new HBox();
			amount = shoppingCart.getAmount(items[i].getItemId());
			price = items[i].getPrice();
			Label payItem = new Label(items[i].getName() + ", " + amount + " kpl, hinta yhteensä: " + amount*price + " e");
			payItem.setFont(new Font(14));
			readySingleItem.getChildren().add(payItem);
			Label ingredients = new Label();
			if (getDatabaseIngredients(items[i]) != null) {
				if (items[i].getRemovedIngredientsAsList() != null) {
					String[] removedIngredients =items[i].getRemovedIngredientsAsList();
					String removedIngredientList ="";
					for (int j = 0; j < removedIngredients.length; j++) {
						removedIngredientList += " " + removedIngredients[j].toString();
					}
					ingredients.setText(" poistettu:" + removedIngredientList);
				}
			}
			File file = new File(this.getClass().getResource("/imgs/" + items[i].getPath()).getFile());
			Image image = new Image(file.toURI().toString());
			ImageView iv = new ImageView(image);
			iv.setFitHeight(20);
			iv.setFitWidth(20);
			readySingleItem.getChildren().add(iv);
			readyList.getChildren().addAll(readySingleItem, ingredients);
		}
		Label sumText = new Label("Summa: " + shoppingCart.getSum() + "0 euroa");
		sumText.setFont(new Font(23));
		Button payButton = new Button("Maksa ostokset");
		payButton.setFont(new Font(20));
		payButton.setStyle("-fx-background-color: green;");
		payButton.setTextFill(Color.WHITE);
		
		if (items.length == 0) {
			payButton.setDisable(true);
		}
		Button cancelButton = new Button("Peruuta maksaminen");
		cancelButton.setFont(new Font(17));
		cancelButton.setStyle("-fx-background-color: red;");
		cancelButton.setTextFill(Color.WHITE);
		readyList.getChildren().addAll(sumText, payButton, cancelButton);
		EventHandler<MouseEvent> pay = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Order order = new Order(orderNumber, shoppingCart.getShoppingCart());
				orderAO.createOrder(order);
				HBox popBox = new HBox(1);
				Scene payPopUp = new Scene(popBox);
				readyToPay.setOpacity(0.9);
				
				PauseTransition delay = new PauseTransition(Duration.seconds(5));
				delay.setOnFinished( event -> pay(readyToPay));
				delay.play();
				
				Label payText = new Label("Seuraa maksupäätteen ohjeita!");
				String style = "-fx-background-color: rgba(100, 100, 100, 0.5);";
				popBox.setStyle(style);
				popBox.getChildren().add(payText);
				payText.setFont(new Font(50));
				readyToPay.setScene(payPopUp);
				readyToPay.centerOnScreen();
			}
		};
		payButton.addEventHandler(MouseEvent.MOUSE_PRESSED, pay);
		cancelButton.setOnAction(event -> readyToPay.close());
		sPane.setContent(readyList);

		int heightWindow = 200 + 70*items.length;
		if (heightWindow > 700) {
			heightWindow = 700;
		}
		Scene payScene = new Scene(sPane, 400, heightWindow);
		readyToPay.setScene(payScene);
		readyToPay.initModality(Modality.APPLICATION_MODAL);
		readyToPay.initStyle(StageStyle.UNDECORATED);
		readyToPay.show();
	}
	
	/**
	 * Method for the paying process.
	 * @param s Stage of the paying process.
	 */
	private void pay(Stage s) {
		shoppingCart.emptyShoppingCart();
		shoppingCartList.getChildren().clear();
		setSum();
		orderNumber++;
		s.close();
	}
	
	/**
	 * Method for emptying the shopping cart element in UI and shopping cart object. Also resets the ingredients.
	 */
	@FXML
	private void emptyShoppingCart() {
		Alert options = new Alert(AlertType.CONFIRMATION);
		options.setTitle("Lopetus");
		options.setHeaderText("Haluatko varmasti lopettaa tilauksesi?");
		options.setContentText("Valitse OK tai Peruuta");
	
		ButtonType okayDel = new ButtonType("OK");
		ButtonType cancelDel = new ButtonType("Peruuta");
		
		options.getButtonTypes().setAll(okayDel, cancelDel);
		Optional<ButtonType> result = options.showAndWait();
		
		if (result.get() == okayDel) {
			FoodItem[] allItems = shoppingCart.getFoodItems();
			// If the foodItem in the shopping cart has ingredients, reset the ingredients.
			for (int i = 0; i < allItems.length; i++) {
				if (getDatabaseIngredients(allItems[i]) != null) {
					allItems[i].setIngredients(getDatabaseIngredients(allItems[i]).toArray(new String[getDatabaseIngredients(allItems[i]).size()]));
				}
			}
			shoppingCart.emptyShoppingCart();
			shoppingCartList.getChildren().clear();
			System.out.println(shoppingCart);
		}
		else if (result.get() == cancelDel) {
		}
		setSum();
		
	}

	
	/**
	 * Method for creating the menu items (GridPane elements).
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
				menuItem.getStyleClass().add("menubutton");
				
				// Adding the menubutton (with the picture, text, size, handler) to the menulist.
				File file = new File(this.getClass().getResource("/imgs/" + fItem.getPath()).getFile());
				Image image = new Image(file.toURI().toString());
				ImageView iv = new ImageView(image);
				iv.setFitHeight(150);
				iv.setFitWidth(150);
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
						menuButtonHandler(fItem);
					}
				};
				menuItem.addEventHandler(MouseEvent.MOUSE_PRESSED, eventHandler);
			}
		}
	}
	
	
	/**
	 * Button handler for the menubuttons. Adds items to the shopping cart.
	 * 
	 * @param foodItem The fooditem tied to the particular button.
	 */
	private void menuButtonHandler(FoodItem foodItem) {
		Button sCartItem = new Button("");
		int id = foodItem.getItemId();

		sCartItem.setId(Integer.toString(id));
		sCartItem.setFont(new Font(25));
		sCartItem.setMinSize(375, 60);
		sCartItem.getStyleClass().add("cartbutton");
		
		// Reset the removed ingredients.
		String[] reset = new String[0];
		foodItem.setRemovedIngredients(reset);
		
		// Get all the item numbers of the shopping cart and check whether the item already exists in the shopping cart.
		int[] listOfItemIds= shoppingCart.getAllItemId();
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
			for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
				if (id == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
					shoppingCartList.getChildren().set(i, sCartItem);
				}
			}	
		}
		// Otherwise add the item to the shopping cart.
		else {
			shoppingCart.addToShoppingCart(foodItem, 1);
			shoppingCartList.getChildren().add(sCartItem);
		}

		setSum();
		sCartItem.setText(shoppingCart.getAmount(id) + " x " + foodItem.getName());
		
		// Adding a handler for the shopping cart item buttons.
		System.out.println(shoppingCart);
		sCartItem.setOnAction(event -> editItem(sCartItem, foodItem));
	}
	
	/**
	 * Ingredients of an item are retrieved from the local foodItem object.
	 * @param foodItem Fooditem of which ingredients are retrieved.
	 * @return Ingredients of the local foodItem object.
	 */
	private ArrayList<String> getObjectIngredients(FoodItem foodItem) {
		//String[] ingredientsNames;
		ArrayList<String> ingredientsOfItem;
		
		if (foodItem.getIngredientsAsList() == null ) {
			ingredientsOfItem = null;
		}
		else {
			ingredientsOfItem = new ArrayList<String>(Arrays.asList(foodItem.getIngredientsAsList()));
			/*
			// Checks which ingredients are removable
			for (int i = 0; i < ingredientsNames.length; i++) {
				Ingredient ingredientsAsIngredients= ingredientAO.readIngredientByName(ingredientsNames[i]);
				if (ingredientsAsIngredients.isRemoveable()) {
					ingredientsOfItem.add(ingredientsNames[i]);
				}
			}*/
		}
		return ingredientsOfItem;
	}	
	
	/**
	 * 	Removable ingredients of an item are retrieved from the database ie. original ingredients.
	 * @param foodItem Fooditem of which ingredients are retrieved.
	 * @return Ingredients of the database object.
	 */
	private ArrayList<String> getDatabaseIngredients(FoodItem foodItem) {
		
		ArrayList<String> ingredientsOfItem = new ArrayList<String>();
		String[] ingredientsNames;

		// If foodItem has got no ingredients.
		if (foodItem.getIngredientsAsList() == null ) {
			ingredientsOfItem = null;
		}
		else {
			ingredientsNames = foodItemAO.readFoodItemByName(foodItem.getName()).getIngredientsAsList();

			// Checks which ingredients are removable
			for (int i = 0; i < ingredientsNames.length; i++) {
				Ingredient ingredientsAsIngredients= ingredientAO.readIngredientByName(ingredientsNames[i]);
				if (ingredientsAsIngredients.isRemoveable()) {
					ingredientsOfItem.add(ingredientsNames[i]);
				}
			}
		}
		return ingredientsOfItem;
	}
	

	/**
	 * Method to update the ingredients of a Fooditem.
	 * @param foodItem FoodItem of which ingredients are updated.
	 * @param ingredientName Name of the ingredient being updated.
	 * @param included Boolean representing whether the ingredient is included in the Fooditem or not.
	 */
	private void updateItem(FoodItem foodItem, String ingredientName, boolean included) {
		ArrayList<String> ingredientsOfItem = getObjectIngredients(foodItem);

		String[] removedIngredients;

		if (foodItem.getRemovedIngredientsAsList() == null) {
			removedIngredients = null;
		} else {
			removedIngredients = foodItem.getRemovedIngredientsAsList();
		}
		
		// If checkbox has been chosen.
		if (included) {
			ingredientsOfItem.add(ingredientName);
			foodItem.setIngredients(ingredientsOfItem.toArray(new String[ingredientsOfItem.size()]));
			
			for (int i = 0; i < removedIngredients.length; i++) {
				if (removedIngredients[i].equals(ingredientName)) {
					removedIngredients[i] = null;
				}
			}
			foodItem.setRemovedIngredients(removedIngredients);
			
		}
		// If checkbox has been unchosen.
		else if (!included) {
			for (int i = 0; i < ingredientsOfItem.size(); i++) {

				if (ingredientsOfItem.get(i).equals(ingredientName)) {

					ingredientsOfItem.remove(i);
					foodItem.setIngredients(ingredientsOfItem.toArray(new String[ingredientsOfItem.size()]));
					String[] listRemoved;
					
					if (removedIngredients != null) {

						int size = removedIngredients.length;
						listRemoved = new String[size + 1];
						for (int j = 0; j < size; j++) {
							listRemoved[j] = removedIngredients[j];
						}
						listRemoved[size] = ingredientName;					
					} else {
						listRemoved = new String[1];
						listRemoved[0] = ingredientName;					
					}
					foodItem.setRemovedIngredients(listRemoved);
					//System.out.println("getRemovedIngredientsAsList on " + Arrays.toString(foodItem.getRemovedIngredientsAsList()));
				}
			}
		}
	}
	
	/**
	 * Method for the edit menu of the shopping list item
	 * 
	 * @param button Button of the item in the shopping cart.
	 * @param foodItem The foodItem connected to that particular button.
	 */	
	private void editItem(Button button, FoodItem foodItem) {
		Stage popUp = new Stage();
		int amountNow = shoppingCart.getAmount(foodItem.getItemId());
		int originalAmount = amountNow;
		
		Label nameAndAmount = new Label("Valitse " + foodItem.getName() + " määrä: ");
		nameAndAmount.setFont(new Font(18));
		nameAndAmount.setPadding(new Insets(0,0,0,20));
		Label pick = new Label(Integer.toString(amountNow));
		pick.setFont(new Font(20));
		pick.setPadding(new Insets(0,0,0,20));
		HBox boxInfo = new HBox(20);
		
		VBox boxWhole = new VBox(20);
		HBox boxButtons = new HBox(20);
		boxButtons.setPadding(new Insets(10,0,0,10));
		HBox boxOkCancel = new HBox(20);
		boxOkCancel.setPadding(new Insets(10,0,0,10));

		VBox boxIngredient = new VBox(20);
		boxIngredient.setPadding(new Insets(10,0,0,10));
		
		ArrayList<String> ingredientsOfObject;
		// Database ingredients.
		ArrayList<String> ingredientsOfDatabase = getDatabaseIngredients(foodItem);

		
		// If the item has ingredients, create ingredient list.
		if (ingredientsOfDatabase != null) {
			//System.out.println("ingredientsOfDatabase on " + ingredientsOfDatabase);
			
			// If object has been removed, ingredients are retrieved from database.
			if (removed) {
				ingredientsOfObject = ingredientsOfDatabase;
			} else {
				// Otherwise the local ingredients are retrieved.
				ingredientsOfObject = getObjectIngredients(foodItem);
			}
			removed=false;
			foodItem.setIngredients(ingredientsOfObject.toArray(new String[ingredientsOfObject.size()]));
			Label header = new Label("Ainesosat:");
			header.setFont(new Font(17));
			boxIngredient.getChildren().add(header);

			for (int j = 0; j < ingredientsOfDatabase.size(); j++) {
				HBox boxIngredient2 = new HBox(20);
				String name = ingredientsOfDatabase.get(j);
				Label newIngredient = new Label(name);
				CheckBox included = new CheckBox();
				
				// Comparing local ingredients to the database ingredients. If ingredient has not been deleted, mark checkbox.
				if (ingredientsOfObject.contains(ingredientsOfDatabase.get(j)))
				{
					included.setSelected(true);
				}
				// Checkbox listener
				ChangeListener<Object> listener = (obs, oldValue, newValue) ->
			
					updateItem(foodItem, name, included.isSelected());
					
				included.selectedProperty().addListener(listener);
				
				boxIngredient2.getChildren().addAll(newIngredient, included);
				boxIngredient.getChildren().add(boxIngredient2);
			}
		}
		
		// Other buttons
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

				for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
					if (foodItem.getItemId() == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
						shoppingCartList.getChildren().remove(i);
					}
				}
				removed = true;
				setSum();
				popUp.close();
			}
			else if (result.get() == cancelDel) {
			}

		});
		okay.setOnAction(event -> {
			button.setText(shoppingCart.getAmount(foodItem.getItemId()) + " x " + foodItem.getName());
			setSum();

			for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
				if (foodItem.getItemId() == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
					shoppingCartList.getChildren().set(i, button);
				}
			}
			popUp.close();
		});
		cancel.setOnAction(event -> {
			shoppingCart.setAmount(foodItem.getItemId(), originalAmount);
			popUp.close();
		});
		
		boxInfo.getChildren().addAll(nameAndAmount, pick);
		boxButtons.getChildren().addAll(increase, decrease, delete);
		boxOkCancel.getChildren().addAll(okay, cancel);
		
		boxWhole.getChildren().addAll(boxInfo, boxButtons, boxIngredient, boxOkCancel);
		Scene popUpScene = new Scene(boxWhole, 400, 600);
		popUp.setScene(popUpScene);
		popUp.initModality(Modality.APPLICATION_MODAL);
		popUp.show();
		
		
		System.out.println(shoppingCart);
	}

}
