
import java.awt.Color;

public class SolveSecondCornersOrientation extends Model {

	Color[][][] rubiksCubeSCO = new Color[6][3][3]; // rubiksCubeSCO : rubiksCubeSecondCornersOrientation
			
	public SolveSecondCornersOrientation(Color[][][] rubiksCubeSCP) 
	{
		// Orange is front face and yellow is top face
		rubiksCubeSCO = rubiksCubeSCP;
		InitializeOtherSideTab();
	}
	
	/*String getDirectionOfAlgorithm()
	{
		String cornersState = "";
		
		// If YGR is at YGO's place
		if (checkUnitaryCube(rubiksCubeSCO, 0, 0, 0, Color.yellow, Color.green) 
		 || checkUnitaryCube(rubiksCubeSCO, 4, 0, 2, Color.yellow, Color.green)
		 || checkUnitaryCube(rubiksCubeSCO, 2, 2, 0, Color.yellow, Color.green))
		{
			cornersState = "clockwise";
		}
		// If YGR is at YBR's place
		else if (checkUnitaryCube(rubiksCubeSCO, 3, 0, 0, Color.yellow, Color.green) 
			  || checkUnitaryCube(rubiksCubeSCO, 1, 0, 2, Color.yellow, Color.green)
			  || checkUnitaryCube(rubiksCubeSCO, 2, 0, 2, Color.yellow, Color.green))
		{
			cornersState = "counterclockwise";
		}
		// If YGO is at YGR's place
		else if (checkUnitaryCube(rubiksCubeSCO, 4, 0, 0, Color.yellow, Color.orange) 
			  || checkUnitaryCube(rubiksCubeSCO, 3, 0, 2, Color.yellow, Color.orange)
			  || checkUnitaryCube(rubiksCubeSCO, 2, 0, 0, Color.yellow, Color.orange))
		{
			cornersState = "counterclockwise";
		}
		// If YGO is at YBO's place
		else if (checkUnitaryCube(rubiksCubeSCO, 1, 0, 0, Color.yellow, Color.orange) 
			  || checkUnitaryCube(rubiksCubeSCO, 0, 0, 2, Color.yellow, Color.orange)
			  || checkUnitaryCube(rubiksCubeSCO, 2, 2, 2, Color.yellow, Color.orange))
		{
			cornersState = "clockwise";
		}
		// If YBR is at YGR's place
		else if (checkUnitaryCube(rubiksCubeSCO, 4, 0, 0, Color.yellow, Color.red) 
			  || checkUnitaryCube(rubiksCubeSCO, 3, 0, 2, Color.yellow, Color.red)
			  || checkUnitaryCube(rubiksCubeSCO, 2, 0, 0, Color.yellow, Color.red))
		{
			cornersState = "clockwise";
		}
		// If YBR is at YBO's place
		else if (checkUnitaryCube(rubiksCubeSCO, 1, 0, 0, Color.yellow, Color.red) 
			  || checkUnitaryCube(rubiksCubeSCO, 0, 0, 2, Color.yellow, Color.red)
			  || checkUnitaryCube(rubiksCubeSCO, 2, 2, 2, Color.yellow, Color.red))
		{
			cornersState = "counterclockwise";
		}
		
		return cornersState;
	}*/
	
	int[] getCorrectlyOrientedCorners()
	{
		int[] corner = new int[5];
		
		if (checkUnitaryCube(rubiksCubeSCO, 2, 2, 2, Color.yellow, Color.blue)) corner[1] = 1;
		if (checkUnitaryCube(rubiksCubeSCO, 2, 2, 0, Color.yellow, Color.orange)) corner[2] = 1;
		if (checkUnitaryCube(rubiksCubeSCO, 2, 0, 0, Color.yellow, Color.green)) corner[3] = 1;
		if (checkUnitaryCube(rubiksCubeSCO, 2, 0, 2, Color.yellow, Color.red)) corner[4] = 1;
		
		corner[0] = corner[1] + corner[2] + corner[3] + corner[4];
		
		return corner;
	}
	
