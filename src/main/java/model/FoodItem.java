package model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private int itemId;
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
	@Column 
	private String path;
	@Column
	private String ingredients;
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
	 * Constructor in case you need to add ItemId also
	 * @param name Items name
	 * @param price Items price
	 * @param inMenu Is item in active menu
	 * @param itemId ItemId number
	 */
	public FoodItem(String name, double price, boolean inMenu, int itemId) {
		this.price = price;
		this.name = name;
		this.inMenu = inMenu;
		this.itemId = itemId;
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
		return itemId;
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
	 * Getter to path for picture
	 * @return path path to picture
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Sets path to pictrue
	 * @param path path to picture
	 */
	public void setPath(String path) {
		this.path = path;
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
	 * Decreases ready amount by one
	 */
	public void decreaseReady() {
		if (this.ready > 0) {
			this.ready--;
		}
	}
	
	/**
	 * Getter for list of ingredients. transforms from string to list. Removes all empty list items.
	 * @return list of ingredients
	 */
	public String[] getIngredientsAsList() {
		System.out.println(this.ingredients);
		if (this.ingredients.trim().length() == 0) {
			return null;
		}
		List<String> items = Arrays.asList(this.ingredients.split("\\s*,\\s*"));
		int i = 0;
		int size = items.size();
		while (i < size) {
			if (items.get(i).trim().length() == 0) {
				items.remove(i);
				size--; // reduces size if deletes item so it wont skip one list item
			}else {
				i++; // otherwise increase i
			}
		}
		for (String s : items) {
			if (s.trim().length() == 0) {
				items.remove(s);
			}
		}
		String[] returnList = new String[items.size()];
		return (String[])items.toArray(returnList);
	}
	
	/**
	 * Sets ingredient string. Transforms from list to string
	 * @param ingredients list of ingredients.
	 */
	public void setIngredients(String[] ingredients) {
		int i = 0;
		int size = ingredients.length;
		while (i < size) {
			if (ingredients[i] != null && ingredients[i].trim().length() != 0) {
				i++; // if ingredient isn't null or empty
			}else {
				for (int a = i; a < size-1; a++) {
					ingredients[a] = ingredients[a+1];
				}
				size--; // reduces size if deletes item so it wont skip one list item
			}
		}
		
		String[] newIngredients = new String[size];
		for (int b = 0; b < size; b++) {
			newIngredients[b] = ingredients[b];
		}
		this.ingredients = String.join(",", newIngredients);
	}
	
	public String toString() {
		return this.name + ", " + this.price + ", " + this.inMenu + ", " + this.itemId;
	}
}
