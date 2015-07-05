package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author jpereda, April 2014 - @JPeredaDnr
 * https://github.com/jperedadnr/RubikFX/blob/master/src/com/jpl/games/RubikFX.java
 */
public class ViewTest extends Application {

	private final BorderPane pane = new BorderPane();
	private Rubik rubik;

	@Override
	public void start(Stage stage) {
		rubik = new Rubik(800,600);

		pane.setCenter(rubik.getSubScene());

		final Scene scene = new Scene(pane, 880, 680, true);
		stage.setTitle("Rubik's Cube - JavaFX3D");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
