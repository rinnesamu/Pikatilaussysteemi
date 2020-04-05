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

	private static ResourceBundle INSTANCE = null;
	private static Locale curLocale;
	
	private Bundle() {
	}
	public static synchronized ResourceBundle getInstance() {
		if (INSTANCE == null) {
			changeBundle(curLocale);
		}
		return INSTANCE;
	}
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
