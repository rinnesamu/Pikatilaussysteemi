package view;

import java.util.Arrays;

import org.controlsfx.control.Notifications;
import application.RestaurantKeeperGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import model.*;

/**
 * Kontrolleri luokka ravintoloitsijan käyttölittymälle.
 * Määritellään käyttäliittymän nappien toiminnot.
 * 
 * @author Arttu Seuna
 *
 */
public class RestaurantKeeperController {
	
	private FoodItemAccessObject foodItemDao;
	
	// Omistaja solmu ilmoitus popupeille
	@FXML
	private TabPane tabPane;
	
	// Tuotteen lisääminen tietokantaan nappi
	@FXML
	private Button addItemButton;
	@FXML
	private TextField addItemNameTextField;
	@FXML
	private TextField addItemPriceTextField;
	@FXML
	private CheckBox addItemCheckBox;
	@FXML
	private TextField addItemCategoryTextField;
	
	private ObservableList<FoodItem> foodItemList;
	
	// Ruokalistataulukko ja sarakkeet
	@FXML
	private TableView<FoodItem> foodItemTableView;
	@FXML
	private TableColumn<FoodItem, Integer> idColumn;
	@FXML
	private TableColumn<FoodItem, String> nameColumn;
	@FXML
	private TableColumn<FoodItem, Double> priceColumn;
	@FXML
	private TableColumn<FoodItem, Boolean> inMenuColumn;
	@FXML
	private TableColumn<FoodItem, String> categoryColumn;
	@FXML
	private TableColumn<FoodItem, Integer> soldColumn;
	@FXML
	private TableColumn<FoodItem, Integer> readyColumn;
	@FXML
	private TableColumn<FoodItem, Void> buttonColumn;
	
	/**
	 * Tyhjä kostruktori
	 */
	public RestaurantKeeperController() {
		
	}
	
	/**
	 * Alkutoimet kontrolleri luokan käytäämiseen.
	 */
	@FXML
	public void initialize() {		
		foodItemDao = new FoodItemAccessObject();
		
		refreshFoodItems();
	}
	/**
	 * Paikallinen metodi ilmoituksen luomiseksi
	 * @param msg - ilmoituksen teksti
	 */
	private void createNotification(String msg) {
		Notifications.create()
		.title("Alert!")
		.text(msg)
		.owner(tabPane)
		.showWarning();
	}
	
	/**
	 * Metodi tuotelistan päivittämiseen tietokannasta
	 */
	public void refreshFoodItems() {
		try {
			// Määritetään tableView:n sarakkeet
			foodItemList = FXCollections.observableArrayList(Arrays.asList(foodItemDao.readFoodItems()));
			foodItemTableView.setItems(foodItemList);
			foodItemTableView.setEditable(true);
			idColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("ItemId"));
			nameColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
			priceColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("price"));
			inMenuColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Boolean>("inMenu"));
			categoryColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("category"));
			soldColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("sold"));
			readyColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("ready"));
			
			nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
			categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			// inMenuColumn.setCellFactory(new CheckBoxTableCell<FoodItem, Boolean>());

			// Tehdään Poista painike, jokaiselle riville
			Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> cellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
				@Override
				public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
					final TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {

	                    private final Button btn = new Button("Poista");

	                    {
	                        btn.setOnAction((ActionEvent event) -> {
	                            FoodItem foodItem = getTableView().getItems().get(getIndex());
	                            System.out.println("selectedData: " + foodItem + ", itemId" + foodItem.getItemId());
	                            boolean success = foodItemDao.deleteFoodItem(foodItem.getItemId());
	                            if(success) {
	                            	foodItemTableView.getItems().remove(foodItem);
	                            	createNotification("Tuote poistettu onnistuneesti!");
	                            }else {
	                            	createNotification("Tuotetta ei onnistuttu poistamaan");
	                            }
	                        });
	                    }

	                    @Override
	                    public void updateItem(Void item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (empty) {
	                            setGraphic(null);
	                        } else {
	                            setGraphic(btn);
	                        }
	                    }
	                };
					return cell;
				}
			};
			buttonColumn.setCellFactory(cellFactory);
			// Poistopainike loppuu
			
		}catch(NullPointerException e) {
			System.out.println("ruokalista on tyhjä");
		}
	}

	/**
	 * Metodi tuotteen lisäämiselle tietokantaan
	 */
	public void addItem() {
		boolean isNumber;
		
		String name = addItemNameTextField.getText();
		double price = 0.0;
		String category = addItemCategoryTextField.getText();
		boolean inMenu = addItemCheckBox.isSelected();
		try {
			price = Double.parseDouble(addItemPriceTextField.getText());
			isNumber = true;
		}catch(Exception e) {
			isNumber = false;
		}
		
		if(!name.contentEquals("") && !addItemPriceTextField.getText().contentEquals("") && !addItemCategoryTextField.getText().contentEquals("")  && isNumber) {
			FoodItem newFoodItem = new FoodItem(name, price, category, inMenu);
			foodItemDao.createFoodItem(newFoodItem);
			
			// Asetetaan kentät alkutilaan
			addItemNameTextField.setText("");
			addItemPriceTextField.setText("");
			addItemCategoryTextField.setText("");
			addItemCheckBox.setSelected(false);
			
			// Päivitetään tuotteet listassa
			refreshFoodItems();
			createNotification("Uusi tuote lisätty!");
			
		}else{
			createNotification("Anna pakolliset tiedot: Nimi, Hinta ja Kategoria. Hinnan tulee olla numero, desimaali erotin on '.' ");
		}
	}
	
	
}
