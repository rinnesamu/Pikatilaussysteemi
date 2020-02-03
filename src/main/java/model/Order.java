package model;

import java.time.LocalDateTime;
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
	private int OrderId;
	@Column
	private int orderNumber;
	@CreationTimestamp
	@Column
	private LocalDateTime creationTimeStamp;
	@Column
	private String additionalInfo;
	@Column
	private boolean status;
	
	public Order() {
		
	}
	
	public Order(String additionalInfo, boolean status, int orderNumber) {
		this.additionalInfo = additionalInfo;
		this.status = status;
		this.orderNumber = orderNumber;
	}
	
	public int getOrderId() {
		return OrderId;
	}

	public void setOrderId(int orderId) {
		OrderId = orderId;
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
	
}
