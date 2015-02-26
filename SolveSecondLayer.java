
import java.awt.Color;

public class SolveSecondLayer extends Model {

	Color[][][] rubiksCubeSecondLayer = new Color[6][3][3];
			
	public SolveSecondLayer(Color[][][] rubiksCubeFirstCorners) 
	{
		rubiksCubeSecondLayer = rubiksCubeFirstCorners;
		InitializeOtherSideTab();
	}
	
	void doOneEdge(Solution solution, Color c1, Color c2)
	{
		String message = "";
		String bord = "";
		int goodFace = -1;
		int faceToTakeTop = 2;
		int faceToTakeFront = -1;
		int faceToTakeLeft = -1;
		int faceToTakeRight = -1;
		
		int[] edgePos = findUnitaryEdge(rubiksCubeSecondLayer, c1, c2);
		if (c1.equals(Color.orange))
		{
			bord += "orange-";
			goodFace = 0;
		}
		else if (c1.equals(Color.blue))
		{
			bord += "bleu-";
			goodFace = 1;
		}
		else if (c1.equals(Color.red))
		{
			bord += "rouge-";
			goodFace = 3;
		}
		else if (c1.equals(Color.green))
		{
			bord += "vert-";
			goodFace = 4;
		}
		
		if (c2.equals(Color.orange))
			bord += "orange";
		else if (c2.equals(Color.blue))
			bord += "bleu";
		else if (c2.equals(Color.red))
			bord += "rouge";
		else if (c2.equals(Color.green))
			bord += "vert";
		
		// If the edge is already placed
		if (edgePos[0] == goodFace && edgePos[1] == 1 && edgePos[2] == 2) 
		{ }
		// If the edge is in the middle layer, need to take it away from there
		else if (edgePos[0] != 2 && edgePos[1] == 1) 
		{
			message = "Le bord " + bord + " se trouve sur la tranche du milieu mais mal placé, il faut le sortir "
					+ "(le placer sur la tranche du haut) pour ensuite pouvoir le placer à sa position finale. "
					+ "Pour cela, il faut se placer de manière à avoir ce bord entre la face de devant et la face "
					+ "de droite, tout en gardant la face jaune en haut, puis appliquer l'algorithme pour placer le "
					+ "bord en haut de la face de devant à la droite de la face de devant. C'est l'algorithme suivant :\n"
					+ "U R U' R' U' F' U F";
			
			if (edgePos[2] == 2) // on the middle-right
			{
				faceToTakeFront = edgePos[0];
				faceToTakeRight = otherSideOfUnitaryCube[edgePos[0]][edgePos[1]][edgePos[2]]/100;
			}
			else if (edgePos[2] == 0) // on the middle-left
			{
				faceToTakeFront = otherSideOfUnitaryCube[edgePos[0]][edgePos[1]][edgePos[2]]/100;
				faceToTakeRight = edgePos[0];
			}
			
			fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 1, message, "second layer");	 // U
			fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeRight, 1, message, "second layer"); // R
			fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 3, message, "second layer");	 // U'
			fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeRight, 3, message, "second layer"); // R'
			fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 3, message, "second layer");	 // U'
			fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeFront, 3, message, "second layer"); // F'
			fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 1, message, "second layer");	 // U
			fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeFront, 1, message, "second layer"); // F

			edgePos = findUnitaryEdge(rubiksCubeSecondLayer, c1, c2);
		}
		// If the edge needs to be placed on c2's face (c1 on top face)
		if (edgePos[0] == 2)
		{ 
			// Swap colors so c1 is the color of the face to do the algorithm on
			Color temp = c1;
			c1 = c2;
			c2 = temp;
			
			// Update interesting stuff
			if 		(c1.equals(Color.orange))	
			{
				faceToTakeFront = 0;
				faceToTakeLeft = 4;
			}
			else if (c1.equals(Color.blue))		
			{
				faceToTakeFront = 1;
				faceToTakeLeft = 0;
			}
			else if (c1.equals(Color.red))		
			{
				faceToTakeFront = 3;
				faceToTakeLeft = 1;
			}
			else if (c1.equals(Color.green))		
			{
				faceToTakeFront = 4;
				faceToTakeLeft = 3;
			}
			goodFace = faceToTakeFront;
			
			edgePos = findUnitaryEdge(rubiksCubeSecondLayer, c1, c2);
		}
		// Place the edge, should be always true when coming here if edge isn't already placed
		if (edgePos[0] != 2 && edgePos[1] == 0) 
		{
			// Move the top face the match the edge with the center
			while (edgePos[0] != goodFace)
			{
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 1, message, "second layer");
				edgePos = findUnitaryEdge(rubiksCubeSecondLayer, c1, c2);
			}
			
			// Algorithm
			if (faceToTakeLeft > -1) // On the left
			{
				message = "Le bord " + bord + " se trouve sur la tranche du haut donc on va pouvoir le placer. "
						+ "Pour cela, tourner la face du haut de manière à avoir ce bord de la même couleur que le centre de "
						+ "la face latérale sur laquelle on le place, ce qui devrait former un T à l'envers, car on aura la "
						+ "1e couronne, le centre et ce bord de la même couleur. Ensuite il ne reste plus qu'à appliquer "
						+ "l'algorithme pour placer le bord à gauche, l'algorithme est un peu modifié par rapport à "
						+ "placer le bord à droite (il faut échanger U avec U', F avec F', R avec L' et R' avec L) :\n"
						+ "U' L' U L U F U' F'";
				
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 3, message, "second layer");	 // U'
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeLeft, 3, message, "second layer");  // L'
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 1, message, "second layer");	 // U
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeLeft, 1, message, "second layer");  // L
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 1, message, "second layer");	 // U
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeFront, 1, message, "second layer"); // F
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 3, message, "second layer");	 // U'
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeFront, 3, message, "second layer"); // F'
			}
			else // On the right
			{
				message = "Le bord " + bord + " se trouve sur la tranche du haut donc on va pouvoir le placer. "
						+ "Pour cela, tourner la face du haut de manière à avoir ce bord de la même couleur que le centre de "
						+ "la face latérale sur laquelle on le place, ce qui devrait former un T à l'envers, car on aura la "
						+ "1e couronne, le centre et ce bord de la même couleur. Ensuite il ne reste plus qu'à appliquer "
						+ "l'algorithme pour placer le bord à droite :\n"
						+ "U R U' R' U' F' U F\n";
				
				faceToTakeFront = edgePos[0];
				if (faceToTakeFront%3 == 0)
					faceToTakeRight = faceToTakeFront+1;
				else if (faceToTakeFront%3 == 1)
					faceToTakeRight = (faceToTakeFront+2)%6;
				
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 1, message, "second layer");	 // U
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeRight, 1, message, "second layer"); // R
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 3, message, "second layer");	 // U'
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeRight, 3, message, "second layer"); // R'
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 3, message, "second layer");	 // U'
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeFront, 3, message, "second layer"); // F'
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeTop, 1, message, "second layer");	 // U
				fillAnswerTab(solution, rubiksCubeSecondLayer, faceToTakeFront, 1, message, "second layer"); // F
			}
		}
	}
	
	void doSecondLayer(Solution solution)
	{
		// Start with orange so that it doesn't scramble a solved first corners
		doOneEdge(solution, Color.orange, Color.blue);
		doOneEdge(solution, Color.blue, Color.red);
		doOneEdge(solution, Color.red, Color.green);
		doOneEdge(solution, Color.green, Color.orange); 
	}
}
