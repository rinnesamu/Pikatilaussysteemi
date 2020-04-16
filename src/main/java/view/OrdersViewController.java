package view;

import java.util.ArrayList;
import java.util.List;

import application.IStart;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import model.IOrderDao;
import model.Order;
import model.OrderAccessObject;

public class OrdersViewController {
	
	private IStart start;
	
	@FXML
	private ListView<String> activeOrders, readyOrders;
	
	@FXML
	private Text activeText, readyText;
	
	private ObservableList<String> ready, active;
	
	private IOrderDao orderDao = new OrderAccessObject();

	private Order[] allOrders = orderDao.readOrders();
	
	public void updateOrders() {
		active = FXCollections.observableArrayList();
		ready = FXCollections.observableArrayList();
		System.out.println(allOrders.length);
		String readyString = "";
		String activeString = "";
		int activeCount = 0;
		int readyCount = 0;
		String stringToAdd;
		if (allOrders.length!= 0) {
			for (int i = 0; i < allOrders.length; i++) {
				if(allOrders[i].getOrderNumber() < 10) {
					stringToAdd = "0" + allOrders[i].getOrderNumber();
				}else {
					stringToAdd = Integer.toString(allOrders[i].getOrderNumber());
				}
				if (allOrders[i].isStatus() == false) {
					activeString = activeString + "   " + stringToAdd + "   ";
					activeCount++;
					if (activeCount % 4 == 0) {
						active.add(activeString);
						activeString = "";
					}
					
				}else {
					readyString = readyString + "   " + stringToAdd + "   ";
					readyCount++;
					if (readyCount % 4 == 0) {
						ready.add(readyString);
						readyString = "";
					}
				}
			}
			if (!activeString.equals("")) {
				active.add(activeString);
			}
			if (!readyString.equals("")) {
				ready.add(readyString);
			}
			activeOrders.setItems(active);
			readyOrders.setItems(ready);
		}
		
	}
	
	public void initialize() {
		updateOrders();
	}
	
	public void setStart(IStart s) {
		this.start = s;
	}
	
}
