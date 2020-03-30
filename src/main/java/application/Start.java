package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

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
	FXMLLoader loader;
	private TimingController control;
	Locale curLocale = new Locale("fi", "FI"); // Default Finland
	ResourceBundle bundle;
	String appConfigPath = "Test.properties";
	
	@Override
	public void init() {
		Properties properties = new Properties();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(appConfigPath);
			properties.load(inputStream);
			String language = properties.getProperty("languageEn");
			String country = properties.getProperty("countryEn");
			curLocale = new Locale(language, country);
			Locale.setDefault(curLocale);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Not found, using default");
		}
		try {
			bundle = ResourceBundle.getBundle("TextResources", curLocale);
			System.out.println("Ennen");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Default TextProperties not found.");
			System.exit(0);
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(bundle.getString("headerText"));
		
		control = new TimingController();
		control.setControllable(this);
		control.setDaemon(true);
		control.start();
		
		initUI();
	}
	
	public void initUI() {
		try {
			loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/StartView.fxml"));
			loader.setResources(bundle);
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
			menuViewController.setController(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
