package view;

import application.Start;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartViewController {
	
	@FXML
	public Button startButton, fin, eng;
	
	private Start controller;
	
	@FXML
	public void startOrdering() {
		controller.startOrder();
	}
	
	/* @FXML void setLanguage(String language) {
		 controller.setLanguage(language);
	 }*/
	 
	 public void setController(Start start) {
		 this.controller = start;
	 }

}
