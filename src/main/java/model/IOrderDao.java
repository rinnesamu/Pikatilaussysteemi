package model;

import java.util.List;

/**
 * Interface for OrderAccessObject
 * 
 * @author Arttu Seuna
 */
public interface IOrderDao {
	boolean createOrder(Order order);
	List<Order> readOrders();
	/*Order readOrder(int orderId);
	boolean updateOrder(Order order);
	boolean deleteOrder(int orderId);*/
}
