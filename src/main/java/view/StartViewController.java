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
	
	@FXML 
	public void setLanguageFi() {
		 controller.setLanguage("languageFi", "countryFi");
	 }
	
	@FXML 
	public void setLanguageEn() {
		 controller.setLanguage("languageEn", "countryEn");
	 }
	 
	 public void setController(Start start) {
		 this.controller = start;
	 }

}
