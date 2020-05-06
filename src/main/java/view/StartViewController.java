package view;

import application.Start;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartViewController {
	
	@FXML
	public Button startButton, fin, eng;
	
	private Start controller;
	
	/**
	 * Starts order
	 */
	@FXML
	public void startOrdering() {
		controller.startOrder();
	}
	
	/**
	 * Changes language to Finnish
	 */
	@FXML 
	public void setLanguageFi() {
		 controller.setLanguage("languageFi", "countryFi");
	 }
	
	/**
	 * Changes language to Englis
	 */
	@FXML 
	public void setLanguageEn() {
		 controller.setLanguage("languageEn", "countryEn");
	 }
	 
	/**
	 * Setter for start
	 * @param start
	 */
	 public void setController(Start start) {
		 this.controller = start;
	 }

}
