package view;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.Notifications;

import application.IStart;
import controller.IRKController;
import controller.RKController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import model.*;
import util.Bundle;

/**
 * View -class for user interface used by restaurant keeper.
 * 
 * @author Arttu Seuna
 *
 */
public class RestaurantKeeper {
	
	private IRKController controller = new RKController(this);

	// Owner node for notification pop-ups
	@FXML
	private TabPane tabPane;
	// Tabs
	@FXML
	private Tab browseTab;
	@FXML
	private Tab categoryTab;
	@FXML
	private Tab ingredientTab;
	@FXML
	private Tab orderTab;
	@FXML
	private Tab orderSearchTab;
	
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
	private TableColumn<FoodItem, Void> ingredientColumn;
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
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> inMenuFoodItemCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> categoryFoodItemCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem,Void>> ingredientsFoodItemCellFactory;
	
	// Table and columns for adding a new item to database
	@FXML
	private TableView<FoodItem> addFoodItemTableView;
	@FXML
	private TableColumn<FoodItem, String> addFoodItemNameColumn;
	@FXML
	private TableColumn<FoodItem, Double> addFoodItemPriceColumn;
	@FXML
	private TableColumn<FoodItem, Void> addFoodItemInMenuColumn;
	@FXML
	private TableColumn<FoodItem, Void> addFoodItemCategoryColumn;
	@FXML
	private TableColumn<FoodItem, String> addFoodItemPathColumn;
	@FXML
	private TableColumn<FoodItem, Void> addFoodItemButtonColumn;
	@FXML
	private TableColumn<FoodItem, Void> addFoodItemIngredientsColumn;
	
	// observable list containing one dummy item that is set to addFoodItemTableView
	private ObservableList<FoodItem> addItemObList;
	
	// cellFactories for adding an Item
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> addFoodItemInMenuCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> addFoodItemButtonCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> addFoodItemCategoryCBCellFactory;
	Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>> addFoodItemIngredientsCCBFactory;

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
	private TableColumn<Order, String> orderTimeStampColumn;
	@FXML
	private TableColumn<Order, Void> orderReadyColumn;
	@FXML
	private TableColumn<Order, Void> orderContentsColumn;
	@FXML
	private TableColumn<Order, Void> orderEditColumn;
	
	// formatter for datetime column
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	
	// Observable list for orders
	private ObservableList<Order> orderObList;

	//CellFactories for widget columns in order table
	Callback<TableColumn<Order, Void>, TableCell<Order, Void>> orderReadyCellFactory;
	Callback<TableColumn<Order, Void>, TableCell<Order, Void>> orderContentsCellFactory;
	Callback<TableColumn<Order, Void>, TableCell<Order, Void>> orderEditCellFactory;
	
	// Table and Columns for searching orders
	@FXML
	private TableView<Order> searchOrderTableView;
	@FXML
	private TableColumn<Order, Integer> searchOrderIdColumn;
	@FXML
	private TableColumn<Order, Integer> searchOrderNumberColumn;
	@FXML
	private TableColumn<Order, String> searchOrderTimeStampColumn;
	@FXML
	private TableColumn<Order, Void> searchOrderContentsColumn;
	
	// Observable list for orders
	private ObservableList<Order> searchOrderObList;
	
	//CellFactories for widget columns in search order table
	Callback<TableColumn<Order, Void>, TableCell<Order, Void>> searchOrderContentsCellFactory;
	
	// datepickers and search button
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;
	@FXML
	private Button searchButton;
	
	// language buttons
	@FXML
	private Button buttonFi;
	@FXML
	private Button buttonEn;
	
	private IStart start;
	
	// resource bundle
	ResourceBundle bundle;
	
	/**
	 * Empty constructor
	 * 
	 */
	public RestaurantKeeper() {
		
	}
	
