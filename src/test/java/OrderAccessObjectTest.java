import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import model.FoodItem;
import model.FoodItemAccessObject;
import model.Order;
import model.OrderAccessObject;

//@TestInstance(Lifecycle.PER_CLASS)
class OrderAccessObjectTest {

	private Order order;
	private Order order2;
	
	private OrderAccessObject orderDao = new OrderAccessObject();
	
	private Map<FoodItem, Integer> shoppingCartContent;
	private Map<FoodItem, Integer> shoppingCartContent2;
	/*
	@BeforeAll
	void bAll() {

	}*/
	
	@BeforeEach
	void init() {
		shoppingCartContent = new HashMap<FoodItem, Integer>();
		shoppingCartContent2 = new HashMap<FoodItem, Integer>();
		FoodItem f1 = new FoodItem("kokis", 2.5, true);
		FoodItem f2 = new FoodItem("fanta", 3.5, true);
		FoodItem f3 = new FoodItem("kakku", 5.0, true);
		shoppingCartContent.put(f1, 3);
		shoppingCartContent.put(f2, 5);

		shoppingCartContent2.put(f3, 4);
		
		order = new Order(69, shoppingCartContent);
		order2 = new Order(666, shoppingCartContent2);
	}
	
	@AfterEach
	void after() {
		orderDao.deleteAllOrders();
	}
	
	@Test
	@DisplayName("Adding order to database")
	void testCreateOrder() {
		assertEquals(true, orderDao.createOrder(order), "Couldn't create order");
		assertEquals(1, orderDao.readOrders().length, "Expected length not fitting");
	}

	@Test
	@DisplayName("Reading orders")
	void testReadOrders() {
		assertEquals(true, orderDao.createOrder(order), "Couldn't create 1st order");
		assertEquals(true, orderDao.createOrder(order2), "Couldn't create 2nd order");
		assertEquals(2, orderDao.readOrders().length, "Couldn't read correct order length");
	}
	
	@Test
	@DisplayName("Reading order by id")
	void testReadOrderById() {
		assertEquals(true, orderDao.createOrder(order), "Couldn't create 1st order");
		assertEquals(true, orderDao.createOrder(order2), "Couldn't create 2nd order");
		assertEquals(orderDao.readOrders()[0].getOrderNumber(), orderDao.readOrderById((orderDao.readOrders()[0]).getOrderId()).getOrderNumber(), "Couldn't read order by id");

	}
	
	@Test
	@DisplayName("Updating order status")
	void testUpdateOrderStatus() {
		order.setStatus(true);
		assertEquals(true, orderDao.updateOrderStatus(order), "Couldn't update order");
	}
	
	/*
	@Test
	@DisplayName("Reading active orders from db")
	void testReadOrdersByStatus() {
		assertEquals(true, orderDao.createOrder(order), "Couldn't create 1st order");
		assertEquals(1, orderDao.readOrdersByStatus(false).length, "Couldn't read orders by status");
	}*/
	
	@Test
	@DisplayName("Deleting all orders from db")
	void testDeleteAllOrders() {
		
		assertEquals(true, orderDao.createOrder(order), "Couldn't create 1st order");
		assertEquals(true, orderDao.createOrder(order2), "Couldn't create 2nd order");
		
		assertEquals(true, orderDao.deleteAllOrders(), "Couldn't delete all food items");
		assertEquals(0, orderDao.readOrders().length, "Couldn't delete all items");
	}
	
}