	void doSecondCornersOrientation(Solution solution)
	{
		String message = "";
		//String cornersState = "";
		int corner[] = getCorrectlyOrientedCorners();
		int numberOfCorners = corner[0];
	
		int faceToTakeTop = 2; // never changes
		int faceToTakeRight = -1;
		int faceToTakeLeft = -1;
		
		String faceHaut = "";
		String faceDroite = "";
		
		while (numberOfCorners != 4)
		{
			// faceToTakeTop = 2;
			if (rubiksCubeSCO[2][2][2].equals(Color.orange) 
					&& (rubiksCubeSCO[2][2][0].equals(Color.orange) || corner[4] == 1))
			{
				faceToTakeTop = 2;
				faceHaut = "jaune";
				faceToTakeRight = 0;
				faceDroite = "orange";
			}
			else if (rubiksCubeSCO[2][2][0].equals(Color.green) 
					&& (rubiksCubeSCO[2][0][0].equals(Color.green) || corner[1] == 1))
			{
				faceToTakeTop = 2;
				faceHaut = "jaune";
				faceToTakeRight = 4;
				faceDroite = "verte";
			}
			else if (rubiksCubeSCO[2][0][0].equals(Color.red) 
					&& (rubiksCubeSCO[2][0][2].equals(Color.red) || corner[2] == 1))
			{
				faceToTakeTop = 2;
				faceHaut = "jaune";
				faceToTakeRight = 3;
				faceDroite = "rouge";
			}
			else if (rubiksCubeSCO[2][0][2].equals(Color.blue) 
					&& (rubiksCubeSCO[2][2][2].equals(Color.blue) || corner[3] == 1))
			{
				faceToTakeTop = 2;
				faceHaut = "jaune";
				faceToTakeRight = 1;
				faceDroite = "bleue";
			}

			// faceToTakeRight = 2;
			else if (rubiksCubeSCO[2][2][2].equals(Color.blue) 
					&& (rubiksCubeSCO[2][2][0].equals(Color.green) || corner[4] == 1))
			{
				faceToTakeTop = 0;
				faceHaut = "orange";
				faceToTakeRight = 2;
				faceDroite = "jaune";
			}
			else if (rubiksCubeSCO[2][2][0].equals(Color.orange) 
					&& (rubiksCubeSCO[2][0][0].equals(Color.red) || corner[1] == 1))
			{
				faceToTakeTop = 4;
				faceHaut = "verte";
				faceToTakeRight = 2;
				faceDroite = "jaune";
			}
			else if (rubiksCubeSCO[2][0][0].equals(Color.green) 
					&& (rubiksCubeSCO[2][0][2].equals(Color.blue) || corner[2] == 1))
			{
				faceToTakeTop = 3;
				faceHaut = "rouge";
				faceToTakeRight = 2;
				faceDroite = "jaune";
			}
			else if (rubiksCubeSCO[2][0][2].equals(Color.red) 
					&& (rubiksCubeSCO[2][2][2].equals(Color.orange) || corner[3] == 1))
			{
				faceToTakeTop = 1;
				faceHaut = "bleue";
				faceToTakeRight = 2;
				faceDroite = "jaune";
			}
			
			
			/*if (numberOfCorners == 0) // If 1 corner is correct, just swap the 3 others
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
			else if (numberOfCorners == 1) // If 1 corner is correct, just swap the 3 others
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
			else if (numberOfCorners == 2) // If 1 corner is correct, just swap the 3 others
			{
				cornersState = getDirectionOfAlgorithm();
				
				// Find the corner placed
				String cornerPlaced = "";
				if (corner[1] == 1 && corner[2] == 1) 
				{
					if
					faceToTakeTop = 
				}
				else if (corner[2] == 1 && corner[4] == 1)
				{
					
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
			}*/
			
			//if (faceToTakeRight != -1)
			{
			message = "Pour orienter les coins, on utilise un algorithme (qui réutilise les 2 algorithmes pour les coins "
					+ "de la dernière face) qui a "
					+ "pour effet d'orienter le coin en bas à droite de la face du haut dans le sens horaire et le coin "
					+ "en haut à droite de la face du haut dans le sens anti-horaire. Ici on va prendre comme face en haut "
					+ "la face " + faceHaut + " et à droite la face " + faceDroite + " puis appliquer l'algorithme :\n"
					+ "L R' U L' U' R U L U' L'\n"
					+ "Garder la face " + faceHaut + " en haut et prendre la face " + faceDroite + " devant soi\n"
					+ "L R' U' R U L' U' R' U R";	
			
			faceToTakeLeft = (faceToTakeRight+3)%6;
			
			// clockwise
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeLeft, 1, message, "second corners orientation"); // L
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeRight, 3, message, "second corners orientation"); // R'
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeTop, 1, message, "second corners orientation"); // U
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeLeft, 3, message, "second corners orientation"); // L'
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeTop, 3, message, "second corners orientation"); // U'
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeRight, 1, message, "second corners orientation"); // R
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeTop, 1, message, "second corners orientation"); // U
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeLeft, 1, message, "second corners orientation"); // L
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeTop, 3, message, "second corners orientation"); // U'
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeLeft, 3, message, "second corners orientation"); // L'
			
			if 		(faceToTakeRight == 0) faceToTakeRight = 1;
			else if (faceToTakeRight == 1) faceToTakeRight = 3;
			else if (faceToTakeRight == 3) faceToTakeRight = 4;
			else if (faceToTakeRight == 4) faceToTakeRight = 0;
			if (faceToTakeRight == 2) 
			{
				if 		(faceToTakeTop == 0) faceToTakeRight = 4;
				else if (faceToTakeTop == 1) faceToTakeRight = 0;
				else if (faceToTakeTop == 3) faceToTakeRight = 1;
				else if (faceToTakeTop == 4) faceToTakeRight = 3;
			}
			
			faceToTakeLeft = (faceToTakeRight+3)%6;
			
			// counterclockwise
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeLeft, 1, message, "second corners orientation"); // L
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeRight, 3, message, "second corners orientation"); // R'
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeTop, 3, message, "second corners orientation"); // U'
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeRight, 1, message, "second corners orientation"); // R
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeTop, 1, message, "second corners orientation"); // U
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeLeft, 3, message, "second corners orientation"); // L'
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeTop, 3, message, "second corners orientation"); // U'
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeRight, 3, message, "second corners orientation"); // R'
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeTop, 1, message, "second corners orientation"); // U
			fillAnswerTab(solution, rubiksCubeSCO, faceToTakeRight, 1, message, "second corners orientation"); // R					
						
			corner = getCorrectlyOrientedCorners();
			numberOfCorners = corner[0];
			}
		}
	}
}