	/**
	 * Initial actions for using controller class. Setting CellFactories for the menu table, and instantiating a DataAccessObject for FoodItem.
	 * setting event listeners for tabs and language change buttons
	 */
	@FXML
	public void initialize() {		
		
		bundle = Bundle.getInstance();		
		initAllTableViews();
		
		// creating change listener for tab pane for listening selection changes in tabs
		tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if(newTab == browseTab) {
				this.initFoodItemTables();
			}else if(newTab == categoryTab) {
				this.initCategoryTables();
			}else if(newTab == ingredientTab) {
				this.initIngredientTables();
			}else if(newTab == orderTab) {
				this.initOrderTable();
			}
		});
		
		buttonFi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				start.setLanguageRestaurantKeeper("languageFi", "countryFi");
			}
		});
		
		buttonEn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				start.setLanguageRestaurantKeeper("languageEn", "countryEn");
			}
		});
	}
	
	/**
	 * Method for initializing all tableviews and their cellFactories
	 */
	@FXML
	private void initAllTableViews() {
		try {
			initFoodItemTables();		
			initCategoryTables();
			initIngredientTables();
			initOrderTable();
			initSearchOrderTable();
			
		}catch(NullPointerException npe) {
			System.out.println("category, ingerdient, order columns give nullPointerException");
		}
	}
	
	/**
	 * Method for initializing the tableviews for foodItems
	 * 
	 * @throws NullPointerException
	 */
	private void initFoodItemTables() throws NullPointerException{
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
		//ingredientColumn.setCellFactory(ingredientsFoodItemCellFactory);
		refreshFoodItems();
		
		// add Item cellFactories for addFoodItemTableView
		addFoodItemNameColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("name"));
		addFoodItemPriceColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("price"));
		addFoodItemPathColumn.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("path"));
		
		addFoodItemNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		addFoodItemPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		addFoodItemPathColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		// cellfactories for widget columns in food item adding table
		createAddFoodItemCellFactories();
		addFoodItemInMenuColumn.setCellFactory(addFoodItemInMenuCellFactory);
		addFoodItemButtonColumn.setCellFactory(addFoodItemButtonCellFactory);
		addFoodItemCategoryColumn.setCellFactory(addFoodItemCategoryCBCellFactory);
		//addFoodItemIngredientsColumn.setCellFactory(addFoodItemIngredientsCCBFactory);
		refreshDummyFoodItem();
	}
	
	/**
	 * Method for initializing the tableviews for Categories
	 * @throws NullPointerException
	 */
	private void initCategoryTables() throws NullPointerException{
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
	}
	
	/**
	 * Method for initializing ingredient tableviews
	 * @throws NullPointerException
	 */
	private void initIngredientTables() throws NullPointerException{
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
	}
	
	/**
	 * Method for initializing Order tableview
	 * @throws NullPointerException
	 */
	private void initOrderTable() throws NullPointerException{
		// initiliazing order column cellfactories
		orderIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNumber"));
		orderTimeStampColumn.setCellValueFactory(order-> new SimpleStringProperty(order.getValue().getDate().format(formatter)));
		
		createOrderCellFactories();
		orderReadyColumn.setCellFactory(orderReadyCellFactory);
		orderContentsColumn.setCellFactory(orderContentsCellFactory);
		orderEditColumn.setCellFactory(orderEditCellFactory);
		refreshOrders();
	}
	
	/**
	 * Method for initializing order search table view
	 * @throws NullPointerException
	 */
	private void initSearchOrderTable() throws NullPointerException{
		// initiliazing searching order column cellfactories
		searchOrderIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
		searchOrderNumberColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNumber"));
		searchOrderTimeStampColumn.setCellValueFactory(order-> new SimpleStringProperty(order.getValue().getDate().format(formatter)));
		
		createSearchOrderCellFactories();
		searchOrderContentsColumn.setCellFactory(searchOrderContentsCellFactory);
	}
	

	
	/**
	 * Private helper method for creating popup toast notifications.
	 * @param msg text string for the message
	 */
	private void createNotification(String msg) {
		Notifications.create()
		.title("")
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
	private void onEditCommitNameColumn(CellEditEvent<?,String> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	/**
	 * Event handler for the changes in price column. When the change is committed the current object will be updated.
	 * Method for updating a menu FoodItem.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	private void onEditCommitPriceColumn(CellEditEvent<?,Double> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
	}
	/**
	 * Event handler for the changes in path column. When the change is committed the current object will be updated.
	 * Method for updating a menu FoodItem.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	private void onEditCommitPathColumn(CellEditEvent<?, String> event) {
		foodItemTableView.getItems().get(event.getTablePosition().getRow()).setPath(event.getNewValue());
	}
	
	// event handlers for editing table cells in addFoodItemTableView
	/**
	 * Event handler for the changes in name column. When the change is committed the current object will be updated.
	 * This method is for adding a new FoodItem.
	 * @param event event object containing information on the edit
	 */
	@FXML
	private void onEditCommitAddNameColumn(CellEditEvent<?,String> event) {
		addFoodItemTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	/**
	 * Event handler for the changes in price column. When the change is committed the current object will be updated.
	 * This method is for adding a new FoodItem.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	private void onEditCommitAddPriceColumn(CellEditEvent<?,Double> event) {
		addFoodItemTableView.getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
	}
	/**
	 * Event handler for the changes in path column. When the change is committed the current object will be updated.
	 * Method for adding new FoodItem.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	private void onEditCommitAddPathColumn(CellEditEvent<?, String> event) {
		addFoodItemTableView.getItems().get(event.getTablePosition().getRow()).setPath(event.getNewValue());
	}
	
	// Event handlers for adding a new category
	/**
	 * Event handler for the changes in adding new category name column.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	private void onEditCommitAddCategoryNameColumn(CellEditEvent<?,String> event) {
		addCategoryTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	
	// Event handlers for adding a new ingredient
	/**
	 * Event handler for the changes in adding new ingredient name column.
	 * 
	 * @param event event object containing information on the edit
	 */
	@FXML
	private void onEditCommitAddIngredientNameColumn(CellEditEvent<?,String> event) {
		addIngredientTableView.getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
	}
	
	/**
	 * Method for handling button press action on searchOrder button
	 */
	@FXML
	private void onSearchButtonPress() {
		Order[] searchedOrders = controller.searchOrdersByDate(startDate.getValue(), endDate.getValue());	
		searchOrderObList = FXCollections.observableArrayList(Arrays.asList(searchedOrders));
		searchOrderTableView.setItems(searchOrderObList);
		
	}
	
	/**
	 * Method for fetching foodItems from the database
	 */
	private void refreshFoodItems() {
		try {
			// setting foodItems into the menu table
			foodItemObList = FXCollections.observableArrayList(Arrays.asList(controller.readFoodItems()));
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
	private void refreshDummyFoodItem() {
		FoodItem dummyFoodItem = new FoodItem(bundle.getString("newItemText"), 0.0, true);
		List<FoodItem> tempList = new ArrayList<FoodItem>(0);
		tempList.add(dummyFoodItem);
		addItemObList = FXCollections.observableArrayList(tempList);
		addFoodItemTableView.setItems(addItemObList);
		addFoodItemTableView.setEditable(true);
	}
	
	/**
	 * Method for fetching categories from database, and setting them on TableView
	 */
	private void refreshCategories() {
		categoryObList = FXCollections.observableArrayList(controller.readCategories());
		categoryTableView.setItems(categoryObList);
	}
	
	/**
	 * Instantiates a dummy category object for category adding table
	 */
	private void refreshDummyCategory() {
		Category dummyCategory = new Category(bundle.getString("newCategoryText"));
		List<Category> tempList = new ArrayList<Category>(0);
		tempList.add(dummyCategory);
		addCategoryObList = FXCollections.observableArrayList(tempList);
		addCategoryTableView.setItems(addCategoryObList);
		addCategoryTableView.setEditable(true);
	}
	
	/**
	 * Method for fetching ingrtedients from database, and setting them in TableView
	 */
	private void refreshIngredients() {
		ingredientObList = FXCollections.observableArrayList(controller.readIngredients());
		ingredientTableView.setItems(ingredientObList);
	}
	
	/**
	 * Instantiates a dummy ingredient object for ingredient adding table
	 */
	private void refreshDummyIngredient() {
		Ingredient dummyIngredient = new Ingredient(bundle.getString("newIngredientText"), false);
		List<Ingredient> tempList = new ArrayList<Ingredient>(0);
		tempList.add(dummyIngredient);
		addIngredientObList = FXCollections.observableArrayList(tempList);
		addIngredientTableView.setItems(addIngredientObList);
		addIngredientTableView.setEditable(true);
	}
	/**
	 * Method for fetching orders from database
	 */
	private void refreshOrders() {
		Order[] orders = controller.readOrders();
		Arrays.sort(orders);
		orderObList = FXCollections.observableArrayList(orders);
		orderTableView.setItems(orderObList);
	}
	
	// Creating cellfactories for choicebox, button and checkbox columns in ALL table views
	/**
	 * Method that creates custom cellFactories for choicebox, button and checkbox columns in menu TableView. Widgets are created within Callback objects.
	 * 
	 */
	private void createFoodItemCellFactories() {
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
                        Category[] categoryArray = controller.readCategories();
                        String[] categoryStringArray = new String[categoryArray.length];
                		for (int i = 0; i < categoryArray.length; i++) {
                			categoryStringArray[i] = categoryArray[i].getName();
                		}
                    	choiceBox.setItems(FXCollections.observableArrayList(categoryStringArray));
                    	
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
                    Button btn = new Button(bundle.getString("deleteButtonText"));
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            FoodItem foodItem = getTableView().getItems().get(getIndex());
                            System.out.println("poisto selectedData: " + foodItem + ", itemId" + foodItem.getItemId());
                            boolean success = controller.deleteFoodItem(foodItem.getItemId());
                            if(success) {
                            	foodItemTableView.getItems().remove(foodItem);
                            	createNotification(bundle.getString("foodItemDeletionSuccess"));
                            }else {
                            	createNotification(bundle.getString("foodItemDeletionFailure"));
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
		// delete button ends
		
		// creating cellFactory for editbutton
		editFoodItemCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    Button btn = new Button(bundle.getString("saveButtonText"));
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            FoodItem foodItem = getTableView().getItems().get(getIndex());
                            System.out.println("muokkaus selectedData: " + foodItem + ", itemId " + foodItem.getItemId() + ", kateg. " + foodItem.getCategory());
                            boolean success = controller.updateFoodItem(foodItem);
                            if(success) {
                            	createNotification(bundle.getString("foodItemEditSuccess"));
                            }else {
                            	createNotification(bundle.getString("foodItemEditFailure"));
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
		
		// ingredients choicecheckbox cellfactory
		//TODO TRY TO FIX THIS MESS
		/*
		ingredientsFoodItemCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
					Button button = new Button(bundle.getString("ingrtedientsButton"));
					{
                        button.setOnAction((ActionEvent event) -> {
                        	FoodItem foodItem = getTableView().getItems().get(getIndex());
                        	List<String> ingredientInFoodItemList;
                        	if(foodItem.getIngredientsAsList() != null) {
                        		ingredientInFoodItemList = new LinkedList<String>(Arrays.asList(foodItem.getIngredientsAsList()));
                        	}else {
                        		ingredientInFoodItemList = new LinkedList<String>();
                        	}
    						ListView<Ingredient> listView = new ListView<Ingredient>();
    						listView.setItems(ingredientObList);
    						listView.setCellFactory(CheckBoxListCell.forListView(new Callback<Ingredient, ObservableValue<Boolean>>() {
    				            @Override
    				            public ObservableValue<Boolean> call(Ingredient item) {
    				                BooleanProperty observable = new SimpleBooleanProperty();
    				                observable.set(ingredientInFoodItemList.contains(item.getName()));
    				                observable.addListener((obs, wasSelected, isNowSelected) -> {
    				                    System.out.println("Check box for "+item+" changed from "+wasSelected+" to "+isNowSelected);
    				                    
    				                    if(!isNowSelected) {
    				                    	ingredientInFoodItemList.remove(item.getName());
    				                    }else {
    				                    	ingredientInFoodItemList.add(item.getName());
    				                    }
    				                    String[] ingredientInFoodItemArr = new String[ingredientInFoodItemList.size()];
    				                    ingredientInFoodItemArr = ingredientInFoodItemList.toArray(ingredientInFoodItemArr);
    				                    foodItem.setIngredients(ingredientInFoodItemArr);
    				                });
    				                return observable;
    				            }
    				        }));
    						
    						StackPane stackPane = new StackPane(listView);
    	            		Stage popUp = new Stage();
    	            		Scene popUpScene = new Scene(stackPane, 150, 400);
    	            		popUp.setScene(popUpScene);
    	            		popUp.initModality(Modality.APPLICATION_MODAL);	
    	            		
                    		popUp.show();
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {

                        } else {        	
                        	setGraphic(button);
                        }
                    }
                };
				return cell;
			}
		};
		*/
		// ingredients choicecheckbox ends
		
	}

	/**
	 * Method that creates custom cellFactories for addButton and checkbox columns in addFoodItemTableView.
	 */
	private void createAddFoodItemCellFactories() {
		// creating checkbox cellFactory
		addFoodItemInMenuCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
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
		
		// creating cellFactory for addButton
		addFoodItemButtonCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    Button btn = new Button(bundle.getString("addButtonText"));
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            FoodItem foodItem = getTableView().getItems().get(getIndex());
                            System.out.println("lisäys selectedData: " + foodItem + ", itemId " + foodItem.getItemId() + ", kateg. " + foodItem.getCategory());
                            boolean success = controller.createFoodItem(foodItem);
                            if(success) {
                            	createNotification(bundle.getString("foodItemAddSuccess"));
                            }else {
                            	createNotification(bundle.getString("foodItemAddFailure"));
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
		addFoodItemCategoryCBCellFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
                    ChoiceBox<String> choiceBox = new ChoiceBox<String>();
                    {
                        Category[] categoryArray = controller.readCategories();
                        String[] categoryStringArray = new String[categoryArray.length];
                		for (int i = 0; i < categoryArray.length; i++) {
                			categoryStringArray[i] = categoryArray[i].getName();
                		}
                		
                    	choiceBox.setItems(FXCollections.observableArrayList(categoryStringArray));
                    	
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
		
		// TODO delete ccb solution, and replace it with something better
		// ingredients choicecheckbox cellfactory
		/*
		addFoodItemIngredientsCCBFactory = new Callback<TableColumn<FoodItem, Void>, TableCell<FoodItem, Void>>(){
			@Override
			public TableCell<FoodItem, Void> call(TableColumn<FoodItem, Void> arg0) {
				TableCell<FoodItem, Void> cell = new TableCell<FoodItem, Void>() {
					CheckComboBox<Ingredient> ingredientsCCB = new CheckComboBox<Ingredient>(ingredientObList);
					{
						ingredientsCCB.getCheckModel().getCheckedItems().addListener(new ListChangeListener<Ingredient>(){
						     public void onChanged(ListChangeListener.Change<? extends Ingredient> c) {
						    	 ArrayList<String> ingredientList = new ArrayList<String>();
						    	 for(Ingredient i : ingredientsCCB.getCheckModel().getCheckedItems()) {
						    		 ingredientList.add(i.getName());
						    	 }
						    	 // everytime new item is checked the current fooditem's ingredientlist is updated
						    	 FoodItem foodItem = getTableView().getItems().get(getIndex());
						    	 foodItem.setIngredients(ingredientList.toArray(new String[ingredientList.size()]));
						     }
						});
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {

                        } else {   	
                        	ingredientsCCB.getCheckModel().clearChecks();
                        	setGraphic(ingredientsCCB);
                        }
                    }
                };
				return cell;
			}
		};
		*/
		// ingredients choicecheckbox ends
	}
	
	/**
	 * Method that creates custom CellFactories for widget columns in category table
	 */
	private void createCategoryCellFactories() {
		// delete category cellfactory
		categoryDeleteCellFactory = new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>(){
			@Override
			public TableCell<Category, Void> call(TableColumn<Category, Void> arg0) {
				TableCell<Category, Void> cell = new TableCell<Category, Void>() {
                    Button btn = new Button(bundle.getString("deleteButtonText"));
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Category category = getTableView().getItems().get(getIndex());
                            System.out.println("kategorian poisto selectedData: " + category.getName());
                            boolean success = controller.deleteCategoryByName(category.getName());
                            if(success) {
                            	categoryTableView.getItems().remove(category);
                            	createNotification(bundle.getString("categoryDeletionSuccess"));
                            }else {
                            	createNotification(bundle.getString("categoryDeletionFailure"));
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
	private void createAddCategoryCellFactories() {
		// add category cellfactory
		addCategoryButtonCellFactory = new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>(){
			@Override
			public TableCell<Category, Void> call(TableColumn<Category, Void> arg0) {
				TableCell<Category, Void> cell = new TableCell<Category, Void>() {
                    Button btn = new Button(bundle.getString("addButtonText"));
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Category category = getTableView().getItems().get(getIndex());
                            System.out.println("kategorian lisäys selectedData: " + category.getName());
                            boolean success = controller.createCategory(category);
                            if(success) {
                            	createNotification(bundle.getString("categoryAddSuccess"));
                            }else {
                            	createNotification(bundle.getString("categoryAddFailure"));
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
	private void createIngredientCellFactories() {
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
                    Button btn = new Button(bundle.getString("deleteButtonText"));
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Ingredient ingredient = getTableView().getItems().get(getIndex());
                            System.out.println("ainesosan poisto selectedData: " + ingredient.getName());
                            boolean success = controller.deleteIngredient(ingredient.getItemId());
                            if(success) {
                            	ingredientTableView.getItems().remove(ingredient);
                            	createNotification(bundle.getString("ingredientDeleteSuccess"));
                            }else {
                            	createNotification(bundle.getString("ingredientDeleteFailure"));
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
	private void createAddIngredientCellFactories() {
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
                    Button btn = new Button(bundle.getString("addButtonText"));
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Ingredient ingredient = getTableView().getItems().get(getIndex());
                            System.out.println("ainesosan lisäys selectedData: " + ingredient.getName());
                            boolean success = controller.createIngredient(ingredient);
                            if(success) {
                            	createNotification(bundle.getString("ingredientAddSuccess"));
                            }else {
                            	createNotification(bundle.getString("ingredientAddSuccess"));
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
	private void createOrderCellFactories() {
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
		
		//order contents cellfactory
		orderContentsCellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>(){
			@Override
			public TableCell<Order, Void> call(TableColumn<Order, Void> arg0) {
				TableCell<Order, Void> cell = new TableCell<Order, Void>() {
					Button button = new Button(bundle.getString("orderContentsButton"));
					{
                        button.setOnAction((ActionEvent event) -> {
                        	Order order = getTableView().getItems().get(getIndex());
                        	List<String> orderContents = new ArrayList<String>();
                        	Map<String, Integer> contents = order.getOrderContent();
                        	contents.forEach((item, amount) -> orderContents.add(item + " x" + amount));
                        	ObservableList<String> orderContentsObList = FXCollections.observableArrayList(orderContents);
    						ListView<String> listView = new ListView<String>();
    						listView.setItems(orderContentsObList);
    						StackPane stackPane = new StackPane(listView);
    	            		Stage popUp = new Stage();
    	            		Scene popUpScene = new Scene(stackPane, 150, 400);
    	            		popUp.setScene(popUpScene);
    	            		popUp.initModality(Modality.APPLICATION_MODAL);	
    	            		
                    		popUp.show();
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {                       
                        	setGraphic(button);
                        }
                    }
                };
				return cell;
			}
		};
		//order contents cellfactory ends
		
		// creating cellFactory for editbutton
		orderEditCellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>(){
			@Override
			public TableCell<Order, Void> call(TableColumn<Order, Void> arg0) {
				TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                    Button btn = new Button(bundle.getString("saveButtonText"));
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Order order = getTableView().getItems().get(getIndex());
                            System.out.println("tilauksen muutos selectedData: status" + order.isStatus());
                            boolean success = controller.updateOrderStatus(order);
                            if(success) {
                            	createNotification(bundle.getString("orderEditSuccess"));
                            }else {
                            	createNotification(bundle.getString("orderEditFailure"));
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
	/**
	 * Method for creating cellFactories for order search table view
	 */
	private void createSearchOrderCellFactories() {
		// searched order contents
		searchOrderContentsCellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>(){
			@Override
			public TableCell<Order, Void> call(TableColumn<Order, Void> arg0) {
				TableCell<Order, Void> cell = new TableCell<Order, Void>() {
					Button button = new Button(bundle.getString("orderContentsButton"));
					{
                        button.setOnAction((ActionEvent event) -> {
                        	Order order = getTableView().getItems().get(getIndex());
                        	List<String> orderContents = new ArrayList<String>();
                        	Map<String, Integer> contents = order.getOrderContent();
                        	contents.forEach((item, amount) -> orderContents.add(item + " x" + amount));
                        	ObservableList<String> orderContentsObList = FXCollections.observableArrayList(orderContents);
    						ListView<String> listView = new ListView<String>();
    						listView.setItems(orderContentsObList);
    						StackPane stackPane = new StackPane(listView);
    	            		Stage popUp = new Stage();
    	            		Scene popUpScene = new Scene(stackPane, 150, 400);
    	            		popUp.setScene(popUpScene);
    	            		popUp.initModality(Modality.APPLICATION_MODAL);	
    	            		
                    		popUp.show();
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {                       
                        	setGraphic(button);
                        }
                    }
                };
				return cell;
			}
		};
		// order contents ends
	}
	
	/**
	 * Method for setting start class. Used for changing the language of the ui
	 * 
	 * @param start start class
	 */
	public void setStart(IStart start) {
		this.start = start;
	}
}
