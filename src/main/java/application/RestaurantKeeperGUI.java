package application;

import java.io.IOException;

import controller.RestaurantKeeperController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RestaurantKeeperGUI extends Application{

	private Stage primaryStage;
	private AnchorPane restaurantKeeperView;
	
	@Override
	public void start(Stage primaryStage){
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Ravintoloitsijan käyttöliittymä");
		
		initializeRestaurantKeeperView();
	}

	public void initializeRestaurantKeeperView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RestaurantKeeperGUI.class.getResource("/view/restaurantKeeperView.fxml"));
			restaurantKeeperView = (AnchorPane) loader.load();
			
			RestaurantKeeperController rkController = loader.getController();
			
			Scene scene = new Scene(restaurantKeeperView);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
