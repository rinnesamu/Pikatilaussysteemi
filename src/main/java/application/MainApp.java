package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.MenuViewController;

/**
 * 
 * @author Kimmo Perälä
 *
 */

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
		
	public MainApp() {
	}
	
	public void showMenuView() {
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Ravintolasovellus");
		// En tiiä pitäskö lisätä? Pitäs muokata sit vähän ainaki kuvakkeitten kokoja. Mut ois ehkä paremman näkönen.
		//this.primaryStage.setMaximized(true);
		 
		//DEMOVERSIOSSA
		//this.primaryStage.initStyle(StageStyle.UNDECORATED);
		
		
		initUI();
	}
	
	public void initUI() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/CustomerUI.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			MenuViewController controller = loader.getController();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
