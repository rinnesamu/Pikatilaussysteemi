import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.FoodItem;
import model.FoodItemAccessObject;
import model.Order;
import model.OrderAccessObject;

class OrderAccessObjectTest {

	private Order order;
	private Order order2;
	private OrderAccessObject orderDao;
	private FoodItem foodItem;
	private FoodItem foodItem2;
	private Map<FoodItem, Integer> shoppingCart;
	
	private FoodItemAccessObject foodItemDao;
	
	
	@BeforeEach
	void init() {
		foodItem = new FoodItem("kokis", 2.5, true);
		foodItem2 = new FoodItem("Iso Kokis", 3.5, true);
		orderDao = new OrderAccessObject(); // drops table and creates new.-
		foodItemDao = new FoodItemAccessObject(); // drops table and creates new.-
		foodItemDao.createFoodItem(foodItem);
		foodItemDao.createFoodItem(foodItem2);
	
		shoppingCart = new HashMap<>();
		shoppingCart.put(foodItem, 5);
		shoppingCart.put(foodItem2, 3);
		order = new Order(4, shoppingCart);
			
		order = new Order(69, shoppingCart);
		order2 = new Order(45, shoppingCart);
	}
	
	
	@Test
	@DisplayName("Adding order to database")
	void testCreateOrder() {
		assertEquals(true, orderDao.createOrder(order), "Couldn't create order");
	}

	@Test
	@DisplayName("Reading orders")
	void testReadOrders() {
		orderDao.createOrder(order);
		orderDao.createOrder(order2);
		assertEquals(2, orderDao.readOrders().size(), "read orders doesn't work");
	}
	
	@Test
	@DisplayName("Updating order status")
	void testUpdateOrderStatus() {
		orderDao.createOrder(order);
		assertEquals(true, orderDao.updateOrderStatus(order, false), "couldn't update order status");
	}
	
	@Test
	@DisplayName("Reading active orders from db")
	void testReadOrdersByStatus() {
		orderDao.createOrder(order);
		orderDao.createOrder(order2);
		assertEquals(2, orderDao.readOrdersByStatus(true).size(), "couldn't read order by status");
		orderDao.updateOrderStatus(order, false);
		assertEquals(1, orderDao.readOrdersByStatus(true).size(), "couldn't read orders by status after update");
	}
	
}
