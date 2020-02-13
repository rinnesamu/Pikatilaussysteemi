package model;

import javax.persistence.*;

/**
 * 
 * @author Samu Rinne
 *
 */

@Entity
@Table(name="Categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int Id;
	
	@Column
	private String name;
	
	public Category() {};
	

	public Category(String name) {
		this.name = name;
	}

	public int getId() {
		return Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
