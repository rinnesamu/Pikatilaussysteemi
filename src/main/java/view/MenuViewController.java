package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import application.IStart;
import controller.CustomerController;
import controller.ICustomerController;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
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
import model.FoodItem;
import util.Bundle;

/**
 * Controller for the customer user interface.
 * 
 * @author Kimmo Perälä
 *
 */

public class MenuViewController implements IMenuView {
	
	private IStart start;
	private ICustomerController controller;
	
	// Element where the menu is located.
	@FXML
	private FlowPane menu;

	// Category list element.
	@FXML
	private VBox categoryList;
	
	// Shopping cart list element.
	@FXML
	private VBox shoppingCartList;
	
	// Button element for emptying the shopping cart.
	@FXML
	private Button emptyButton;
	
	// Button element for paying the shopping cart.
	@FXML
	private Button buyButton;
	
	// Sum element of the shopping cart list.
	@FXML
	private Label sumShoppingCart;
	
	// All the fooditems in a category.
	private FoodItem[] items;
	
	// Order number reset.
	private static int orderNumber = 1;

	// ResourceBundle for language selection
	ResourceBundle bundle;
			
	public MenuViewController() {
		
	}
	
	public void setStart(IStart start) {
		 this.start = start;
	 }
	
	
	/**
	 * Setter for all foodItems in selected category.
	 */
	public void setItems(FoodItem[] items) {
		this.items = items;
	}
	
	/**
	 * If there are no categories in database, the method informs the user.
	 */
	public void emptyCategory() {
		menu.getChildren().clear();
		Label emptyText = new Label(bundle.getString("emptyCategory"));
		menu.getChildren().add(emptyText);
		emptyText.setFont(new Font(25));
	}
	
	/**
	 * Initial actions: starting the creation of the menus.
	 */
	@FXML
	private void initialize() {
		this.controller = new CustomerController(this);
		bundle = Bundle.getInstance();

		controller.initMenu();
		
	}
	
	/**
	 * Method for updating the sum of the shopping cart on the shopping cart list item.
	 * 
	 */
	public void setSum() {
		sumShoppingCart.setText(bundle.getString("sumText") + ": " + controller.getShoppingCartSum() + "0 " + bundle.getString("eurosText"));
	}

