package model;

public interface IIngredientDao {
	
	boolean createIngredient(Ingredient ingredient);
	Ingredient[] readIngredients();
	boolean updateIngredient(Ingredient ingredient);
	boolean deleteIngredient(int id);
	Ingredient readIngredientByName(String name);
	Ingredient readIngredient(int id);
	boolean deleteAllIngredients();
}
