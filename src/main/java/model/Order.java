package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import org.hibernate.annotations.CreationTimestamp;

/**
 * @author Arttu Seuna
 */

@Entity
@Table(name="orders")
public class Order {

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
	private List<Integer> orderContent;
	
	public Order() {
		
	}
	
	public Order(int orderNumber) {
		this.orderContent = new ArrayList<>();
		this.status = true;
		this.orderNumber = orderNumber;
		this.additionalInfo = "";
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDateTime getDate() {
		return creationTimeStamp;
	}
	
	public int getOrderSize() {
		return this.orderContent.size();
	}
	
	public void addItemToOrder(FoodItem foodItem) {
		orderContent.add(foodItem.getItemId());
	}
	public void deleteItemFromOrder(FoodItem foodItem) {
		orderContent.remove(foodItem.getItemId());
	}
	
	@Override
	public String toString() {
		return "Order: " + this.orderId + ", items in order: " + this.getOrderSize() + ", time created " + this.getDate();
	}
	
}
