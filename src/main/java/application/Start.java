package application;

import java.io.IOException;

import controller.TimingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MenuViewController;
import view.StartViewController;

public class Start extends Application {
	
	private Stage primaryStage;
	private AnchorPane rootLayout;
	FXMLLoader loader = new FXMLLoader();
	private TimingController control;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Ravintolasovellus");
		
		control = new TimingController();
		control.setControllable(this);
		control.setDaemon(true);
		control.start();
		
		initUI();
	}
	
	public void initUI() {
		try {
			System.out.println("Tääl ollaa");
			loader.setLocation(MainApp.class.getResource("/view/StartView.fxml"));
			rootLayout = (AnchorPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			StartViewController startViewController = loader.getController();
			startViewController.setController(this);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startOrder() {
		control.update();
		BorderPane menuLayout;
		try {
			loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/CustomerUI.fxml"));
			menuLayout = (BorderPane) loader.load();
			Scene scene = new Scene(menuLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			MenuViewController menuViewController = loader.getController();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
