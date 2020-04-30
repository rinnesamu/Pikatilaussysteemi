package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;


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
import javafx.scene.control.Tooltip;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

public class MenuView implements IMenuView {
	
	// The start view to return to.
	private IStart start;
	
	// The controller of the menu view.
	private ICustomerController controller;
	
	// Element where the menu is located.
	@FXML
	private FlowPane menu;

	// Category list element.
	@FXML
	private VBox categoryList;
	
	// Top left element for restaurant logo.
	@FXML
	private Pane leftPart;

	// Top right element for NoQ logo.
	@FXML
	private Pane rightPart;

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
	
	// The variable of the negative id numbers for the foodItems with ingredients.
	private int newId=0;
	
	// Order number reset.
	private static int orderNumber = 1;

	// ResourceBundle for language selection
	ResourceBundle bundle;
			
	public MenuView() {
		
	}
	
	/**
	 * Setter for the start view.
	 * @param start The start view.
	 */
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
	 * If there are no categories in the database, the method informs the user.
	 */
	public void noCategories() {
		menu.getChildren().clear();
		Label noCategoriesText = new Label(bundle.getString("noCategories"));
		menu.getChildren().add(noCategoriesText);
		noCategoriesText.setFont(new Font(25));
	}

	/**
	 * If category is empty in the database, the method informs the user.
	 */
	public void emptyCategory() {
		menu.getChildren().clear();
		Label emptyText = new Label(bundle.getString("emptyCategory"));
		menu.getChildren().add(emptyText);
		emptyText.setFont(new Font(25));
	}
	
	/**
	 * Initial actions: starting the creation of the menus with the controller.
	 */
	@FXML
	private void initialize() {
		this.controller = new CustomerController(this);
		bundle = Bundle.getInstance();
		
		File file1 = new File(this.getClass().getResource("/imgs/logo.png").getFile());
		Image image1 = new Image(file1.toURI().toString());
		ImageView iv1 = new ImageView(image1);
		iv1.setFitHeight(80);
		iv1.setFitWidth(80);
		leftPart.getChildren().add(iv1);
		
		File file2 = new File(this.getClass().getResource("/imgs/rect4547.png").getFile());
		Image image2 = new Image(file2.toURI().toString());
		ImageView iv2 = new ImageView(image2);
		iv2.setFitHeight(80);
		iv2.setFitWidth(80);
		rightPart.getChildren().add(iv2);

		controller.initMenu();
	}
	
	/**
	 * Method for updating the sum of the shopping cart on the shopping cart list item.
	 * 
	 */
	public void setSum(double value) {
		sumShoppingCart.setText(bundle.getString("sumText") + ": " + value + "0 " + bundle.getString("eurosText"));
	}
	
	public void setElementRemovedIngredients(Object observable, String removedIngredients) {
		FoodItem foodItem = (FoodItem) observable;
		int id = foodItem.getItemId();
		System.out.println("ID ON " + id);
		/*		
		for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
			if (id == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
				shoppingCartList.getChildren().set(i, itemBox);
			}
		}*/
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
			int categoryButtonHeight = 600 / categories.length;
			categoryButton.setMinSize(230, categoryButtonHeight);
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
			Label ingredients = new Label();
			// if item has ingredients (null exception)
			if (controller.getDatabaseIngredients(shoppingCartItems[i]) != null) {
				// if item has removed ingredients
				if (shoppingCartItems[i].getRemovedIngredientsAsList() != null) {
					String[] removedIngredients =shoppingCartItems[i].getRemovedIngredientsAsList();
					String removedIngredientList ="";
					for (int j = 0; j < removedIngredients.length; j++) {
						removedIngredientList += removedIngredients[j].toString() + " ";
					}
					ingredients.setText(" " + bundle.getString("removedText") + removedIngredientList);
					infoIngredient += controller.getAmount(shoppingCartItems[i].getItemId()) + "*" + shoppingCartItems[i].getName() + "=" + removedIngredientList;
				}
			}
			File file = new File(this.getClass().getResource("/imgs/" + shoppingCartItems[i].getPath()).getFile());
			Image image = new Image(file.toURI().toString());
			ImageView iv = new ImageView(image);
			iv.setFitHeight(20);
			iv.setFitWidth(20);
			readySingleItem.getChildren().addAll(payItem, iv);
			readyList.getChildren().addAll(readySingleItem, ingredients);
		}
		Label sumText = new Label(bundle.getString("sumText") + ": " + controller.getShoppingCartSum() + "0 " + bundle.getString("eurosText"));
		sumText.setFont(new Font(23));
		CheckBox takeaway = new CheckBox("Takeaway?");
		takeaway.setMinSize(50, 50);
		Button payButton = new Button(bundle.getString("payShopcartText"));
		payButton.setFont(new Font(20));
		payButton.setStyle("-fx-background-color: green;");
		payButton.setTextFill(Color.WHITE);
		
