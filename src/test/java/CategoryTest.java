import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Category;

class CategoryTest {
	Category cat;
	
	@BeforeEach
	void initTest() {
		cat = new Category("Juomat");
    }

	@Test
	@DisplayName("Getting name")
	void testGetName() {
		assertEquals("Juomat", cat.getName(), "Couldn't get name");
	}

	@Test
	@DisplayName("Setting name")
	void testSetName() {
		cat.setName("juomat");
		assertEquals("juomat", cat.getName(), "Couldn't set name");
	}

}
