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
	private boolean removeable;
	
	/**
	 * Empty constructor for hibernate
	 */
	public Ingredient() {};
	
	/**
	 * Constructor
	 * @param name Ingredients name
	 * @param removeable Tells if you can order item without this ingredient.
	 */
	public Ingredient(String name, boolean removeable) {
		this.name = name;
		this.removeable = removeable;
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
	 * Getter for removeable
	 * @return removeable Can you order item without this ingredient
	 */
	public boolean isRemoveable() {
		return removeable;
	}

	/**
	 * setter for removeable
	 * @param removealbe
	 */
	public void setRemoveable(boolean removealbe) {
		this.removeable = removealbe;
	}

}
