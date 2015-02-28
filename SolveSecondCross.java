
import java.awt.Color;

public class SolveSecondCross extends Model {

	Color[][][] rubiksCubeSecondCross = new Color[6][3][3];
			
	public SolveSecondCross(Color[][][] rubiksCubeSecondLayer) 
	{
		// Orange is front face and yellow is top face
		rubiksCubeSecondCross = rubiksCubeSecondLayer;
		InitializeOtherSideTab();
	}
	
	void doSecondCross(Solution solution)
	{
		String message = "";
		String crossState = "";
	
		int faceToTakeTop = 2; // never changes
		int faceToTakeFront = -1;
		int faceToTakeRight = -1;
		
		while (!crossState.equals("finished"))
		{
			// First, let's check the state of the cross
			int numberOfYellowEdges = 0;
			if (rubiksCubeSecondCross[2][0][1].equals(Color.yellow)) numberOfYellowEdges++;
			if (rubiksCubeSecondCross[2][1][0].equals(Color.yellow)) numberOfYellowEdges++;
			if (rubiksCubeSecondCross[2][1][2].equals(Color.yellow)) numberOfYellowEdges++;
			if (rubiksCubeSecondCross[2][2][1].equals(Color.yellow)) numberOfYellowEdges++;
			
			if (numberOfYellowEdges == 0) 		// It can be either no yellow edge on yellow face
				crossState = "empty";
			else if (numberOfYellowEdges == 2)
			{
				if (rubiksCubeSecondCross[2][0][1].equals(rubiksCubeSecondCross[2][2][1]))
					crossState = "line";		// Or 2 yellow edges forming a line
				else
					crossState = "L-shape";  	// Or 2 yellow edges forming a "L"
			}
			else if (numberOfYellowEdges == 4)	// Or already done
				crossState = "finished";
				
			// Then deal with it accordingly
			if (crossState == "finished") 
			{ }
			else if (crossState == "empty") // Keep yellow top face and do algorithm
			{
				message = "La croix n'est pas du tout faite, donc on peut appliquer l'algorithme à partir de "
						+ "n'importe quelle position, tant que la face jaune est en haut (ce solveur prend la "
						+ "face orange devant). L'algorithme de la croix est :\n"
						+ "R' U' F' U F R";
	
				faceToTakeFront = 0;
				faceToTakeRight = 1;
				
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeRight, 3, message, "second cross"); // R'
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeTop, 3, message, "second cross");   // U'
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeFront, 3, message, "second cross"); // F'
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeTop, 1, message, "second cross");   // U
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeFront, 1, message, "second cross"); // F
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeRight, 1, message, "second cross"); // R
			}
			else if (crossState == "line") // Put the line vertically (pointing towards you) and do the algorithm
			{ 
				message = "Sur la face jaune on peut voir une ligne jaune, donc on peut appliquer l'algorithme "
						+ "en positionnant la ligne verticalement (donc vers nous).";
	
				if (rubiksCubeSecondCross[2][0][1].equals(Color.yellow)) // Orange as front face
				{					
					message += "\nCe solveur prend la face orange devant. L'algorithme de la croix est :\n"
							+ "R' U' F' U F R";					
					faceToTakeFront = 0;
					faceToTakeRight = 1;
				}
				else // Blue as front face
				{
					message += "\nCe solveur prend la face bleue devant. L'algorithme de la croix est :\n"
							+ "R' U' F' U F R";					
					faceToTakeFront = 1;
					faceToTakeRight = 3;
				}

				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeRight, 3, message, "second cross"); // R'
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeTop, 3, message, "second cross");   // U'
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeFront, 3, message, "second cross"); // F'
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeTop, 1, message, "second cross");   // U
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeFront, 1, message, "second cross"); // F
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeRight, 1, message, "second cross"); // R
			}
			else if (crossState == "L-shape") // Put the edges on left and top of yellow face (away from us)
			{
				message = "Sur la face jaune on peut voir 2 bords jaunes à côté (qui peuvent former un L "
						+ "avec la bonne orientation), donc il faut placer les 2 bords à gauche et en haut "
						+ "(éloigné de nous), puis on peut appliquer l'algorithme.";
	
				if (rubiksCubeSecondCross[2][0][1].equals(Color.yellow))
				{
					if (rubiksCubeSecondCross[2][1][0].equals(Color.yellow)) // Orange as front face
					{
						message += "\nOn prend donc la face orange devant. L'algorithme de la croix est :\n"
								+ "R' U' F' U F R";
						faceToTakeFront = 0;
						faceToTakeRight = 1;
					}
					else // Green as front face
					{
						message += "\nOn prend donc la face verte devant. L'algorithme de la croix est :\n"
								+ "R' U' F' U F R";
						faceToTakeFront = 4;
						faceToTakeRight = 0;
					}
				}
				else 
				{
					if (rubiksCubeSecondCross[2][1][0].equals(Color.yellow)) // Blue as front face
					{
						message += "\nOn prend donc la face bleue devant. L'algorithme de la croix est :\n"
								+ "R' U' F' U F R";
						faceToTakeFront = 1;
						faceToTakeRight = 3;
					}
					else // Red as front face
					{
						message += "\nOn prend donc la face rouge devant. L'algorithme de la croix est :\n"
								+ "R' U' F' U F R";
						faceToTakeFront = 3;
						faceToTakeRight = 4;
					}
				}

				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeRight, 3, message, "second cross"); // R'
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeTop, 3, message, "second cross");   // U'
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeFront, 3, message, "second cross"); // F'
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeTop, 1, message, "second cross");   // U
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeFront, 1, message, "second cross"); // F
				fillAnswerTab(solution, rubiksCubeSecondCross, faceToTakeRight, 1, message, "second cross"); // R
			}
		}
	}
}
