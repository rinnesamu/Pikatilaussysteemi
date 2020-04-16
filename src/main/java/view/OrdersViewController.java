package view;

import java.util.ArrayList;
import java.util.List;

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
	
	@FXML
	private ListView<String> activeOrders, readyOrders;
	
	@FXML
	private Text activeText, readyText;
	
	private ObservableList<String> ready, active;
	
	private IOrderDao orderDao = new OrderAccessObject();

	private Order[] allOrders;
	
	public void updateOrders() {
		active = FXCollections.observableArrayList();
		ready = FXCollections.observableArrayList();
		allOrders = orderDao.readOrders();
		System.out.println(allOrders.length);
		if (allOrders.length!= 0) {
			for (Order o : allOrders) {
				if (o.isStatus() == false) {
					active.add(Integer.toString(o.getOrderNumber()));
				}else {
					ready.add(Integer.toString(o.getOrderNumber()));
				}
			}
			activeOrders.setItems(active);
			readyOrders.setItems(ready);
		}
		
	}
	
	public void initialize() {
		updateOrders();
	}
	
}
