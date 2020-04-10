package view;

import model.Category;
import model.FoodItem;

public interface IMenuView {
	public void createCategoryList(Category[] categories);
	public void setSum();
	public void setItems(FoodItem[] items);
	public void createMenu();
	public void emptyCategory();
}
