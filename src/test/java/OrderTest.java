import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.*;

/**
 * 
 * @author Arttu Seuna
 *
 */
class OrderTest {

	private Order order;
	private FoodItem f;
	private FoodItem f2;
	List<Integer> orderList;
	private final double DELTA = 0.001;
	
	
	@BeforeEach
	void initTestSystem() {
		f = new FoodItem("kokis", 2.5, true);
		f2 = new FoodItem("pulla", 3.0, true);
		order = new Order(4);
		
		orderList = new ArrayList<>();
		orderList.add(f.getItemId());
		orderList.add(f.getItemId());
		orderList.add(f2.getItemId());
	}
	
	@Test
	@DisplayName("Getting order number")
	void testGetOrderNumber() {
		assertEquals(4, order.getOrderNumber(), DELTA, "Wrong order number");
	}
	
	@Test
	@DisplayName("Setting order number")
	void testSetOrderNumber() {
		order.setOrderNumber(10);
		assertEquals(10, order.getOrderNumber(), DELTA, "Couldn't change order number");
	}
	
	@Test
	@DisplayName("Getting additional info")
	void testGetAdditionalInfo() {
		assertEquals("", order.getAdditionalInfo(), "Wrong additional info");
	}
	
	@Test
	@DisplayName("Setting additional info")
	void testSetAddtionalInfo() {
		order.setAdditionalInfo("5 extra ketchup packets");
		assertEquals("5 extra ketchup packets", order.getAdditionalInfo(), "Couldn't change additional info");

	}
	
	@Test
	@DisplayName("Checking the status of the order")
	void testIsStatus() {
		assertEquals(true, order.isStatus(), "Couldn't check the order status");
	}
	
	@Test
	@DisplayName("Changing the status of the order")
	void testsetStatus() {
		order.setStatus(false);
		assertEquals(false, order.isStatus(), "Couldn't change the order status");
	}
	
	@Test
	@DisplayName("Getting the size of the order")
	void testGetOrderSize() {
		order.addItemToOrder(f);
		order.addItemToOrder(f);
		assertEquals(2, order.getOrderSize(), DELTA, "Couldn't get order size");
	}
	@Test
	@DisplayName("Adding order content as list")
	void testSetOrderContent() {
		order.setOrderContent(orderList);
		assertEquals(3, order.getOrderSize(), DELTA, "Couldn't add the order as list");
	}
	
	@Test
	@DisplayName("Adding food items to order")
	void testAddItemToOrder() {
		order.addItemToOrder(f2);
		assertEquals(1, order.getOrderSize(), DELTA, "Couldn't add item to order");
	}
	
	@Test
	@DisplayName("Deleting food items from order")
	void testDeleteItemFromOrder() {
		order.addItemToOrder(f);
		order.addItemToOrder(f);
		order.deleteItemFromOrder(f);
		assertEquals(1, order.getOrderSize(), DELTA, "Couldn't delete item from order");
	}
	
	
}
