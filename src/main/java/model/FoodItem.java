package model;
import javax.persistence.*;

/**
 * Represents all food items
 * @author Samu Rinne
 *
 */

@Entity
@Table(name="foodItems")
public class FoodItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int ItemId;
	@Column
	private String name;
	@Column
	private double price;
	@Column
	private boolean inMenu;
	@Column
	private String category;
	@Column
	private int sold;
	@Column
	private int ready;
	
	/**
	 * Empty constructor for hibernate
	 */
	public FoodItem() {
		
	}
	
	/**
	 * 
	 * Constructor
	 * @param name Items name
	 * @param price Items price
	 * @param inMenu Is item in active menu
	 */
	public FoodItem(String name, double price, boolean inMenu) {
		this.price = price;
		this.name = name;
		this.inMenu = inMenu;
		this.category = "None";
		this.sold = 0;
		this.ready = 0;
	}
	
	/**
	 * Constructor in case you need to add category also
	 * @param name Items name
	 * @param price Items price
	 * @param category Items category
	 * @param inMenu Is item in active menu
	 */
	public FoodItem(String name, double price, String category, boolean inMenu) {
		this.price = price;
		this.name = name;
		this.inMenu = inMenu;
		this.category = category;
		this.sold = 0;
		this.ready = 0;
	}

	/**
	 * Getter of price
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Setter for price
	 * @param price items price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Getter for name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * @param name items name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for id
	 * @return itemId
	 */
	public int getItemId() {
		return ItemId;
	}

	/**
	 * Getter for inMenu
	 * @return inMenmu
	 */
	public boolean isInMenu() {
		return inMenu;
	}

	/**
	 * Setter for inMenu
	 * @param inMenu
	 */
	public void setInMenu(boolean inMenu) {
		this.inMenu = inMenu;
	}
	
	/**
	 * Getter for category
	 * @return category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Setter for category
	 * @param category items category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Getter for sold amount
	 * @return sold
	 */
	public int getSold() {
		return sold;
	}

	/**
	 * Setter for sold amount
	 * @param sold how many items have been sold
	 */
	public void setSold(int sold) {
		if (sold < 0) {
			this.sold = 0;
		} else {
			this.sold = sold;
		}
	}

	/**
	 * Getter for ready products
	 * @return ready
	 */
	public int getReady() {
		return ready;
	}

	/**
	 * Setter for ready amount
	 * @param ready how many items are ready
	 */
	public void setReady(int ready) {
		if (ready < 0) {
			this.ready = 0;
		}else {
			this.ready = ready;
		}
	}
	
	/**
	 * Increases sold amount by one
	 */
	public void increaseSold() {
		this.sold++;
		
	}
	
	/**
	 * Increases ready amount by one
	 */
	public void increaseReady() {
		this.ready++;
		
	}
	
	/**
	 * Decreases reaady amount by one
	 */
	public void decreaseReady() {
		if (this.ready > 0) {
			this.ready--;
		}
		
	}
	
	public String toString() {
		return this.name + ", " + this.price + ", " + this.inMenu;
	}

	

}
