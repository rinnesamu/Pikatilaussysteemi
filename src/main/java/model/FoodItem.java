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
	@Column
	private String category;
	@Column
	private int sold;
	@Column
	private int ready;
	
	public FoodItem() {
		
	}
	
	public FoodItem(String name, double price, boolean inMenu) {
		this.price = price;
		this.name = name;
		this.inMenu = inMenu;
		this.category = "None";
		this.sold = 0;
		this.ready = 0;
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
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		if (sold < 0) {
			this.sold = 0;
		} else {
			this.sold = sold;
		}
	}

	public int getReady() {
		return ready;
	}

	public void setReady(int ready) {
		if (ready < 0) {
			this.ready = 0;
		}else {
			this.ready = ready;
		}
	}
	
	public void increaseSold() {
		this.sold++;
		
	}
	
	public void increaseReady() {
		this.ready++;
		
	}
	
	public void decreaseReady() {
		if (this.ready > 0) {
			this.ready--;
		}
		
	}
	
	public String toString() {
		return this.name + ", " + this.price + ", " + this.inMenu;
	}

	

}
