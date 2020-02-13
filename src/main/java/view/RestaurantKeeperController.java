package view;

import java.util.Arrays;

import org.controlsfx.control.Notifications;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import model.*;

/**
 * Controller -class for user interface used by restaurant keeper.
 * 
 * @author Arttu Seuna
 *
 */
public class RestaurantKeeperController {
	
	private FoodItemAccessObject foodItemDao;
	private ObservableList<FoodItem> foodItemList;

	// Owned node for notification pop-ups
	@FXML
	private TabPane tabPane;
	
	// Defining variables for adding a new item to database
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
		
	// Table and columns for restarant menu
	@FXML
	private TableView<FoodItem> foodItemTableView;
	@FXML
	private TableColumn<FoodItem, Integer> idColumn;
	@FXML
	private TableColumn<FoodItem, String> nameColumn;
	@FXML
	private TableColumn<FoodItem, Double> priceColumn;
	@FXML
	private TableColumn<FoodItem, Void> inMenuColumn;
	@FXML
	private TableColumn<FoodItem, String> categoryColumn;
	@FXML
	private TableColumn<FoodItem, Integer> soldColumn;
	@FXML
	private TableColumn<FoodItem, Integer> readyColumn;
	@FXML
	private TableColumn<FoodItem, Void> deleteColumn;
	@FXML
	private TableColumn<FoodItem, Void> saveEditColumn;
	@FXML
	private TableColumn<FoodItem, Void> cancelColumn;
	
	//cellFactories for buttons in the menu table
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> deleteCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> editCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> cancelCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> inMenuCellFactory;
	
	/**
	 * Empty constructor
	 * 
	 */
	public RestaurantKeeperController() {
		
	}
	
	/**
	 * Initial actions for using controller class. Setting CellFactories for the menu table, and instantiating a DataAccessObject for FoodItem.
	 */
	@FXML
	public void initialize() {		
		foodItemDao = new FoodItemAccessObject();
		
		// initializing cellFActories
		idColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("ItemId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("price"));

		categoryColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("category"));
		soldColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("sold"));
		readyColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("ready"));
		
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		// Button and checkbox columns
		createButtonColumns();
		inMenuColumn.setCellFactory(inMenuCellFactory);
		deleteColumn.setCellFactory(deleteCellFactory);
		saveEditColumn.setCellFactory(editCellFactory);
		// cancelColumn.setCellFactory(cancelCellFactory);
		
		// fetching foodItems from database
		refreshFoodItems();
	}
	
	// event handlers for editing tablecells
	/**
	 * Event handler for the changes in name column. When the change is committed the current object will be updated.
	 * 
	 * @param event
	 */
	@FXML
	public void onEditCommitNameColumn(CellEditEvent<?,String> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	/**
	 * Event handler for the changes in price column. When the change is committed the current object will be updated.
	 * 
	 * @param event
	 */
	@FXML
	public void onEditCommitPriceColumn(CellEditEvent<?,Double> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
	}
	/**
	 * Event handler for the changes in category column. When the change is committed the current object will be updated.
	 * 
	 * @param event
	 */
	@FXML
	public void onEditCommitCategoryColumn(CellEditEvent<?,String> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setCategory(event.getNewValue());
	}


	/**
	 * Private helper method for creating popup toast notifications.
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
	 * Method for fetching foodItems from the database
	 */
	public void refreshFoodItems() {
		try {
			// setting foodItems into the menu table
			foodItemList = FXCollections.observableArrayList(Arrays.asList(foodItemDao.readFoodItems()));
			foodItemTableView.setItems(foodItemList);
			foodItemTableView.setEditable(true);
			
		}catch(NullPointerException e) {
			System.out.println("ruokalista on tyhjä");
		}
	}

	/**
	 * Method for adding a food item into the database
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
			
			// setting fields to the initial state
			addItemNameTextField.setText("");
			addItemPriceTextField.setText("");
			addItemCategoryTextField.setText("");
			addItemCheckBox.setSelected(false);
			
			// refreshing menu table
			refreshFoodItems();
			createNotification("Uusi tuote lisätty!");
			
		}else{
			createNotification("Anna pakolliset tiedot: Nimi, Hinta ja Kategoria. Hinnan tulee olla numero, desimaali erotin on '.' ");
		}
	}
	
	// Creating button column cellfactories
	
	/**
	 * Method for creating custom cellFactories for button and checkbox columns. Buttons and checkbox are created within Callback objects.
	 * 
	 */
	public void createButtonColumns() {
		// creating checkbox cellFactory
		inMenuCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    CheckBox cb = new CheckBox();
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(cb);
                            FoodItem foodItem = getTableView().getItems().get(getIndex());
                            cb.setSelected(foodItem.isInMenu());
                            cb.setOnAction((ActionEvent event) -> {
                            	// current foodItem object - getTableView().getItems().get(getIndex())
                                getTableView().getItems().get(getIndex()).setInMenu(cb.isSelected());
                            });
                        }
                    }
                };
				return cell;
			}
		};
		// checkbox ends
		
		// creating CellFactory for delete button
		deleteCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    Button btn = new Button("Poista");
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction((ActionEvent event) -> {
                                FoodItem foodItem = getTableView().getItems().get(getIndex());
                                System.out.println("poisto selectedData: " + foodItem + ", itemId" + foodItem.getItemId());
                                boolean success = foodItemDao.deleteFoodItem(foodItem.getItemId());
                                if(success) {
                                	foodItemTableView.getItems().remove(foodItem);
                                	createNotification("Tuote poistettu onnistuneesti!");
                                }else {
                                	createNotification("Tuotetta ei onnistuttu poistamaan");
                                }
                            });
                            setGraphic(btn);
                        }
                    }
                };
				return cell;
			}
		};
		// delete button ends
		
		// creating cellFactory for editbutton
		editCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    Button btn = new Button("Tallenna");
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction((ActionEvent event) -> {
                                FoodItem foodItem = getTableView().getItems().get(getIndex());
                                System.out.println("muokkaus selectedData: " + foodItem + ", itemId " + foodItem.getItemId() + ", kateg. " + foodItem.getCategory());
                                boolean success = foodItemDao.updateFoodItem(foodItem);
                                if(success) {
                                	createNotification("Tuotetta muokattu onnistuneesti!");
                                }else {
                                	createNotification("Tuotetta ei onnistuttu muokkaamaan");
                                }
                                refreshFoodItems();
                            });
                            setGraphic(btn);
                        }
                    }
                };
				return cell;
			}
		};
		// editbutton ends
		
		// cancelbutton
		cancelCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    Button btn = new Button("Peruuta");
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction((ActionEvent event) -> {
                            	// functionality for cancel button
                            });
                            setGraphic(btn);
                        }
                    }
                };
				return cell;
			}
		};
		// cancel button ends
	}

}
