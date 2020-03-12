package application;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Order;
import view.MenuViewController;
import view.RestaurantKeeperController;

public class App extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private AnchorPane restaurantKeeperView;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Demo");
		startDemo();
		
		
	}
	
	public void startDemo() {
		TilePane tile = new TilePane();
		tile.setAlignment(Pos.CENTER);
		Button owner = new Button("Owner");
		Button customer = new Button("Customer");
		owner.setPrefSize(200, 200);
		customer.setPrefSize(200, 200);
		HBox hBox = new HBox();
		hBox.getChildren().addAll(owner, customer);
		tile.getChildren().add(hBox);
		Scene scene = new Scene(tile, 500, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
		EventHandler<MouseEvent> goCustomer = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(MainApp.class.getResource("/view/CustomerUI.fxml"));
					rootLayout = (BorderPane) loader.load();
					
					Scene scene = new Scene(rootLayout);
					scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
					  () {

					        @Override
					        public void handle(KeyEvent t) {
					          if(t.getCode()==KeyCode.ESCAPE)
					          {
					           startDemo();
					          }
					        }
					    });
					primaryStage.setScene(scene);
					primaryStage.show();
					
					MenuViewController controller = loader.getController();
					
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		};
		EventHandler<MouseEvent> goOwner = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(RestaurantKeeperGUI.class.getResource("/view/restaurantKeeperView.fxml"));
					restaurantKeeperView = (AnchorPane) loader.load();
					
					RestaurantKeeperController rkController = loader.getController();
					
					Scene scene = new Scene(restaurantKeeperView);
					scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>
					  () {

					        @Override
					        public void handle(KeyEvent t) {
					          if(t.getCode()==KeyCode.ESCAPE)
					          {
					           startDemo();
					          }
					        }
					    });
					primaryStage.setScene(scene);
					primaryStage.show();
					
				}catch(IOException e) {
					e.printStackTrace();
				}

			}
		};
		customer.addEventHandler(MouseEvent.MOUSE_PRESSED, goCustomer);
		owner.addEventHandler(MouseEvent.MOUSE_PRESSED, goOwner);
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
