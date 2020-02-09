package view;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	private FlowPane menu;
	
	@FXML
	private VBox shoppingCartList;
	
	@FXML
	private Button emptyButton;
	
	private MainApp mainApp;
	
	private FoodItemAccessObject foodItemAO = new FoodItemAccessObject();
	
	private ShoppingCart shoppingCart = new ShoppingCart();
	
	public MenuViewController() {
		
	}
	
	@FXML
	private void initialize() {
		FoodItem[] fItems = foodItemAO.readFoodItems();
		int menuId;
		//Image image = new Image("https://cdn.pixabay.com/photo/2015/07/01/07/06/burger-827309_1280.jpg");
		
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
				menuItem.setMinSize(150, 150);
				menuItem.setText(fItems[i].getName());
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
							System.out.println("On jo korissa");
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
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
