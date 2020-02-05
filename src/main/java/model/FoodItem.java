package model;
import javax.persistence.*;

/**
 * 
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
	
	public FoodItem() {
		
	}
	
	public FoodItem(String name, double price, boolean inMenu) {
		this.price = price;
		this.name = name;
		this.inMenu = inMenu;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getItemId() {
		return ItemId;
	}

	public void setItemId(int itemId) {
		ItemId = itemId;
	}

	public boolean isInMenu() {
		return inMenu;
	}

	public void setInMenu(boolean inMenu) {
		this.inMenu = inMenu;
	}
	
	public String toString() {
		return this.name + ", " + this.price + ", " + this.inMenu;
	}

}
