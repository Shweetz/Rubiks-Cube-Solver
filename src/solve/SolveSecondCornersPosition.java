package solve;

import java.awt.Color;

import main.Model;
import main.Solution;

public class SolveSecondCornersPosition extends Model {

	public Color[][][] rubiksCubeSecondCornersPosition = new Color[6][3][3]; // rubiksCubeSCP : rubiksCubeSecondCornersPosition
			
	public SolveSecondCornersPosition(Color[][][] rubiksCubeSecondEdges) 
	{
		// Orange is front face and yellow is top face
		rubiksCubeSecondCornersPosition = rubiksCubeSecondEdges;
		InitializeOtherSideTab();
	}
	
	String getDirectionOfAlgorithm()
	{
		String cornersState = "";
		
		// If YGR is at YGO's place
		if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 0, 0, 0, Color.yellow, Color.green) 
		 || checkUnitaryCube(rubiksCubeSecondCornersPosition, 4, 0, 2, Color.yellow, Color.green)
		 || checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 2, 0, Color.yellow, Color.green))
		{
			cornersState = "clockwise";
		}
		// If YGR is at YBR's place
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 3, 0, 0, Color.yellow, Color.green) 
			  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 1, 0, 2, Color.yellow, Color.green)
			  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 0, 2, Color.yellow, Color.green))
		{
			cornersState = "counterclockwise";
		}
		// If YGO is at YGR's place
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 4, 0, 0, Color.yellow, Color.orange) 
			  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 3, 0, 2, Color.yellow, Color.orange)
			  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 0, 0, Color.yellow, Color.orange))
		{
			cornersState = "counterclockwise";
		}
		// If YGO is at YBO's place
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 1, 0, 0, Color.yellow, Color.orange) 
			  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 0, 0, 2, Color.yellow, Color.orange)
			  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 2, 2, Color.yellow, Color.orange))
		{
			cornersState = "clockwise";
		}
		// If YBR is at YGR's place
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 4, 0, 0, Color.yellow, Color.red) 
			  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 3, 0, 2, Color.yellow, Color.red)
			  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 0, 0, Color.yellow, Color.red))
		{
			cornersState = "clockwise";
		}
		// If YBR is at YBO's place
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 1, 0, 0, Color.yellow, Color.red) 
			  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 0, 0, 2, Color.yellow, Color.red)
			  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 2, 2, Color.yellow, Color.red))
		{
			cornersState = "counterclockwise";
		}
		
		return cornersState;
	}
	
	int[] getCorrectlyPositionedCorners()
	{
		int[] corner = new int[5];
		
		if 		(checkUnitaryCube(rubiksCubeSecondCornersPosition, 1, 0, 0, Color.yellow, Color.blue)) corner[1] = 1;
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 0, 0, 2, Color.yellow, Color.blue)) corner[1] = 1;
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 2, 2, Color.yellow, Color.blue)) corner[1] = 1;

		if 		(checkUnitaryCube(rubiksCubeSecondCornersPosition, 0, 0, 0, Color.yellow, Color.orange)) corner[2] = 1;
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 4, 0, 2, Color.yellow, Color.orange)) corner[2] = 1;
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 2, 0, Color.yellow, Color.orange)) corner[2] = 1;

		if 		(checkUnitaryCube(rubiksCubeSecondCornersPosition, 4, 0, 0, Color.yellow, Color.green)) corner[3] = 1;
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 3, 0, 2, Color.yellow, Color.green)) corner[3] = 1;
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 0, 0, Color.yellow, Color.green)) corner[3] = 1;

		if 		(checkUnitaryCube(rubiksCubeSecondCornersPosition, 3, 0, 0, Color.yellow, Color.red)) corner[4] = 1;
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 1, 0, 2, Color.yellow, Color.red)) corner[4] = 1;
		else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 0, 2, Color.yellow, Color.red)) corner[4] = 1;
		
		corner[0] = corner[1] + corner[2] + corner[3] + corner[4];
		
		return corner;
	}
	
	public void doSecondCornersPosition(Solution solution)
	{
		String message = "";
		String cornersState = "";
		int corner[] = getCorrectlyPositionedCorners();
		int numberOfCorners = corner[0];
	
		int faceToTakeTop = 2; // never changes
		int faceToTakeRight = -1;
		int faceToTakeLeft = -1;
		
		while (numberOfCorners != 4)
		{
			if (numberOfCorners == 1) // If 1 corner is correct, just swap the 3 others
			{
				cornersState = getDirectionOfAlgorithm();
				
				// Find the corner placed
				String cornerPlaced = "";
				if (corner[1] == 1) 
				{
					faceToTakeRight = 4;	
					cornerPlaced = "jaune-bleu-orange";
				}
				else if (corner[2] == 1) 
				{
					faceToTakeRight = 3;	
					cornerPlaced = "jaune-orange-vert";
				}	
				else if (corner[3] == 1) 
				{
					faceToTakeRight = 1;	
					cornerPlaced = "jaune-vert-rouge";
				}	
				else if (corner[4] == 1) 
				{
					faceToTakeRight = 0;	
					cornerPlaced = "jaune-rouge-bleu";
				}	
				
				if (cornersState == "clockwise") // Put the well-placed edge in the top-left and algorithm
				{ 					
					message = "On a le coin " + cornerPlaced + " placé au bon endroit et il suffit de tourner les 3 autres "
							+ "dans le sens horaire pour tous les placer au bon endroit. On va placer ce coin en haut "
							+ "à gauche et appliquer l'algorithme des coins de la dernière face dans le sens horaire :\n"
							+ "L R' U L' U' R U L U' L'";
					
				}
				else if (cornersState == "counterclockwise") // Put the well-placed edge in the top-right and algorithm
				{ 
					message = "On a le coin " + cornerPlaced + " placé au bon endroit et il suffit de tourner les 3 autres "
							+ "dans le sens anti-horaire pour tous les placer au bon endroit. On va placer ce coin en haut "
							+ "à droite et appliquer l'algorithme des coins de la dernière face dans le sens anti-horaire. "
							+ "C'est le même que dans le sens horaire mais il faut échanger les U avec U', les L avec R' "
							+ "et les L' avec les R (sauf le L R' du début qui ne change pas) :\n"
							+ "L R' U' R U L' U' R' U R";					
				}
			}
			else if (numberOfCorners == 0) // Place the YBO corner
			{
				// Find YBO
				if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 0, 0, 0, Color.yellow, Color.blue) // On YOG spot
				 || checkUnitaryCube(rubiksCubeSecondCornersPosition, 4, 0, 2, Color.yellow, Color.blue) 
				 || checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 2, 0, Color.yellow, Color.blue))
				{
					faceToTakeRight = 1; 
					cornersState = "counterclockwise";
					
					message = "On n'a pas de coin placé au bon endroit donc on va commencer par placer le coin "
							+ "jaune-bleu-orange. On va ainsi prendre la face bleue à droite et appliquer "
							+ "l'algorithme des coins de la dernière face dans le sens anti-horaire. "
							+ "C'est le même que dans le sens horaire mais il faut échanger les U avec U', les L avec R' "
							+ "et les L' avec les R (sauf le L R' du début qui ne change pas) :\n"
							+ "L R' U' R U L' U' R' U R";	
				}
				else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 4, 0, 0, Color.yellow, Color.blue) // On YGR spot
					  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 3, 0, 2, Color.yellow, Color.blue)
					  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 0, 0, Color.yellow, Color.blue))
				{
					faceToTakeRight = 0; 
					cornersState = "clockwise";
					
					message = "On n'a pas de coin placé au bon endroit donc on va commencer par placer le coin "
							+ "jaune-bleu-orange. On va ainsi prendre la face orange à droite et appliquer "
							+ "l'algorithme des coins de la dernière face dans le sens horaire :\n"
							+ "L R' U L' U' R U L U' L'";
				}

				else if (checkUnitaryCube(rubiksCubeSecondCornersPosition, 3, 0, 0, Color.yellow, Color.blue) // On YRB spot
					  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 1, 0, 2, Color.yellow, Color.blue) 
					  || checkUnitaryCube(rubiksCubeSecondCornersPosition, 2, 0, 2, Color.yellow, Color.blue))
				{
					faceToTakeRight = 1; 
					cornersState = "clockwise";
					
					message = "On n'a pas de coin placé au bon endroit donc on va commencer par placer le coin "
							+ "jaune-bleu-orange. On va ainsi prendre la face bleue à droite et appliquer "
							+ "l'algorithme des coins de la dernière face dans le sens horaire :\n"
							+ "L R' U L' U' R U L U' L'";
				}
			}

			faceToTakeLeft = (faceToTakeRight+3)%6;
			
			// Use the appropriate algorithm
			if (cornersState == "clockwise") // Put the well-placed edge in the top-left and algorithm
			{ 					
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeLeft, 1, message, "second corners position"); // L
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeRight, 3, message, "second corners position"); // R'
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeTop, 1, message, "second corners position"); // U
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeLeft, 3, message, "second corners position"); // L'
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeTop, 3, message, "second corners position"); // U'
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeRight, 1, message, "second corners position"); // R
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeTop, 1, message, "second corners position"); // U
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeLeft, 1, message, "second corners position"); // L
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeTop, 3, message, "second corners position"); // U'
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeLeft, 3, message, "second corners position"); // L'
				
			}
			else if (cornersState == "counterclockwise") // Put the well-placed edge in the top-right and algorithm
			{ 
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeLeft, 1, message, "second corners position"); // L
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeRight, 3, message, "second corners position"); // R'
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeTop, 3, message, "second corners position"); // U'
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeRight, 1, message, "second corners position"); // R
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeTop, 1, message, "second corners position"); // U
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeLeft, 3, message, "second corners position"); // L'
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeTop, 3, message, "second corners position"); // U'
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeRight, 3, message, "second corners position"); // R'
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeTop, 1, message, "second corners position"); // U
				fillAnswerTab(solution, rubiksCubeSecondCornersPosition, faceToTakeRight, 1, message, "second corners position"); // R					
			}
			
			corner = getCorrectlyPositionedCorners();
			numberOfCorners = corner[0];
		}
	}
}
