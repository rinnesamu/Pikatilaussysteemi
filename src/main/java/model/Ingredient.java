package model;

import javax.persistence.*;

/**
 * Model for Ingredients
 * @author Samu Rinne
 *
 */

@Entity
@Table(name="Ingredients")
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int itemId;
	@Column
	private String name;
	@Column
	private boolean removable;
	
	/**
	 * Empty constructor for hibernate
	 */
	public Ingredient() {};
	
	/**
	 * Constructor
	 * @param name Ingredients name
	 * @param removable Tells if you can order item without this ingredient.
	 */
	public Ingredient(String name, boolean removable) {
		this.name = name;
		this.removable = removable;
	}

	/**
	 * Getter for id
	 * @return id
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * Getter for name
	 * @return name Ingredients name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * @param name Ingredients new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for removable
	 * @return removable Can you order item without this ingredient
	 */
	public boolean isRemoveable() {
		return removable;
	}

	/**
	 * setter for removeable
	 * @param removealbe Tells if you can remove ingredient from product
	 */
	public void setRemoveable(boolean removable) {
		this.removable = removable;
	}

}
