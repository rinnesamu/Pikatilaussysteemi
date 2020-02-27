import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.*;

/**
 * Testiluokka Order luokalle
 * 
 * @author Arttu Seuna
 *
 */
class OrderTest {

	private Order order;
	private FoodItem f;
	private FoodItem f2;
	Map<FoodItem, Integer> shoppingCart;
	private final double DELTA = 0.001;
	
	
	@BeforeEach
	void initTestSystem() {
		f = new FoodItem("kokis", 2.5, true);
		f2 = new FoodItem("pulla", 3.0, true);
	
		shoppingCart = new HashMap<>();
		shoppingCart.put(f, 5);
		shoppingCart.put(f2, 3);
		order = new Order(4, shoppingCart);
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
		assertEquals(false, order.isStatus(), "Couldn't check the order status");
	}
	
	@Test
	@DisplayName("Changing the status of the order")
	void testsetStatus() {
		order.setStatus(false);
		assertEquals(false, order.isStatus(), "Couldn't change the order status");
	}
	
	@Test
	@DisplayName("Adding order content as list")
	void testSetOrderContent() {
		order.setOrderContent(shoppingCart);
		assertEquals(2, order.getOrderSize(), DELTA, "Couldn't add the order as list");
	}
	
	@Test
	@DisplayName("Getting the size of the order")
	void testGetOrderSize() {
		order.setOrderContent(shoppingCart);
		assertEquals(2, order.getOrderSize(), DELTA, "Couldn't get order size");
	}

}
