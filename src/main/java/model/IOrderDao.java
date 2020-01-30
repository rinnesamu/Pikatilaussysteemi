package model;
/**
 * 
 * @author Arttu Seuna
 */
public interface IOrderDao {
	boolean createOrder(Order order);
	Order[] readOrders();
	Order readOrder(int orderId);
	boolean updateOrder(Order order);
	boolean deleteOrder(int orderId);
}
