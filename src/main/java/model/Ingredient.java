package model;

import javax.persistence.*;

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
	
	public Ingredient() {};
	
	public Ingredient(String name, boolean removeable) {
		this.name = name;
		this.removeable = removeable;
	}

	public int getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRemoveable() {
		return removeable;
	}

	public void setRemoveable(boolean removealbe) {
		this.removeable = removealbe;
	}

}
