package model;

import javax.persistence.*;

/**
 * Model for Category
 * @author Samu Rinne
 *
 */

@Entity
@Table(name="Categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	private String name;
	/**
	 * Empty coonstructor for hibernate
	 */
	public Category() {};

	/**
	 * Constructor
	 * @param name Categorys name
	 */
	public Category(String name) {
		this.name = name;
	}

	/**
	 * Getter for id
	 * @return id Categorys id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter for name
	 * @return name Categorys name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * @param name Categorys new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	

}
