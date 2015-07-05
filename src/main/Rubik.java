package main;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.shape.MeshView;

/**
 *
 * @author jpereda, April 2014 - @JPeredaDnr
 * https://github.com/jperedadnr/RubikFX/blob/master/src/com/jpl/games/model/Rubik.java
 */
public class Rubik {

	private final Group cube = new Group();
	private Map<String, MeshView> mapMeshes = new HashMap<>();
	private final double dimCube;

	private final ContentModel content;

	public Rubik(final int width, final int height) {
		/*
		 * Import Rubik's Cube model and arrows
		 */
		Model3D model = new Model3D();
		model.importObj();
		mapMeshes = model.getMapMeshes();
		cube.getChildren().setAll(mapMeshes.values());
		dimCube = cube.getBoundsInParent().getWidth();

		/*
		 * Create content subscene, add cube, set camera and lights
		 */
		content = new ContentModel(width, 400, dimCube);
		content.setContent(cube);
	}

	public SubScene getSubScene() {
		return content.getSubScene();
	}
}