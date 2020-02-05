package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;


import org.hibernate.annotations.CreationTimestamp;

/**
 * Order luokka sisältää käyttäjän tekemän tilauksen tilaus tunnuksen(orderId), tilausnumeron,
 * aikaleiman tilauksen luonti ajankohdalle, tilauksen lisätieto merkkijonon ja tilauksen tilan totuusarvona
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
	@MapKeyColumn (name="foodItemId")
	@Column(name="amount")
	private Map<FoodItem, Integer> orderContent;
	
	public Order() {
		
	}
	
	/**
	 * Order -luokan konstruktori
	 * 
	 * @param orderNumber - tilaukselle annettava tilausnumero
	 * @param orderContent - hashmap, joka sisältää tilauksen tuotteet ja niiden lukumäärän
	 */
	public Order(int orderNumber, Map<FoodItem, Integer> orderContent) {
		this.orderContent = orderContent;
		this.status = true;
		this.orderNumber = orderNumber;
		this.additionalInfo = "";
	}
	
	/**
	 * Tilauksen tunnusnumeron haku
	 * 
	 * @return orderId - tilauksen tunnusnumero
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Tilauksen tunnusnumeron asettaminen
	 * 
	 * @param orderId - annettu tunnusnumero
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * Tilauksen numeron haku
	 * 
	 * @return orderNumber - tilauksen numero
	 */
	public int getOrderNumber() {
		return orderNumber;
	}
	/**
	 * Asetetaan tilauksen numero
	 * 
	 * @param orderNumber - annettu tilauksen numero
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * Tilauksen lisätietojen haku
	 * 
	 * @return additionalInfo - tilauksen lisätieto merkkijono
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	/**
	 * Annetaan tilaukselle lisätiedot
	 * 
	 * @param additionalInfo - Annettu lisätieto merkkijono
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	/**
	 * Tilauksen statuksen haku.
	 * 
	 * @return status - Tilauksen status totuusarvona
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * Asetetaan tilaukselle status
	 * 
	 * @param status - asetettu status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	/**
	 * Tilauksen luontiajan haku 
	 * 
	 * @return creationTimeStamp - tilauksen luontiaika
	 */
	public LocalDateTime getDate() {
		return creationTimeStamp;
	}
	
	/**
	 * Tilauksen erillisten tuotteiden lukumäärän haku
	 * 
	 * @return erillisten tuotteiden lukumäärä
	 */
	public int getOrderSize() {
		return this.orderContent.size();
	}
	/**
	 * Tilauksen sisällön haku
	 * 
	 * @return Tilauksen sisältö HashMap muodossa
	 */
	public HashMap<FoodItem, Integer> getOrderContent() {
		return (HashMap<FoodItem, Integer>)this.orderContent;
	}
	/**
	 * Tilauksen sisällön asettaminen
	 * 
	 * @param orderContent - tilauksen sisältö
	 */
	public void setOrderContent(Map<FoodItem, Integer> orderContent) {
		this.orderContent = orderContent;
	}
	
	@Override
	public String toString() {
		return "Order: " + this.orderId + ", items in order: " + this.getOrderSize() + ", time created " + this.getDate();
	}
	
}
