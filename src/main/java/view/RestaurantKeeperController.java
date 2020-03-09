package view;

import java.time.LocalDateTime;
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
	private IngredientAccessObject ingredientAccessObject;
	private OrderAccessObject orderDao;
	
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
	private TableColumn<FoodItem, String> pathColumn;
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
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> deleteFoodItemCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> editFoodItemCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> cancelFoodItemCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> inMenuFoodItemCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> categoryFoodItemCellFactory;
	
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
	private TableColumn<FoodItem, String> addPathColumn;
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
	@FXML
	private TableView<Ingredient> ingredientTableView;
	@FXML
	private TableColumn<Ingredient, Integer> ingredientIdColumn;
	@FXML
	private TableColumn<Ingredient, String> ingredientNameColumn;
	@FXML
	private TableColumn<Ingredient, Void> ingredientRemovableColumn;
	@FXML
	private TableColumn<Ingredient, Void> ingredientDeleteColumn;
	
	// Observable list for ingredients
	private ObservableList<Ingredient> ingredientObList;
	
	// CellFactories for widget columns in ingredient table
	Callback<TableColumn<Ingredient, Void>, TableCell<Ingredient, Void>> ingredientRemovableCellFactory;
	Callback<TableColumn<Ingredient, Void>, TableCell<Ingredient, Void>> ingredientDeleteCellFactory;
	
	// Table and columns for adding new ingredient to database
	@FXML
	private TableView<Ingredient> addIngredientTableView;
	@FXML
	private TableColumn<Ingredient, String> addIngredientNameColumn;
	@FXML
	private TableColumn<Ingredient, Void> addIngredientRemovableColumn;
	@FXML
	private TableColumn<Ingredient, Void> addIngredientButtonColumn;
	
	// Observable list for dummy ingredient
	private ObservableList<Ingredient> addIngredientObList;
	
	// CellFactories for widget columns in ingredient adding table
	Callback<TableColumn<Ingredient, Void>, TableCell<Ingredient, Void>> addIngredientRemovableCellFactory;
	Callback<TableColumn<Ingredient, Void>, TableCell<Ingredient, Void>> addIngredientButtonCellFactory;
	
	// Table and columns for orders
	@FXML
	private TableView<Order> orderTableView;
	@FXML
	private TableColumn<Order, Integer> orderIdColumn;
	@FXML
	private TableColumn<Order, Integer> orderNumberColumn;
	@FXML
	private TableColumn<Order, LocalDateTime> orderTimeStampColumn;
	@FXML
	private TableColumn<Order, Void> orderReadyColumn;
	@FXML
	private TableColumn<Order, Void> orderEditColumn;
	
	// Observable list for orders
	private ObservableList<Order> orderObList;

	//CellFactories for widget columns in order table
	Callback<TableColumn<Order, Void>, TableCell<Order, Void>> orderReadyCellFactory;
	Callback<TableColumn<Order, Void>, TableCell<Order, Void>> orderEditCellFactory;
	
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
		
		// init data access objects needed
		categoryDao = new CategoryAccessObject();
		foodItemDao = new FoodItemAccessObject();
		ingredientAccessObject = new IngredientAccessObject();
		orderDao = new OrderAccessObject();
		
		refreshAll();
	}
	
	/**
	 * Method for refreshing everything inside tables
	 */
	public void refreshAll() {
		// initializing menu cellFactories
		idColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("ItemId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("price"));
		pathColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("path"));
		soldColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("sold"));
		readyColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("ready"));
		
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		pathColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		// choicebox, Button and checkbox column cellFactories for menu TableView
		createFoodItemCellFactories();
		inMenuColumn.setCellFactory(inMenuFoodItemCellFactory);
		deleteColumn.setCellFactory(deleteFoodItemCellFactory);
		saveEditColumn.setCellFactory(editFoodItemCellFactory);
		categoriesColumn.setCellFactory(categoryFoodItemCellFactory);
		// cancelColumn.setCellFactory(cancelCellFactory);
		refreshFoodItems();
		
		// add Item cellFactories for addFoodItemTableView
		addNameColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
		addPriceColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("price"));
		addPathColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("path"));
		
		addNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		addPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		addPathColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		// cellfactories for widget columns in food item adding table
		createAddFoodItemCellFactories();
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
		// cellFactory for category adding button
		createAddCategoryCellFactories();
		addCategoryButtonColumn.setCellFactory(addCategoryButtonCellFactory);
		refreshDummyCategory();
		
		//initializing ingredient column cellfactories
		ingredientIdColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("ItemId"));
		ingredientNameColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
		// cellfactories for widget columns in ingredienttable
		createIngredientCellFactories();
		ingredientRemovableColumn.setCellFactory(ingredientRemovableCellFactory);
		ingredientDeleteColumn.setCellFactory(ingredientDeleteCellFactory);
		refreshIngredients();
		
		// initializing ingredient adding column cellfactories
		addIngredientNameColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
		addIngredientNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		createAddIngredientCellFactories();
		addIngredientRemovableColumn.setCellFactory(addIngredientRemovableCellFactory);
		addIngredientButtonColumn.setCellFactory(addIngredientButtonCellFactory);
		refreshDummyIngredient();
		
		// initiliazing order column cellfactories
		orderIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNumber"));
		orderTimeStampColumn.setCellValueFactory(new PropertyValueFactory<Order, LocalDateTime>("creationTimeStamp"));
		createOrderCellFactories();
		orderReadyColumn.setCellFactory(orderReadyCellFactory);
		orderEditColumn.setCellFactory(orderEditCellFactory);
		refreshOrders();
	}
	
	/**
	 * Private helper method for creating popup toast notifications.
	 * @param msg text string for the message
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
	 * Method for updating a menu FoodItem.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	public void onEditCommitNameColumn(CellEditEvent<?,String> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	/**
	 * Event handler for the changes in price column. When the change is committed the current object will be updated.
	 * Method for updating a menu FoodItem.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	public void onEditCommitPriceColumn(CellEditEvent<?,Double> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
	}
	/**
	 * Event handler for the changes in path column. When the change is committed the current object will be updated.
	 * Method for updating a menu FoodItem.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	public void onEditCommitPathColumn(CellEditEvent<?, String> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setPath(event.getNewValue());
	}
	
	// event handlers for editing table cells in addFoodItemTableView
	/**
	 * Event handler for the changes in name column. When the change is committed the current object will be updated.
	 * This method is for adding a new FoodItem.
	 * @param event event object containing information on the edit
	 */
	@FXML
	public void onEditCommitAddNameColumn(CellEditEvent<?,String> event) {
		addFoodItemTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	/**
	 * Event handler for the changes in price column. When the change is committed the current object will be updated.
	 * This method is for adding a new FoodItem.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	public void onEditCommitAddPriceColumn(CellEditEvent<?,Double> event) {
		addFoodItemTableView.getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
	}
	/**
	 * Event handler for the changes in path column. When the change is committed the current object will be updated.
	 * Method for adding new FoodItem.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	public void onEditCommitAddPathColumn(CellEditEvent<?, String> event) {
		addFoodItemTableView.getItems().get(event.getTablePosition().getRow()).setPath(event.getNewValue());
	}
	
	// Event handlers for adding a new category
	/**
	 * Event handler for the changes in adding new category name column.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	public void onEditCommitAddCategoryNameColumn(CellEditEvent<?,String> event) {
		addCategoryTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	
	// Event handlers for adding a new ingredient
	/**
	 * Event handler for the changes in adding new ingredient name column.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	public void onEditCommitAddIngredientNameColumn(CellEditEvent<?,String> event) {
		addIngredientTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
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
	 * Method for fetching ingrtedients from database, and setting them in TableView
	 */
	public void refreshIngredients() {
		ingredientObList = FXCollections.observableArrayList(ingredientAccessObject.readIngredients());
		ingredientTableView.setItems(ingredientObList);
		// Maybe set editable later !!!!!!!!!!!!! -- Needs CellFActory for name column
	}
	
	/**
	 * Instantiates a dummy ingredient object for ingredient adding table
	 */
	public void refreshDummyIngredient() {
		Ingredient dummyIngredient = new Ingredient("Uusi ainesosa", false);
		List<Ingredient> tempList = new ArrayList<Ingredient>(0);
		tempList.add(dummyIngredient);
		addIngredientObList = FXCollections.observableArrayList(tempList);
		addIngredientTableView.setItems(addIngredientObList);
		addIngredientTableView.setEditable(true);
	}
	/**
	 * Method for fetching all orders from database
	 */
	public void refreshOrders() {
		orderObList = FXCollections.observableArrayList(orderDao.readOrders());
		orderTableView.setItems(orderObList);
	}
	
	// Creating cellfactories for choicebox, button and checkbox columns
	/**
	 * Method that creates custom cellFactories for choicebox, button and checkbox columns in menu TableView. Widgets are created within Callback objects.
	 * 
	 */
	public void createFoodItemCellFactories() {
		// creating checkbox cellFactory
		inMenuFoodItemCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    CheckBox cb = new CheckBox();
                    {
                        cb.setOnAction((ActionEvent event) -> {
                        	// current foodItem object is "getTableView().getItems().get(getIndex())"
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
		categoryFoodItemCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
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
		deleteFoodItemCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
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
		editFoodItemCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
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
		cancelFoodItemCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
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
	 * Method that creates custom cellFactories for addButton and checkbox columns in addFoodItemTableView.
	 * addButton CellFactory contains onAction method for adding item to database.
	 */
	public void createAddFoodItemCellFactories() {
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
	
	/**
	 * Method that creates custom CellFactories for widget columns in category table
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
	 * Method that creates custom cellFactories for widget columns in category adding table
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
	
	/**
	 * Method that creates custom cellFactories for widget columns in ingredient table
	 */
	public void createIngredientCellFactories() {
		// creating checkbox cellFactory
		ingredientRemovableCellFactory = new Callback<TableColumn<Ingredient, Void>, TableCell<Ingredient, Void>>(){
			@Override
			public TableCell<Ingredient, Void> call(TableColumn<Ingredient, Void> arg0) {
				TableCell<Ingredient, Void> cell = new TableCell<Ingredient, Void>() {
                    CheckBox cb = new CheckBox();
                    {
                    	cb.setDisable(true);
                    	/*
                        cb.setOnAction((ActionEvent event) -> {
                        });*/
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {                       
                        	Ingredient ingredient = getTableView().getItems().get(getIndex());
                            cb.setSelected(ingredient.isRemoveable());
                            setGraphic(cb);
                        }
                    }
                };
				return cell;
			}
		};
		// checkbox ends
		
		// delete category cellfactory
		ingredientDeleteCellFactory = new Callback<TableColumn<Ingredient, Void>, TableCell<Ingredient, Void>>(){
			@Override
			public TableCell<Ingredient, Void> call(TableColumn<Ingredient, Void> arg0) {
				TableCell<Ingredient, Void> cell = new TableCell<Ingredient, Void>() {
                    Button btn = new Button("Poista");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Ingredient ingredient = getTableView().getItems().get(getIndex());
                            System.out.println("ainesosan poisto selectedData: " + ingredient.getName());
                            boolean success = ingredientAccessObject.deleteIngredient(ingredient.getItemId());
                            if(success) {
                            	ingredientTableView.getItems().remove(ingredient);
                            	createNotification("Ainesosa poistettu onnistuneesti!");
                            }else {
                            	createNotification("Ainesosaa ei onnistuttu poistamaan");
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
	 * Method that creates custom cellFactories for widget columns in ingredient adding table
	 */
	public void createAddIngredientCellFactories() {
		// creating checkbox cellFactory
		addIngredientRemovableCellFactory = new Callback<TableColumn<Ingredient, Void>, TableCell<Ingredient, Void>>(){
			@Override
			public TableCell<Ingredient, Void> call(TableColumn<Ingredient, Void> arg0) {
				TableCell<Ingredient, Void> cell = new TableCell<Ingredient, Void>() {
                    CheckBox cb = new CheckBox();
                    {
                        cb.setOnAction((ActionEvent event) -> {
                        	// current foodItem object - getTableView().getItems().get(getIndex())
                            getTableView().getItems().get(getIndex()).setRemoveable(cb.isSelected());
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                        	Ingredient ingredient = getTableView().getItems().get(getIndex());
                            cb.setSelected(ingredient.isRemoveable());
                            setGraphic(cb);
                        }
                    }
                };
				return cell;
			}
		};
		// checkbox ends
		
		// add ingredient cellfactory
		addIngredientButtonCellFactory = new Callback<TableColumn<Ingredient, Void>, TableCell<Ingredient, Void>>(){
			@Override
			public TableCell<Ingredient, Void> call(TableColumn<Ingredient, Void> arg0) {
				TableCell<Ingredient, Void> cell = new TableCell<Ingredient, Void>() {
                    Button btn = new Button("Lisää ainesosa");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Ingredient ingredient = getTableView().getItems().get(getIndex());
                            System.out.println("ainesosan lisäys selectedData: " + ingredient.getName());
                            boolean success = ingredientAccessObject.createIngredient(ingredient);
                            if(success) {
                            	createNotification("Ainesosa lisätty onnistuneesti!");
                            }else {
                            	createNotification("Ainesosaa ei onnistuttu lisäämään");
                            }
                            refreshIngredients();
                            refreshDummyIngredient();
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
		// add ingredient ends
	}
	/**
	 * Method that creates custom cellfactories for widget columns in order table
	 * 
	 */
	public void createOrderCellFactories() {
		// creating checkbox cellFactory
		orderReadyCellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>(){
			@Override
			public TableCell<Order, Void> call(TableColumn<Order, Void> arg0) {
				TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                    CheckBox cb = new CheckBox();
                    {
                        cb.setOnAction((ActionEvent event) -> {
                            getTableView().getItems().get(getIndex()).setStatus(cb.isSelected());
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {                       
                            Order order = getTableView().getItems().get(getIndex());
                            cb.setSelected(order.isStatus());
                            setGraphic(cb);
                        }
                    }
                };
				return cell;
			}
		};
		// checkbox ends
		
		// creating cellFactory for editbutton
		orderEditCellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>(){
			@Override
			public TableCell<Order, Void> call(TableColumn<Order, Void> arg0) {
				TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                    Button btn = new Button("Tallenna");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Order order = getTableView().getItems().get(getIndex());
                            System.out.println("tilauksen muutos selectedData: status" + order.isStatus());
                            boolean success = orderDao.updateOrderStatus(order);
                            if(success) {
                            	createNotification("Tilaus muokattu onnistuneesti!");
                            }else {
                            	createNotification("Tilausta ei onnistuttu muokkaamaan");
                            }
                            refreshOrders();
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
	}
}