		if (shoppingCartItems.length == 0) {
			payButton.setDisable(true);
			takeaway.setDisable(true);
		}
		Button cancelButton = new Button(bundle.getString("cancelText"));
		cancelButton.setFont(new Font(17));
		cancelButton.setStyle("-fx-background-color: red;");
		cancelButton.setTextFill(Color.WHITE);
		String infoIngredient2 = infoIngredient;
		readyList.getChildren().addAll(sumText, takeaway, payButton, cancelButton);
		EventHandler<MouseEvent> pay = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				controller.createOrder(orderNumber, controller.getShoppingCart(), infoIngredient2, takeaway.isSelected());
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

		int heightWindow = 240 + 70*shoppingCartItems.length;
		if (heightWindow > 700) {
			heightWindow = 700;
		}
		Scene payScene = new Scene(sPane, 600, heightWindow);
		payScene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			if(start != null)
				start.timeoutWake();
		});
		readyToPay.setScene(payScene);
		readyToPay.initModality(Modality.APPLICATION_MODAL);
		readyToPay.initStyle(StageStyle.UNDECORATED);
		readyToPay.show();
	}
	
	/**
	 * Method for the paying process. At the end brings the user back to the start view.
	 * @param s Stage of the paying process.
	 */
	private void pay(Stage s) {
		controller.emptyShoppingCart();
		shoppingCartList.getChildren().clear();
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
		options.setTitle(bundle.getString("emptyingText"));
		options.setHeaderText(bundle.getString("emptyingQuestion"));
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
		// Shopping cart element button properties
		Button sCartItem = new Button("");
		sCartItem.setFont(new Font(15));
		sCartItem.setMinSize(390, 60);
		sCartItem.getStyleClass().add("cartbutton");
		
		// Labels for the shopping cart element buttons (amount + price & removed ingredients)
		Label itemNameLabel = new Label();
		Label itemIngredientsLabel = new Label();
		
		Button increase = new Button("+");
		increase.setFont(new Font(20));
		increase.setMinSize(50, 60);
		increase.getStyleClass().add("amountbutton");
		
		Button decrease = new Button("-");
		decrease.setFont(new Font(20));
		decrease.setMinSize(50, 60);
		decrease.getStyleClass().add("amountbutton");
		decrease.setTooltip(
				new Tooltip(bundle.getString("minusInfoText"))
		);
		
		Button delete = new Button("X");
		delete.setFont(new Font(20));
		delete.setMinSize(50, 60);
		delete.getStyleClass().add("deletebutton");
		
		HBox itemBox = new HBox(sCartItem);

		// If item has ingredients, create a fooditem with negative id number, reset ingredients and removed ingredients of the fooditem.

		if (foodItem.getIngredientsAsList() != null) {
			
			sCartItem.setMinSize(340, 60);

			Button modify = new Button();
			modify.setMinSize(50, 60);
			modify.getStyleClass().add("modifybutton");
			itemBox.getChildren().addAll(modify, increase, decrease, delete);
			
			//Create a new FoodItem copy with negative id, starting from -1.
			newId -= 1;
			FoodItem newItem = new FoodItem(foodItem.getName(), foodItem.getPrice(), true, newId);
			
			// Create observer for each FoodItem with ingredients added to the shopping cart.
			//controller.createFoodItemObserver(newItem);
			
			// Set the right image path.
			newItem.setPath(foodItem.getPath());
			
			// Reset the removed ingredients.
			String[] reset = new String[0];
			newItem.setRemovedIngredients(reset);
			
			// Reset ingredients.
			newItem.setIngredients(controller.getDatabaseIngredients(foodItem).toArray(new String[controller.getDatabaseIngredients(foodItem).size()]));
			controller.addToShoppingCart(newItem, 1);
			
			// Set negative id of newItem
			itemBox.setId(Integer.toString(newId));
									
			itemNameLabel.setText(controller.getAmount(newItem.getItemId()) + " x " + newItem.getName());
			sCartItem.setText(itemNameLabel.getText() + "\n" + itemIngredientsLabel.getText());
			
			shoppingCartList.getChildren().add(itemBox);
			
			// Adding a handler for the shopping cart item buttons.
			System.out.println(controller.shoppingCartToString());
			modify.setOnAction(event -> editItem(sCartItem, newItem));
			
			increase.setOnAction(event -> {
				int amount = controller.addOneToShoppingCart(newItem);
				String removedIngredients =newItem.getRemovedIngredientsAsString();
				// Set the item's name and amount + possible ingredients to shopping cart element button.
				if (removedIngredients.length() != 0) {
					itemIngredientsLabel.setText(bundle.getString("removedText") + removedIngredients);
				} else {
					itemIngredientsLabel.setText("");
				}
				sCartItem.setText(amount + " x " + newItem.getName() + "\n" + itemIngredientsLabel.getText());
			});
			decrease.setOnAction(event -> {
				int amount = controller.removeOneFromShoppingCart(newItem);
				String removedIngredients2 =newItem.getRemovedIngredientsAsString();
				if (removedIngredients2.length() != 0) {
					itemIngredientsLabel.setText(bundle.getString("removedText") + removedIngredients2);
				} else {
					itemIngredientsLabel.setText("");
				}
				sCartItem.setText(amount + " x " + newItem.getName() + "\n" + itemIngredientsLabel.getText());
			});
			delete.setOnAction(event -> {
				Alert options = new Alert(AlertType.CONFIRMATION);
				options.setTitle(bundle.getString("removalText"));
				options.setHeaderText(bundle.getString("deleteConfirmText") + " " + newItem.getName() + " " + bundle.getString("fromCartText"));
			
				ButtonType okayDel = new ButtonType("OK");
				ButtonType cancelDel = new ButtonType("Cancel");
				
				options.getButtonTypes().setAll(okayDel, cancelDel);
				Optional<ButtonType> result = options.showAndWait();
				
				if (result.get() == okayDel) {
					controller.removeFromShoppingCart(newItem);

					for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
						if (newItem.getItemId() == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
							shoppingCartList.getChildren().remove(i);
						}
					}
				}
			});
			
		} 
		// If item does not have ingredients, add the original foodItem.
		else {
			itemBox.getChildren().addAll(increase, decrease, delete);
			int id = foodItem.getItemId();
			// Get all the item numbers of the shopping cart and check whether the item already exists in the shopping cart.
			int[] listOfItemIds= controller.getAllItemId();
			boolean found = false;
			for (int i = 0; i < listOfItemIds.length; i++) {
				if (id == listOfItemIds[i]) {
					found = true;
				}	
			}
			
			// Set id of foodItem
			itemBox.setId(Integer.toString(id));
			
			// If item is already there, increase the amount in the shopping cart.
			if (found) {
				int oldAmount = controller.getAmount(id);
				controller.setAmount(foodItem.getItemId(), (oldAmount+1));
				
				for (int i = 0; i < shoppingCartList.getChildren().size(); i++) {
					if (id == Integer.parseInt(shoppingCartList.getChildren().get(i).getId())) {
						shoppingCartList.getChildren().set(i, itemBox);
					}
				}
			}
			// Otherwise add a new element to the shopping cart.
			else {
				controller.addToShoppingCart(foodItem, 1);
				shoppingCartList.getChildren().add(itemBox);
			}
			itemNameLabel.setText(controller.getAmount(foodItem.getItemId()) + " x " + foodItem.getName());
			sCartItem.setText(itemNameLabel.getText() + "\n" + itemIngredientsLabel.getText());

			// Adding a handler for the shopping cart item buttons.
			/*System.out.println(controller.shoppingCartToString());
			sCartItem.setOnAction(event -> editItem(sCartItem, foodItem));*/
			
			increase.setOnAction(event -> {
				int amount = controller.addOneToShoppingCart(foodItem);

				// Set the item's amount and name
				sCartItem.setText(amount + " x " + foodItem.getName());
			});
			decrease.setOnAction(event -> {
				int amount = controller.removeOneFromShoppingCart(foodItem);

				// Set the item's amount and name
				sCartItem.setText(amount + " x " + foodItem.getName());
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
				}

			});
		}
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
		System.out.println("getshopc on " + controller.getShoppingCart());
		int height = 500; // default height of the popup
		
		Label nameLabel = new Label(foodItem.getName());
		nameLabel.setFont(new Font(18));
		nameLabel.setPadding(new Insets(10,0,0,10));
		
		VBox boxWhole = new VBox(20);
		HBox boxOkCancel = new HBox(20);
		boxOkCancel.setPadding(new Insets(0,0,0,10));

		VBox boxIngredient = new VBox(20);
		//TabPane tabPane = new TabPane();
		//tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		//tabPane.setTabMinHeight(100);
		
		// Database ingredients.
		ArrayList<String> ingredientsOfDatabase = controller.getDatabaseIngredients(foodItem);
		
		//for (int i = 0; i < controller.getAmount(foodItem.getItemId()); i++) {
		
		//Tab tab = new Tab(bundle.getString("productText") + " " + (i+1));
		boxIngredient.setPadding(new Insets(10,0,0,10));
		// Local ingredients.
		ArrayList<String> ingredientsOfObject = getObjectIngredients(foodItem);
		
		System.out.println("ingredientsOfDatabase on " + ingredientsOfDatabase);
		System.out.println("ingredientsOfObject on " + ingredientsOfObject);

		Label header = new Label(bundle.getString("ingredientsText"));
		header.setFont(new Font(17));
		boxIngredient.getChildren().add(header);

		for (int j = 0; j < ingredientsOfDatabase.size(); j++) {
			HBox boxIngredient2 = new HBox(20);
			String name = ingredientsOfDatabase.get(j);
			Label newIngredient = new Label(name);
			CheckBox included = new CheckBox();
			
			// Comparing local ingredients to the database ingredients. If ingredient has not been deleted, mark check for checkbox (included).
			
			if(ingredientsOfObject != null && ingredientsOfObject.contains(ingredientsOfDatabase.get(j)))
			{
				included.setSelected(true);
			}
			
			// Checkbox listener
			ChangeListener<Object> listener = (obs, oldValue, newValue) ->
		
				updateItem(foodItem, name, included.isSelected());
				
			included.selectedProperty().addListener(listener);
			
			boxIngredient2.getChildren().addAll(newIngredient, included);
			boxIngredient.getChildren().add(boxIngredient2);
			//}
			//tab.setContent(boxIngredient);
			//tabPane.getTabs().add(tab);
		}
		
		Button okay = new Button(bundle.getString("okayText"));
		okay.setFont(new Font(20));
		okay.setMinSize(80, 80);
		/*Button cancel = new Button(bundle.getString("cancelText"));
		cancel.setFont(new Font(20));
		cancel.setMinSize(80, 80);*/
		
		okay.setOnAction(event -> {
			String removedIngredients =foodItem.getRemovedIngredientsAsString();
			Label itemIngredientsLabel = new Label();

			// Set the item's name and amount + possible ingredients to shopping cart element button.
			if (removedIngredients.length() != 0) {
				itemIngredientsLabel.setText(bundle.getString("removedText") + removedIngredients);
			} else {
				itemIngredientsLabel.setText("");
			}
			button.setText(controller.getAmount(foodItem.getItemId()) + " x " + foodItem.getName() + "\n" + itemIngredientsLabel.getText());
			popUp.close();
		});
		/*cancel.setOnAction(event -> {
			popUp.close();
		});*/
		
		boxOkCancel.getChildren().add(okay);
		
		boxWhole.getChildren().addAll(nameLabel, boxIngredient, boxOkCancel);
		Scene popUpScene = new Scene(boxWhole, 400, height);
		popUp.setScene(popUpScene);
		popUp.initModality(Modality.APPLICATION_MODAL);
		popUp.initStyle(StageStyle.UNDECORATED);
		popUp.centerOnScreen();
		popUp.show();
			
		System.out.println(controller.shoppingCartToString());
	}
	
	/**
	 * Popup for informing the user of the upcoming return to the start view.
	 */
	public void timeOutWarning() {
        Notifications.create()
        .title( bundle.getString("notificationTitleText") )
        .text( bundle.getString("notificationText") )
        .owner(menu)
        .showWarning();
	}

}