	/**
	 *
	 * Method for creating the category list menu.
	 * @param categories Categories for creating the categories list menu.
	 */
	public void createCategoryList(Category[] categories) {

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
					controller.readCategories(categoryName);
				}
			};
			categoryButton.addEventHandler(MouseEvent.MOUSE_PRESSED, eventHandler);
			categoryList.getChildren().add(categoryButton);
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
		Label header = new Label(bundle.getString("chosenProducts"));
		header.setFont(new Font(25));
		header.setUnderline(true);
		readyList.getChildren().add(header);
		String infoIngredient = "";
		FoodItem[] shoppingCartItems = controller.getFoodItems();
		for (int i=0; i<shoppingCartItems.length; i++) {
			HBox readySingleItem = new HBox();
			amount = controller.getAmount(shoppingCartItems[i].getItemId());
			price = shoppingCartItems[i].getPrice();
			Label payItem = new Label(shoppingCartItems[i].getName() + ", " + amount + " " + bundle.getString("summaryText") + " " + amount*price + "0 e");
			payItem.setFont(new Font(14));
			readySingleItem.getChildren().add(payItem);
			Label ingredients = new Label();
			if (controller.getDatabaseIngredients(shoppingCartItems[i]) != null) {
				if (shoppingCartItems[i].getRemovedIngredientsAsList() != null) {
					String[] removedIngredients =shoppingCartItems[i].getRemovedIngredientsAsList();
					String removedIngredientList ="";
					for (int j = 0; j < removedIngredients.length; j++) {
						removedIngredientList += removedIngredients[j].toString() + " ";
					}
					ingredients.setText(" " + bundle.getString("removedText") + " " + removedIngredientList);
					infoIngredient += shoppingCartItems[i].getItemId() + "=" + removedIngredientList;
				}
			}
			File file = new File(this.getClass().getResource("/imgs/" + shoppingCartItems[i].getPath()).getFile());
			Image image = new Image(file.toURI().toString());
			ImageView iv = new ImageView(image);
			iv.setFitHeight(20);
			iv.setFitWidth(20);
			readySingleItem.getChildren().add(iv);
			readyList.getChildren().addAll(readySingleItem, ingredients);
		}
		Label sumText = new Label(bundle.getString("sumText") + ": " + controller.getShoppingCartSum() + "0 " + bundle.getString("eurosText"));
		sumText.setFont(new Font(23));
		Button payButton = new Button(bundle.getString("payShopcartText"));
		payButton.setFont(new Font(20));
		payButton.setStyle("-fx-background-color: green;");
		payButton.setTextFill(Color.WHITE);
		
		if (shoppingCartItems.length == 0) {
			payButton.setDisable(true);
		}
		Button cancelButton = new Button(bundle.getString("cancelText"));
		cancelButton.setFont(new Font(17));
		cancelButton.setStyle("-fx-background-color: red;");
		cancelButton.setTextFill(Color.WHITE);
		String infoIngredient2 = infoIngredient;
		readyList.getChildren().addAll(sumText, payButton, cancelButton);
		EventHandler<MouseEvent> pay = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				controller.createOrder(orderNumber, controller.getShoppingCart(), infoIngredient2);
				//Order order = new Order(orderNumber, shoppingCart.getShoppingCart());
				//order.setAdditionalInfo(infoIngredient2);
				//orderAO.createOrder(order);
				HBox popBox = new HBox(1);
				Scene payPopUp = new Scene(popBox);
				readyToPay.setOpacity(0.9);
				
				PauseTransition delay = new PauseTransition(Duration.seconds(5));
				delay.setOnFinished( event -> pay(readyToPay));
				delay.play();
				
				Label payText = new Label(bundle.getString("paymentTerminalText"));
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

		int heightWindow = 200 + 70*shoppingCartItems.length;
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
		controller.emptyShoppingCart();
		shoppingCartList.getChildren().clear();
		setSum();
		orderNumber++;
		s.close();
		start.initUI();
	}
	
	/**
	 * Method for emptying the shopping cart element in UI and shopping cart object. Also resets the ingredients.
	 */
	@FXML
	private void emptyShoppingCart() {
		Alert options = new Alert(AlertType.CONFIRMATION);
		options.setTitle(bundle.getString("cancellationText"));
		options.setHeaderText(bundle.getString("cancellationQuestion"));
		options.setContentText(bundle.getString("cancellationChoice"));
	
		ButtonType okayDel = new ButtonType(bundle.getString("okayText"));
		ButtonType cancelDel = new ButtonType(bundle.getString("cancelText"));
		
		options.getButtonTypes().setAll(okayDel, cancelDel);
		Optional<ButtonType> result = options.showAndWait();
		
		if (result.get() == okayDel) {
			controller.emptyShoppingCart();
			shoppingCartList.getChildren().clear();
			System.out.println(controller.shoppingCartToString());
		}
		else if (result.get() == cancelDel) {
		}
		setSum();
		
	}

	
	/**
	 * Method for creating the menu items (GridPane elements).
	 */
	public void createMenu() {
		menu.getChildren().clear();
		for (int i = 0; i < items.length; i++) {
			if (items[i].isInMenu()) {
				// Creating new menuItem.
				GridPane menuItem = new GridPane();

				// FoodItem from the category
				FoodItem fItem = items[i];
				menuItem.getStyleClass().add("menubutton");
				File file;
				// Adding the menubutton (with the picture, text, size, handler) to the menulist.
				try {
					file = new File(this.getClass().getResource("/imgs/" + fItem.getPath()).getFile());
				} catch (Exception e) {
					fItem.setPath("defaultpic.jpg");
					file = new File(this.getClass().getResource("/imgs/" + fItem.getPath()).getFile());
				}
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
	 *  Button handler for the menubuttons. Adds items to the shopping cart.
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
		
		// Get all the item numbers of the shopping cart and check whether the item already exists in the shopping cart.
		int[] listOfItemIds= controller.getAllItemId();
		boolean found = false;
		for (int i = 0; i < listOfItemIds.length; i++) {
			if (id == listOfItemIds[i]) {
				found = true;
			}	
		}
		// If item is already there, increase the amount in the shopping cart.
		if (found) {
			int oldAmount = controller.getAmount(id);
			controller.setAmount(foodItem.getItemId(), (oldAmount+1));
			
			for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
				if (id == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
					shoppingCartList.getChildren().set(i, sCartItem);
				}
			}
		}
		// Otherwise add the item to the shopping cart.
		else {
			// If item has ingredients, reset ingredients and removed ingredients.
			if (foodItem.getIngredientsAsList() != null) {
				
				// Reset the removed ingredients.
				String[] reset = new String[0];
				foodItem.setRemovedIngredients(reset);
				
				// Reset ingredients.
				foodItem.setIngredients(controller.getDatabaseIngredients(foodItem).toArray(new String[controller.getDatabaseIngredients(foodItem).size()]));
			}
			controller.addToShoppingCart(foodItem, 1);
			shoppingCartList.getChildren().add(sCartItem);
		}

		setSum();
		
		sCartItem.setText(controller.getAmount(id) + " x " + foodItem.getName());
		
		// Adding a handler for the shopping cart item buttons.
		System.out.println(controller.shoppingCartToString());
		sCartItem.setOnAction(event -> editItem(sCartItem, foodItem));
	}
	
	/**
	 * Ingredients of an item are retrieved from the local foodItem object.
	 * @param foodItem Fooditem of which ingredients are retrieved.
	 * @return Ingredients of the local foodItem object.
	 */
	private ArrayList<String> getObjectIngredients(FoodItem foodItem) {
		ArrayList<String> ingredientsOfItem;
		
		// If foodItem has no ingredients, return empty ArrayList for possible addable ingredients (which were removed earlier in the shopping cart).
		if (foodItem.getIngredientsAsList() == null ) {
			ingredientsOfItem = new ArrayList<String>();
			return ingredientsOfItem;
		}
		// Otherwise return the ingredients of the foodItem.
		else {
			ingredientsOfItem = new ArrayList<String>(Arrays.asList(foodItem.getIngredientsAsList()));
			Collections.sort(ingredientsOfItem);
			return ingredientsOfItem;
		}
	}	
	
	
	/**
	 * Method to update the ingredients of a Fooditem.
	 * @param foodItem FoodItem of which ingredients are updated.
	 * @param ingredientName Name of the ingredient being updated.
	 * @param included Boolean representing whether the ingredient is included in the Fooditem or not.
	 */
	private void updateItem(FoodItem foodItem, String ingredientName, boolean included) {
		// Get the foodItem's ingredients.
		ArrayList<String> ingredientsOfItem = getObjectIngredients(foodItem);

		String[] removedIngredients = foodItem.getRemovedIngredientsAsList();
		
		// If checkbox has been chosen.
		if (included) {
			// Add the ingredient to the fooditem.
			ingredientsOfItem.add(ingredientName);
			foodItem.setIngredients(ingredientsOfItem.toArray(new String[ingredientsOfItem.size()]));
			
			// Take that ingredient out of removedIngredients.
			for (int i = 0; i < removedIngredients.length; i++) {
				if (removedIngredients[i].equals(ingredientName)) {
					removedIngredients[i] = null;
				}
			}
			foodItem.setRemovedIngredients(removedIngredients);
			
		}
		// If checkbox has been unchosen.
		else if (!included) {
			// Remove the ingredient from the fooditem.
			ingredientsOfItem.remove(ingredientName);
			foodItem.setIngredients(ingredientsOfItem.toArray(new String[ingredientsOfItem.size()]));
			String[] listRemoved;
			
			// Add that ingredient to the removed ingredients.
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
			System.out.println("getRemovedIngredientsAsList on " + Arrays.toString(foodItem.getRemovedIngredientsAsList()));
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
		int amountNow = controller.getAmount(foodItem.getItemId());
		int originalAmount = amountNow;
		
		Label nameAndAmount = new Label(String.format(bundle.getString("chooseText") + " %s " + bundle.getString("amountText") + ": ", foodItem.getName() ));
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


		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabPane.setTabMinHeight(100);
		
		// Database ingredients.
		ArrayList<String> ingredientsOfDatabase = controller.getDatabaseIngredients(foodItem);

		// If the item has ingredients, create ingredient list.
		if (ingredientsOfDatabase != null) {
			
			for (int i = 0; i < controller.getAmount(foodItem.getItemId()); i++) {
				
				Tab tab = new Tab(bundle.getString("productText") + " " + (i+1));
				VBox boxIngredient = new VBox(20);
				boxIngredient.setPadding(new Insets(10,0,0,10));
				// Local ingredients.
				ArrayList<String> ingredientsOfObject = getObjectIngredients(foodItem);
				
				System.out.println("ingredientsOfDatabase on " + ingredientsOfDatabase);
				System.out.println("ingredientsOfObject on " + ingredientsOfObject);
	
				Label header = new Label(bundle.getString("ingredientsText") + " " + bundle.getString("productText") + " " + (i+1));
				header.setFont(new Font(17));
				boxIngredient.getChildren().add(header);
				//FoodItem newItem = new FoodItem(foodItem.getName(), foodItem.getPrice(), true, newId);
	
				for (int j = 0; j < ingredientsOfDatabase.size(); j++) {
					HBox boxIngredient2 = new HBox(20);
					String name = ingredientsOfDatabase.get(j);
					Label newIngredient = new Label(name);
					CheckBox included = new CheckBox();
					
					// Comparing local ingredients to the database ingredients. If ingredient has not been deleted, mark check for checkbox (included).
					
					if (ingredientsOfObject == null) {
						
					}else if(ingredientsOfObject.contains(ingredientsOfDatabase.get(j)))
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
				tab.setContent(boxIngredient);
				tabPane.getTabs().add(tab);
			}
		}
		
		// Other buttons
		Button increase = new Button("+");
		increase.setFont(new Font(40));
		increase.setMinSize(80, 80);
		Button decrease = new Button("-");
		decrease.setFont(new Font(40));
		decrease.setMinSize(80, 80);
		Button delete = new Button(bundle.getString("removebigText"));
		delete.setStyle("-fx-background-color: #ff0000;");
		delete.setFont(new Font(20));
		delete.setMinSize(80, 80);
		Button okay = new Button(bundle.getString("okayText"));
		okay.setFont(new Font(20));
		okay.setMinSize(80, 80);
		Button cancel = new Button(bundle.getString("cancelText"));
		cancel.setFont(new Font(20));
		cancel.setMinSize(80, 80);
		
		increase.setOnAction(event -> {
			int amount = controller.getAmount(foodItem.getItemId());
			amount += 1;
			pick.setText(Integer.toString(amount));
			controller.setAmount(foodItem.getItemId(), amount);
		});
		decrease.setOnAction(event -> {
			int amount = controller.getAmount(foodItem.getItemId());
			if (amount != 1) {
				amount -= 1;
			}
			pick.setText(Integer.toString(amount));
			controller.setAmount(foodItem.getItemId(), amount);
		});
		delete.setOnAction(event -> {
			Alert options = new Alert(AlertType.CONFIRMATION);
			options.setTitle(bundle.getString("removalText"));
			options.setHeaderText(bundle.getString("deleteConfirmText") + " " + foodItem.getName() + " " + bundle.getString("fromCartText"));
		
			ButtonType okayDel = new ButtonType("OK");
			ButtonType cancelDel = new ButtonType("Cancel");
			
			options.getButtonTypes().setAll(okayDel, cancelDel);
			Optional<ButtonType> result = options.showAndWait();
			
			if (result.get() == okayDel) {
				controller.removeFromShoppingCart(foodItem);

				for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
					if (foodItem.getItemId() == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
						shoppingCartList.getChildren().remove(i);
					}
				}
				setSum();
				popUp.close();
			}
			else if (result.get() == cancelDel) {
			}

		});
		okay.setOnAction(event -> {
			button.setText(controller.getAmount(foodItem.getItemId()) + " x " + foodItem.getName());
			setSum();
			popUp.close();
		});
		cancel.setOnAction(event -> {
			controller.setAmount(foodItem.getItemId(), originalAmount);
			popUp.close();
		});
		
		boxInfo.getChildren().addAll(nameAndAmount, pick);
		boxButtons.getChildren().addAll(increase, decrease, delete);
		boxOkCancel.getChildren().addAll(okay, cancel);
		
		boxWhole.getChildren().addAll(boxInfo, boxButtons, tabPane, boxOkCancel);
		Scene popUpScene = new Scene(boxWhole, 400, 600);
		popUp.setScene(popUpScene);
		popUp.initModality(Modality.APPLICATION_MODAL);
		popUp.show();
		
		
		System.out.println(controller.shoppingCartToString());
	}

}
