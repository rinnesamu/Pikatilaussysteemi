package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;


import org.hibernate.annotations.CreationTimestamp;

/**
 * Order class that is created by the user.
 * 
 * 
 * @author Arttu Seuna
 */

@Entity
@Table(name="orders")
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int orderId;
	@Column
	private int orderNumber;
	@CreationTimestamp
	@Column
	private LocalDateTime creationTimeStamp;
	@Column
	private String additionalInfo;
	@Column
	private boolean status;

	@ElementCollection
	@JoinTable(name="order_content", joinColumns=@JoinColumn(name="orderId"))
	//@MapKeyColumn (name="foodItemId")
	@Column(name="amount")
	private Map<String, Integer> orderContent;
	
	public Order() {
		
	}
	
	/**
	 * Constructor for the Order -class
	 * 
	 * @param orderNumber - ordernumber that is given for the order
	 * @param orderContent - hashmap that contains food items and their amount
	 */
	public Order(int orderNumber, Map<FoodItem, Integer> shoppingCart) {
		Map<String, Integer> orderContent = new HashMap<String, Integer>();
		
		for(Map.Entry<FoodItem, Integer> entry : shoppingCart.entrySet()) {
			orderContent.put(entry.getKey().getName(), entry.getValue());
		}
		
		this.orderContent = orderContent;
		
		
		
		this.status = false;
		this.orderNumber = orderNumber;
		this.additionalInfo = "";
	}
	
	/**
	 * Getter for the order id
	 * 
	 * @return orderId - order id
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Getter for the order number
	 * 
	 * @return orderNumber - order number
	 */
	public int getOrderNumber() {
		return orderNumber;
	}
	/**
	 * Setter for the order number
	 * 
	 * @param orderNumber - order number
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * Getter for additional info
	 * 
	 * @return additionalInfo - additional info of the order
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	/**
	 * Setter for the additional info
	 * 
	 * @param additionalInfo - given additional info
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	/**
	 * Getter for the status of the order, the order is completed when true
	 * 
	 * @return status - status of the order
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * Setter for the order status
	 * 
	 * @param status - given status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	/**
	 * Getter for the order creation timestamp 
	 * 
	 * @return creationTimeStamp - timestamp of the order creation
	 */
	public LocalDateTime getDate() {
		return creationTimeStamp;
	}
	
	/**
	 * Getter for the unique items in the order
	 * 
	 * @return amount of unique items in the order
	 */
	public int getOrderSize() {
		return this.orderContent.size();
	}
	/**
	 * Getter for the content of the order in hashmap
	 * 
	 * @return order content as an hashmap
	 */
	public HashMap<String, Integer> getOrderContent() {
		return (HashMap<String, Integer>)this.orderContent;
	}
	/**
	 * Setter for the order content
	 * 
	 * @param orderContent - order content
	 */
	public void setOrderContent(Map<String, Integer> orderContent) {
		this.orderContent = orderContent;
	}
	
	@Override
	public String toString() {
		return "Order: " + this.orderId + ", items in order: " + this.getOrderSize() + ", time created " + this.getDate();
	}
	
}
