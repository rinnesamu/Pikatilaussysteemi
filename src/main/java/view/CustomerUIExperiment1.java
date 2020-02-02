package view;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * @author Kimmo Perälä
 *
 */

public class CustomerUIExperiment1 extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		
		// Top part
		String logo = "https://cdn.mos.cms.futurecdn.net/BVb3Wzn9orDR8mwVnhrSyd.jpg";
		Image logo_pic= new Image(logo);
		ImageView logo_view = new ImageView(logo_pic);
		logo_view.setFitHeight(120);
		logo_view.setFitWidth(200);

		Text center = new Text();
		center.setText("TILAA TÄSTÄ");
		center.setFont(new Font(60));
		
		String fin = "https://icons.iconarchive.com/icons/custom-icon-design/"
				+ "all-country-flag/256/Finland-Flag-icon.png";
		Image fin1 = new Image(fin);
		ImageView finFlag = new ImageView(fin1);
		finFlag.setFitHeight(30);
		finFlag.setFitWidth(30);
		
		String eng = "https://icons.iconarchive.com/icons/custom-icon-design/"
				+ "all-country-flag/256/United-Kingdom-flag-icon.png";
		Image eng1 = new Image(eng);
		ImageView engFlag = new ImageView(eng1);
		engFlag.setFitHeight(30);
		engFlag.setFitWidth(30);
		
		Button finLang = new Button("Suomi", finFlag);
		Button engLang = new Button("English", engFlag);
		
		Region region1 = new Region(); // Empty space
		HBox.setHgrow(region1, Priority.ALWAYS);
		Region region2 = new Region();
		HBox.setHgrow(region2, Priority.ALWAYS);
		
		HBox hbox = new HBox(logo_view, region1, center, region2, finLang, engLang);
		hbox.setPadding(new Insets(10, 10, 10, 10));
		
		
		// Middle part
		GridPane gPane = new GridPane();
		gPane.setVgap(10);
		gPane.setHgap(10);
		gPane.setAlignment(Pos.CENTER);
		
		String meal1 = "https://www.mcdonalds.com/is/image/content/dam/usa/"
				+ "nfl/assets/meal/desktop/h-mcdonalds-Double-Quarter-Pounder"
				+ "-with-Cheese-Extra-Value-Meals.jpg";
		String meal2 = "https://assets.grab.com/wp-content/uploads/sites/8/2019/"
				+ "03/12120151/mcdo-fillet-o-fish-grabfood-delivery-700x700.jpg";
		String meal3 = "https://cdn.images.express.co.uk/img/dynamic/14/590x/"
		+ "McDonald-s-burger-876121.jpg";
		String meal4 = meal1;
		String meal5 = meal2;
		String meal6 = meal3;
		
		Image image1 = new Image(meal1);
		ImageView imageView1 = new ImageView(image1);
		imageView1.setFitHeight(160);
		imageView1.setFitWidth(220);
		Button b1 = new Button("Ateria 1", imageView1);
		b1.setContentDisplay(ContentDisplay.TOP);
		
		Image image2 = new Image(meal2);
		ImageView imageView2 = new ImageView(image2);
		imageView2.setFitHeight(160);
		imageView2.setFitWidth(220);
		Button b2 = new Button("Ateria 2", imageView2);
		b2.setContentDisplay(ContentDisplay.TOP);
		
		Image image3 = new Image(meal3);
		ImageView imageView3 = new ImageView(image3);
		imageView3.setFitHeight(160);
		imageView3.setFitWidth(220);
		Button b3 = new Button("Ateria 3", imageView3);
		b3.setContentDisplay(ContentDisplay.TOP);
		
		Image image4 = new Image(meal4);
		ImageView imageView4 = new ImageView(image4);
		imageView4.setFitHeight(160);
		imageView4.setFitWidth(220);
		Button b4 = new Button("Ateria 4", imageView4);
		b4.setContentDisplay(ContentDisplay.TOP);

		Image image5 = new Image(meal5);
		ImageView imageView5 = new ImageView(image5);
		imageView5.setFitHeight(160);
		imageView5.setFitWidth(220);
		Button b5 = new Button("Ateria 5", imageView5);
		b5.setContentDisplay(ContentDisplay.TOP);

		Image image6 = new Image(meal6);
		ImageView imageView6 = new ImageView(image6);
		imageView6.setFitHeight(160);
		imageView6.setFitWidth(220);
		Button b6 = new Button("Ateria 6", imageView6);
		b6.setContentDisplay(ContentDisplay.TOP);

		gPane.add(b1, 0, 0);
		gPane.add(b2, 1, 0);
		gPane.add(b3, 2, 0);
		gPane.add(b4, 0, 1);
		gPane.add(b5, 1, 1);
		gPane.add(b6, 2, 1);
		
		
		// Left part
		Button meals = new Button("Ateriat");
		Button drinks = new Button("Juomat");
		Button burgers = new Button("Purilaiset");
		Button milkshakes = new Button("Pirtelöt");
		Button icecreams = new Button("Jäätelöt");
		meals.setMinWidth(200);
		meals.setMinHeight(80);
		meals.setFont(new Font(25));
		drinks.setMinWidth(200);
		drinks.setMinHeight(80);
		drinks.setFont(new Font(25));
		burgers.setMinWidth(200);
		burgers.setMinHeight(80);
		burgers.setFont(new Font(25));
		milkshakes.setMinWidth(200);
		milkshakes.setMinHeight(80);
		milkshakes.setFont(new Font(25));
		icecreams.setMinWidth(200);
		icecreams.setMinHeight(80);
		icecreams.setFont(new Font(25));
		
		VBox vbox_left = new VBox(meals, drinks, burgers, milkshakes, icecreams);
		
		
		// Right part
		Text cart_title = new Text("Ostoskori");
		cart_title.setFont(new Font(25));
		
		Image cart1 = new Image(meal3);
		ImageView cartV1 = new ImageView(cart1);
		cartV1.setFitHeight(45);
		cartV1.setFitWidth(60);
		String amount1 = "2";
		Button cartItem1 = new Button(amount1 + " x Ateria 3", cartV1);
		cartItem1.setContentDisplay(ContentDisplay.RIGHT);
		
		Image cart2 = new Image(meal5);
		ImageView cartV2 = new ImageView(cart2);
		cartV2.setFitHeight(45);
		cartV2.setFitWidth(60);
		String amount2 = "1";
		Button cartItem2 = new Button(amount2 + " x Ateria 5", cartV2);
		cartItem2.setContentDisplay(ContentDisplay.RIGHT);
		
		Region region3 = new Region(); 		// Empty space
		VBox.setVgrow(region3, Priority.ALWAYS);
		
		Button pay = new Button("MAKSA TUOTTEET");
		pay.setMinWidth(180);
		pay.setMinHeight(80);
		
		VBox vbox_right = new VBox(cart_title, cartItem1, cartItem2, region3, pay);
		String style = "-fx-background-color: rgba(192, 192, 192)";
		vbox_right.setStyle(style);
		vbox_right.setMaxHeight(300);

		
		// Lower part
		Button leftLow = new Button("Anna palautetta");
		leftLow.setMinWidth(180);
		
		Region region4 = new Region(); 		// Empty space
		HBox.setHgrow(region4, Priority.ALWAYS);
		
		Text centerLow = new Text("Mainos");
		centerLow.setFont(new Font(40));
		
		Region region5 = new Region(); 		// Empty space
		HBox.setHgrow(region5, Priority.ALWAYS);
		
		Button rightLow = new Button("Exit");
		rightLow.setFont(new Font(20));
		rightLow.setText("EXIT");
		rightLow.setMinHeight(100);
		rightLow.setMinWidth(160);
		
		HBox hbox2 = new HBox(leftLow, region4, centerLow, region5, rightLow);
		hbox2.setPadding(new Insets(10, 10, 10, 10));
		hbox2.setAlignment(Pos.BOTTOM_LEFT);
		
		
		// Overall position
		BorderPane bPane = new BorderPane();
		bPane.setTop(hbox);
		bPane.setBottom(hbox2);
		bPane.setLeft(vbox_left);
		bPane.setRight(vbox_right);
		bPane.setCenter(gPane);

		Scene scene = new Scene(bPane);
		
		primaryStage.setTitle("Tilauskioski");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
