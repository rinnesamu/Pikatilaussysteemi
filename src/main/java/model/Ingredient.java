package model;

import javax.persistence.*;

@Entity
@Table(name="Ingredients")
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int ItemId;
	@Column
	private String name;
	@Column
	private boolean removealbe;
	
	public Ingredient() {};
	
	public Ingredient(String name, boolean removeable) {
		this.name = name;
		this.removealbe = removeable;
	}

	public int getItemId() {
		return ItemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRemovealbe() {
		return removealbe;
	}

	public void setRemovealbe(boolean removealbe) {
		this.removealbe = removealbe;
	}

}
