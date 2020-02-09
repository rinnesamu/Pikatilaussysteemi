package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * 
 * @author Anders Sandsund
 *
 */
public class KitchenViewApp extends Application{
	private Stage kitchenStage;
	private BorderPane rootLayout;
	private KitchenViewApp kitchenViewApp;
	
	public KitchenViewApp() {
	}
	
	@Override
	public void start(Stage kitchenStage) throws Exception {
		
		//BorderPane kitchenBpane = new BorderPane();
		
		
		//Scene scene = new Scene(kitchenBpane);
		this.kitchenStage = kitchenStage;
		this.kitchenStage.setTitle("KITCHEN_VIEW_UI");
		this.kitchenStage.setMaximized(true);
		//kitchenStage.setScene(scene);
		//kitchenStage.show();
		initUI();
	}
	
	
	public void initUI() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(KitchenViewApp.class.getResource("KitchenViewUI.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			rootLayout.setMaxSize(1920, 1080);
			Scene scene = new Scene(rootLayout);
			kitchenStage.setScene(scene);
			kitchenStage.show();
			
			KitchenViewController controller = loader.getController();
			controller.setKitchenViewApp(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setKitchenViewApp(KitchenViewApp kitchenViewApp) {
		this.kitchenViewApp = kitchenViewApp;
	}
	public static void main(String[] args) {
		launch(args);
	}

}
