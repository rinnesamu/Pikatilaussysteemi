

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.FoodItem;
import model.FoodItemAccessObject;

class FoodItemAccessObjectTest {
	static FoodItemAccessObject foodItemDao = new FoodItemAccessObject();
	static Scanner scanner = new Scanner(System.in);
	
	// Test started
	
	@BeforeAll
	

	@Test
	void testCreate() {
		String name = "juoma1";
		double price = 4.5;
		boolean inMenu = true;
		FoodItem foodItem = new FoodItem(name, price, inMenu);
		boolean success = foodItemDao.createFoodItem(foodItem);
	}

}
