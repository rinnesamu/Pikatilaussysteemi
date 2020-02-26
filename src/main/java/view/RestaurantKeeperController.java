package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	// Owner node for notification pop-ups
	@FXML
	private TabPane tabPane;
		
	// Table and columns for restaurant menu
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
	// cellFactory for category choice box
	//Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> categoryCellFactory;
	
	// Table and columns for adding a new item to database
	@FXML
	private TableView<FoodItem> addFoodItemTableView;
	@FXML
	private TableColumn<FoodItem, String> addNameColumn;
	@FXML
	private TableColumn<FoodItem, Double> addPriceColumn;
	@FXML
	private TableColumn<FoodItem, Void> addInMenuColumn;
	@FXML
	private TableColumn<FoodItem, String> addCategoryColumn;
	@FXML
	private TableColumn<FoodItem, Void> addButtonColumn;
	
	// observable list containing one dummy item that is set to addFoodItemTableView
	private ObservableList<FoodItem> addItemObList;
	
	// cellFactories for adding an Item
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> addInMenuCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> addButtonCellFactory;

	
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
		
		// initializing menu cellFactories
		idColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("ItemId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("price"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("category"));
		soldColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("sold"));
		readyColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("ready"));
		
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		// Button and checkbox column cellFactories for manu TableView
		createCellFactories();
		inMenuColumn.setCellFactory(inMenuCellFactory);
		deleteColumn.setCellFactory(deleteCellFactory);
		saveEditColumn.setCellFactory(editCellFactory);
		// cancelColumn.setCellFactory(cancelCellFactory);
		// fetching foodItems from database
		refreshFoodItems();
		
		// add Item cellFactories for addFoodItemTableView
		addNameColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
		addPriceColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("price"));
		addCategoryColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("category"));
		
		addNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		addPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		addCategoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		createAddItemCellFactories();
		addInMenuColumn.setCellFactory(addInMenuCellFactory);
		addButtonColumn.setCellFactory(addButtonCellFactory);
		refreshDummyFoodItem();
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
	
	// event handlers for editing tablecells in menu TableView
	/**
	 * Event handler for the changes in name column. When the change is committed the current object will be updated.
	 * Method for updating menu FoodItems.
	 * 
	 * @param event - event object containing information on the edit
	 */
	@FXML
	public void onEditCommitNameColumn(CellEditEvent<?,String> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	/**
	 * Event handler for the changes in price column. When the change is committed the current object will be updated.
	 * Method for updating menu FoodItems.
	 * 
	 * @param event - event object containing information on the edit
	 */
	@FXML
	public void onEditCommitPriceColumn(CellEditEvent<?,Double> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
	}
	/**
	 * Event handler for the changes in category column. When the change is committed the current object will be updated.
	 * Method for updating menu FoodItems.
	 * 
	 * @param event - event object containing information on the edit
	 */
	@FXML
	public void onEditCommitCategoryColumn(CellEditEvent<?,String> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setCategory(event.getNewValue());
	}
	
	// event handlers for editing table cells in addFoodItemTableView
	/**
	 * Event handler for the changes in name column. When the change is committed the current object will be updated.
	 * This method is for adding new FoodItems.
	 * @param event - event object containing information on the edit
	 */
	@FXML
	public void onEditCommitAddNameColumn(CellEditEvent<?,String> event) {
		addFoodItemTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	/**
	 * Event handler for the changes in price column. When the change is committed the current object will be updated.
	 * This method is for adding new FoodItems.
	 * 
	 * @param event - event object containing information on the edit
	 */
	@FXML
	public void onEditCommitAddPriceColumn(CellEditEvent<?,Double> event) {
		addFoodItemTableView.getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
	}
	/**
	 * Event handler for the changes in category column. When the change is committed the current object will be updated.
	 * Method for adding new FoodItems.
	 * 
	 * @param event - event object containing information on the edit
	 */
	@FXML
	public void onEditCommitAddCategoryColumn(CellEditEvent<?,String> event) {
		addFoodItemTableView.getItems().get(event.getTablePosition().getRow()).setCategory(event.getNewValue());
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
	 * Method for clearing table cells for adding new FoodItem in addFoodItemTableView
	 */
	public void refreshDummyFoodItem() {
		FoodItem dummyFoodItem = new FoodItem("Uusi tuote", 0.0, true);
		List<FoodItem> tempList = new ArrayList<FoodItem>(0);
		tempList.add(dummyFoodItem);
		addItemObList = FXCollections.observableArrayList(tempList);
		addFoodItemTableView.setItems(addItemObList);
		addFoodItemTableView.setEditable(true);
	}
	
	/**
	 * Method for creating custom cellFactories for addButton and checkbox columns in addFoodItemTableView.
	 * addButton CellFactory contains onAction method for adding item to database.
	 * 
	 */
	public void createAddItemCellFactories() {
		// creating checkbox cellFactory
		addInMenuCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
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
		
		// creating cellFactory for addButton
		addButtonCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    Button btn = new Button("Lisää Tuote");
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction((ActionEvent event) -> {
                                FoodItem foodItem = getTableView().getItems().get(getIndex());
                                System.out.println("lisäys selectedData: " + foodItem + ", itemId " + foodItem.getItemId() + ", kateg. " + foodItem.getCategory());
                                boolean success = foodItemDao.createFoodItem(foodItem);
                                if(success) {
                                	createNotification("Tuotetta lisätty onnistuneesti!");
                                }else {
                                	createNotification("Tuotetta ei onnistuttu lisäämään");
                                }
                                refreshFoodItems();
                                refreshDummyFoodItem();
                            });
                            setGraphic(btn);
                        }
                    }
                };
				return cell;
			}
		};
		// addButton ends
		
	}
	
	// Creating cellfactories for button and checkbox columns
	/**
	 * Method for creating custom cellFactories for button and checkbox columns in menu TableView. Buttons and checkbox are created within Callback objects.
	 * 
	 */
	public void createCellFactories() {
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
