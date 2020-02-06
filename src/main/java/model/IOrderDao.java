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
	boolean updateOrderStatus(Order order, boolean status);
	List<Order> readOrdersByStatus(boolean status);

}
