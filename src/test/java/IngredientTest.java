import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Ingredient;

class IngredientTest {
	private Ingredient ingredient;
	
	@BeforeEach
	void initTest() {
		ingredient = new Ingredient("Sipuli", true);
	}

	@Test
	@DisplayName("Getting name")
	void testGetName() {
		assertEquals("Sipuli", ingredient.getName(), "Couldn't get right name");
	}

	@Test
	void testSetName() {
		ingredient.setName("Suolakurkku");
		assertEquals("Suolakurkku", ingredient.getName(), "Couldn't set name");
	}

	@Test
	void testIsRemovealbe() {
		assertEquals(true, ingredient.isRemovealbe(), "Couldn't get removeable status");
	}

	@Test
	void testSetRemovealbe() {
		ingredient.setRemovealbe(false);
		assertEquals(false, ingredient.isRemovealbe(), "Couldn't set removeable");
		ingredient.setRemovealbe(false);
		assertEquals(false, ingredient.isRemovealbe(), "Couldn't set removeable from false to false");
	}

}
