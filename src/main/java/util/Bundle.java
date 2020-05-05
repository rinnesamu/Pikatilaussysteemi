package util;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Creates or changes ResourceBundle (internationalization)
 * 
 * @author Kimmo Perälä
 *
 */

public class Bundle {

	/**
	 * Singleton instance
	 */
	private static ResourceBundle INSTANCE = null;
	/**
	 * 	Locale object (language and country)
	 */
	private static Locale curLocale;
	
	private Bundle() {
	}
	
	/**
	 * Singleton implementation
	 * @return Singleton instance
	 */
	public static synchronized ResourceBundle getInstance() {
		if (INSTANCE == null) {
			changeBundle(curLocale);
		}
		return INSTANCE;
	}
	
	/**
	 * Change the Locale object
	 * @param curLocale The new Locale object.
	 */
	public static synchronized void changeBundle(Locale curLocale) {
		try {
			INSTANCE = ResourceBundle.getBundle("TextResources", curLocale);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Default TextProperties not found.");
			System.exit(0);
		}
	}
}
