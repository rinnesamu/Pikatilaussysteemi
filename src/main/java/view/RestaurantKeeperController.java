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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
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
	private CategoryAccessObject categoryDao;
	
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
	private TableColumn<FoodItem, Void> categoriesColumn;
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

	// ObservableList for foodItems
	private ObservableList<FoodItem> foodItemObList;
	
	//cellFactories for widgets in the menu table
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> deleteCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> editCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> cancelCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> inMenuCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> categoryCellFactory;
	
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
	private TableColumn<FoodItem, Void> addCategoryColumn;
	@FXML
	private TableColumn<FoodItem, Void> addButtonColumn;
	
	// observable list containing one dummy item that is set to addFoodItemTableView
	private ObservableList<FoodItem> addItemObList;
	
	// cellFactories for adding an Item
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> addInMenuCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> addButtonCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> addCategoryChoiceBoxCellFactory;

	// Table and columns for food categories
	@FXML
	private TableView<Category> categoryTableView;
	@FXML
	private TableColumn<Category, Integer> categoryIdColumn;
	@FXML
	private TableColumn<Category, String> categoryNameColumn;
	@FXML
	private TableColumn<Category, Void> categoryDeleteColumn;
	
	// ObservableList for categories
	private ObservableList<Category> categoryObList;
	
	// CellFactories for widget columns in category table
	Callback<TableColumn<Category, Void>, TableCell<Category, Void>> categoryDeleteCellFactory;
	
	// Table and columns for adding food categories
	@FXML
	private TableView<Category> addCategoryTableView;
	@FXML
	private TableColumn<Category, String> addCategoryNameColumn;
	@FXML
	private TableColumn<Category, Void> addCategoryButtonColumn;
	
	// Observable list for one dummy category object
	private ObservableList<Category> addCategoryObList;
	
	// cellfactories for adding category
	Callback<TableColumn<Category, Void>, TableCell<Category, Void>> addCategoryButtonCellFactory;
	
	// Table and columns for ingredients
	
	// Table and columns for adding new ingredient to database
	
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
		
		// reading categories
		categoryDao = new CategoryAccessObject();
		foodItemDao = new FoodItemAccessObject();
		
		// initializing menu cellFactories
		idColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("ItemId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("price"));
		soldColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("sold"));
		readyColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("ready"));
		
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		// choicebox, Button and checkbox column cellFactories for menu TableView
		createCellFactories();
		inMenuColumn.setCellFactory(inMenuCellFactory);
		deleteColumn.setCellFactory(deleteCellFactory);
		saveEditColumn.setCellFactory(editCellFactory);
		categoriesColumn.setCellFactory(categoryCellFactory);
		// cancelColumn.setCellFactory(cancelCellFactory);
		
		// fetching foodItems from database
		refreshFoodItems();
		
		// add Item cellFactories for addFoodItemTableView
		addNameColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
		addPriceColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("price"));
		
		addNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		addPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		
		// cellfactories for widget columns in food item adding table
		createAddItemCellFactories();
		addInMenuColumn.setCellFactory(addInMenuCellFactory);
		addButtonColumn.setCellFactory(addButtonCellFactory);
		addCategoryColumn.setCellFactory(addCategoryChoiceBoxCellFactory);
		refreshDummyFoodItem();
		
		// initializing category column cellfactories
		categoryIdColumn.setCellValueFactory(new PropertyValueFactory<Category, Integer>("Id"));
		categoryNameColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
		
		createCategoryCellFactories();
		categoryDeleteColumn.setCellFactory(categoryDeleteCellFactory);
		refreshCategories();
		
		// init category adding columns
		addCategoryNameColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
		addCategoryNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		// cellFActory for category adding button
		createAddCategoryCellFactories();
		addCategoryButtonColumn.setCellFactory(addCategoryButtonCellFactory);
		refreshDummyCategory();
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
	 * Event handler for the changes in adding new category name column.
	 * 
	 * @param event - event object containing information on the edit
	 */
	@FXML
	public void onEditCommitAddCategoryNameColumn(CellEditEvent<?,String> event) {
		addCategoryTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	
	/**
	 * Method for fetching foodItems from the database
	 */
	public void refreshFoodItems() {
		try {
			// setting foodItems into the menu table
			foodItemObList = FXCollections.observableArrayList(Arrays.asList(foodItemDao.readFoodItems()));
			foodItemTableView.setItems(foodItemObList);
			foodItemTableView.setEditable(true);
			
		}catch(NullPointerException e) {
			System.out.println("ruokalista on tyhjä");
		}
	}
	
	/**
	 * Method for clearing table cells for adding new FoodItem in addFoodItemTableView.
	 * Instantiates a new "default" dummyFoodItem to fill the fields in TableView.
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
	 * Method for fetching categories from database, and setting them on TableView
	 */
	public void refreshCategories() {
		categoryObList = FXCollections.observableArrayList(categoryDao.readCategories());
		categoryTableView.setItems(categoryObList);
		// Maybe set editable later !!!!!!!!!!!!! -- Needs CellFActory for name column
	}
	
	/**
	 * Instantiates a dummy category object for category adding table
	 */
	public void refreshDummyCategory() {
		Category dummyCategory = new Category("Uusi kategoria");
		List<Category> tempList = new ArrayList<Category>(0);
		tempList.add(dummyCategory);
		addCategoryObList = FXCollections.observableArrayList(tempList);
		addCategoryTableView.setItems(addCategoryObList);
		addCategoryTableView.setEditable(true);
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
                    {
                        cb.setOnAction((ActionEvent event) -> {
                        	// current foodItem object - getTableView().getItems().get(getIndex())
                            getTableView().getItems().get(getIndex()).setInMenu(cb.isSelected());
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            FoodItem foodItem = getTableView().getItems().get(getIndex());
                            cb.setSelected(foodItem.isInMenu());
                            setGraphic(cb);
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
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            FoodItem foodItem = getTableView().getItems().get(getIndex());
                            System.out.println("lisäys selectedData: " + foodItem + ", itemId " + foodItem.getItemId() + ", kateg. " + foodItem.getCategory());
                            boolean success = foodItemDao.createFoodItem(foodItem);
                            if(success) {
                            	createNotification("Tuote lisätty onnistuneesti!");
                            }else {
                            	createNotification("Tuotetta ei onnistuttu lisäämään");
                            }
                            refreshFoodItems();
                            refreshDummyFoodItem();
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
		// addButton ends
		
		//category choiceBox
		addCategoryChoiceBoxCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    ChoiceBox<String> choiceBox = new ChoiceBox<String>();
                    {
                        Category[] categoryArray = categoryDao.readCategories();
                        String[] categoryStringArray = new String[categoryArray.length];
                		for (int i = 0; i < categoryArray.length; i++) {
                			categoryStringArray[i] = categoryArray[i].getName();
                		}
                		ObservableList<String> categoryObList = FXCollections.observableArrayList(categoryStringArray);
                    	choiceBox.setItems(categoryObList);
                    	
                        // change listener for selection change in box
                        choiceBox.getSelectionModel()
                        	.selectedItemProperty()
                        	.addListener( (v, oldValue, newValue) -> {
                        		// setting new category to current foodItem
                        		getTableView().getItems().get(getIndex()).setCategory(newValue);
                        	});
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                        	choiceBox.setValue(null);
                            setGraphic(choiceBox);
                        }
                    }
                };
				return cell;
			}
		};
		// category choice box ends
	}
	
	// Creating cellfactories for choicebox, button and checkbox columns
	/**
	 * Method for creating custom cellFactories for choicebox, button and checkbox columns in menu TableView. Widgets are created within Callback objects.
	 * 
	 */
	public void createCellFactories() {
		// creating checkbox cellFactory
		inMenuCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    CheckBox cb = new CheckBox();
                    {
                        cb.setOnAction((ActionEvent event) -> {
                        	// current foodItem object - getTableView().getItems().get(getIndex())
                            getTableView().getItems().get(getIndex()).setInMenu(cb.isSelected());
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {                       
                            FoodItem foodItem = getTableView().getItems().get(getIndex());
                            cb.setSelected(foodItem.isInMenu());
                            setGraphic(cb);
                        }
                    }
                };
				return cell;
			}
		};
		// checkbox ends
		
		//category choiceBox
		categoryCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    ChoiceBox<String> choiceBox = new ChoiceBox<String>();
                    {
                        Category[] categoryArray = categoryDao.readCategories();
                        String[] categoryStringArray = new String[categoryArray.length];
                		for (int i = 0; i < categoryArray.length; i++) {
                			categoryStringArray[i] = categoryArray[i].getName();
                		}
                		ObservableList<String> categoryObList = FXCollections.observableArrayList(categoryStringArray);
                    	choiceBox.setItems(categoryObList);
                    	
                        // change listener for selection change in box
                        choiceBox.getSelectionModel()
                        	.selectedItemProperty()
                        	.addListener( (v, oldValue, newValue) -> {
                        		// setting new category to current foodItem
                        		getTableView().getItems().get(getIndex()).setCategory(newValue);
                        	});
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                        	// setting the choicebox default value to current category
                        	choiceBox.setValue(getTableView().getItems().get(getIndex()).getCategory());
                            setGraphic(choiceBox);
                        }
                    }
                };
				return cell;
			}
		};
		// category choice box ends
		
		
		// creating CellFactory for delete button
		deleteCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    Button btn = new Button("Poista");
                    {
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
		// delete button ends
		
		// creating cellFactory for editbutton
		editCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    Button btn = new Button("Tallenna");
                    {
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
		// editbutton ends
		
		// cancelbutton
		cancelCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    Button btn = new Button("Peruuta");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	// functionality for cancel button
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
		// cancel button ends
	}

	
	/**
	 * Method for creating custom CellFactories for widget columns in category table
	 */
	public void createCategoryCellFactories() {
		// delete category cellfactory
		categoryDeleteCellFactory = new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>(){
			@Override
			public TableCell<Category, Void> call(TableColumn<Category, Void> arg0) {
				TableCell<Category, Void> cell = new TableCell<Category, Void>() {
                    Button btn = new Button("Poista");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Category category = getTableView().getItems().get(getIndex());
                            System.out.println("kategorian poisto selectedData: " + category.getName());
                            boolean success = categoryDao.deleteCategoryByName(category.getName());
                            if(success) {
                            	categoryTableView.getItems().remove(category);
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
		// delete category ends
		
	}
	
	/**
	 * Method for creating custom cellFactories for widget columns in category adding table
	 */
	public void createAddCategoryCellFactories() {
		// add category cellfactory
		addCategoryButtonCellFactory = new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>(){
			@Override
			public TableCell<Category, Void> call(TableColumn<Category, Void> arg0) {
				TableCell<Category, Void> cell = new TableCell<Category, Void>() {
                    Button btn = new Button("Lisää kategoria");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Category category = getTableView().getItems().get(getIndex());
                            System.out.println("kategorian lisäys selectedData: " + category.getName());
                            boolean success = categoryDao.createCategory(category);
                            if(success) {
                            	createNotification("Kategoria lisätty onnistuneesti!");
                            }else {
                            	createNotification("Kategoriaa ei onnistuttu lisäämään");
                            }
                            refreshCategories();
                            refreshDummyCategory();
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
		// add category ends
	}
}
