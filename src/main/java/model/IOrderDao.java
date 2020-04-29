package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for Order Data access object
 * 
 * @author Arttu Seuna
 */
public interface IOrderDao {
	public boolean createOrder(Order order);
	public Order[] readOrders();
	public boolean deleteAllOrders();
	public boolean deleteOrder(int orderId);
	public Order readOrderById(int orderId);
	public boolean updateOrderStatus(Order order);
	public Order[] readOrdersByDate(LocalDateTime startDate, LocalDateTime endDate);
	
}
