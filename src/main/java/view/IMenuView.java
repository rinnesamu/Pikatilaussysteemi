package view;

import model.Category;
import model.FoodItem;

public interface IMenuView {
	public void createCategoryList(Category[] categories);
	public void setSum(double value);
	public void setElementRemovedIngredients(Object observable, String removedIngredients);
	public void setItems(FoodItem[] items);
	public void noCategories();
	public void createMenu();
	public void emptyCategory();
}
