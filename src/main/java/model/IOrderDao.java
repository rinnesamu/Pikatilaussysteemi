package model;

import java.util.List;

/**
 * Interface for OrderAccessObject
 * 
 * @author Arttu Seuna
 */
public interface IOrderDao {
	boolean createOrder(Order order);
	Order[] readOrders();
	Order[] readOrdersByStatus(boolean status);
	boolean deleteAllOrders();
	boolean deleteOrder(int orderId);
	Order readOrderById(int orderId);
	boolean updateOrderStatus(Order order);

}
