package view;
import controller.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

public class ValuuttakoneenGUI extends Application  implements IValuuttakoneenGUI {
	private IValuuttakone valuuttakone;
	private IValuuttakoneenOhjain valuuttakoneenOhjain;
	private String valuutat[];
	private ListView<String> lvMistä, lvMihin;
	private TextField määräTextField, tulosTextField;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Valuutta muunnos!");
		HBox hBox = new HBox();
		VBox vBoxMistä = new VBox();
		VBox vBoxMihin = new VBox();
		VBox vBoxLoput = new VBox();
		valuutat = valuuttakoneenOhjain.getValuutat();
		
		
		ObservableList<String> valuutatLista = FXCollections.observableArrayList(valuutat);
		lvMistä = new ListView<>();
		lvMistä.setItems(valuutatLista);
		Text mistäText = new Text("Mistä");
		vBoxMistä.getChildren().addAll(mistäText, lvMistä);
		
		lvMihin = new ListView<>();
		lvMihin.setItems(valuutatLista);
		Text mihinText = new Text("Mihin");
		vBoxMihin.getChildren().addAll(mihinText, lvMihin);
		
		Text määräText = new Text("Määrä");
		määräTextField = new TextField();
		Button b = new Button("Muunna");
		Text tulosText = new Text("Tulos");
		tulosTextField = new TextField();
		vBoxLoput.getChildren().addAll(määräText, määräTextField, b, tulosText, tulosTextField);
		
		hBox.getChildren().addAll(vBoxMistä, vBoxMihin, vBoxLoput);
		
		b.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				valuuttakoneenOhjain.muunnos();
				
			}
			
		});
		
		Scene scene = new Scene(hBox,500,500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	

	@Override
	public int getLahtoindeksi() {
		return lvMistä.getSelectionModel().getSelectedIndex();
	}

	@Override
	public int getKohdeIndeksi() {
		return lvMihin.getSelectionModel().getSelectedIndex();
	}

	@Override
	public double getMäärä() {
		double palautettava = 0;
		try {
			palautettava = Double.parseDouble(määräTextField.getText());
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Tarkista syöte!");
			alert.setHeaderText(null);
			alert.setContentText("Vain kokonaislukuja tai desimaalilukuja!");

			alert.showAndWait();
		}
		return palautettava;
	}

	@Override
	public void setTulos(double määrä) {

		tulosTextField.setText(Double.toString(määrä));

	}
	public void init() {
		this.valuuttakone = new Valuuttakone();
		this.valuuttakoneenOhjain = new ValuuttakoneenOhjain(this, this.valuuttakone);
	}
	public static void main(String[] args) {
		launch(args);
	}


}
