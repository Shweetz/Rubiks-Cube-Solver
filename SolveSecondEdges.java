
import java.awt.Color;

public class SolveSecondEdges extends Model {

	Color[][][] rubiksCubeSecondEdges = new Color[6][3][3];
			
	public SolveSecondEdges(Color[][][] rubiksCubeSecondCross) 
	{
		// Orange is front face and yellow is top face
		rubiksCubeSecondEdges = rubiksCubeSecondCross;
		InitializeOtherSideTab();
	}
	
	int countCorrectEdges()
	{
		int numberOfEdges = 0;
		if (rubiksCubeSecondEdges[0][0][1].equals(Color.orange)) numberOfEdges++;
		if (rubiksCubeSecondEdges[1][0][1].equals(Color.blue)) numberOfEdges++;
		if (rubiksCubeSecondEdges[3][0][1].equals(Color.red)) numberOfEdges++;
		if (rubiksCubeSecondEdges[4][0][1].equals(Color.green)) numberOfEdges++;
		
		return numberOfEdges;
	}
	
	void doSecondEdges(Solution solution)
	{
		String message = "";
		String edgesState = "";
		int numberOfEdges = 0;
	
		int faceToTakeTop = 2; // never changes
		int faceToTakeRight = -1;
		
		while (!edgesState.equals("finished"))
		{
			numberOfEdges = countCorrectEdges();
			
			while (numberOfEdges < 2)
			{
				message = "On tourne la face du haut jusqu'à ce qu'au moins 2 bords soient sur leur face associée.";
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeTop, 1, message, "second edges");
				
				numberOfEdges = countCorrectEdges();
			}			
			
			if (numberOfEdges == 4) // All other edges are wrong
			{
				edgesState = "finished";
			}
			else if (numberOfEdges == 2) // If not 4
			{
				// If the 2 good edges are opposed (so they are lined up with the center of yellow face)
				if ((rubiksCubeSecondEdges[0][0][1].equals(Color.orange) && rubiksCubeSecondEdges[3][0][1].equals(Color.red))
					|| (rubiksCubeSecondEdges[1][0][1].equals(Color.blue) && rubiksCubeSecondEdges[4][0][1].equals(Color.green)))
					edgesState = "2 and 2";
				else
					edgesState = "2 and 1 and 1";
			}
				
			// Then deal with it accordingly
			if (edgesState == "finished") 
			{ }
			else if (edgesState == "2 and 1 and 1") // You can turn top so as only one edge matches its face
			{
				String bord = ""; // On récupère le bord qui va plus tard être bien placé
				if (rubiksCubeSecondEdges[4][0][1].equals(Color.orange)) bord = "orange";
				if (rubiksCubeSecondEdges[0][0][1].equals(Color.blue)) 	 bord = "bleu";
				if (rubiksCubeSecondEdges[1][0][1].equals(Color.red)) 	 bord = "rouge";
				if (rubiksCubeSecondEdges[3][0][1].equals(Color.green))  bord = "vert";
				
				message = "On peut tourner la face du haut jusqu'à avoir un seul bord correct, le " + bord + ", "
						+ "et les 3 autres de telle manière que il faille les échanger dans le sens horaire. "
						+ "Ensuite il n'y a plus qu'à prendre la face avec le bord correcte à gauche et "
						+ "appliquer l'algorithme des bords de la dernière face :\n"
						+ "R U R' U R U2 R' U2";
				
				// Turn top counter-clockwise once so that other 3 need to be swapped clockwise
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeTop, 3, message, "second edges");
				
				// Put maching edge on the left and do algorithm
				if 		(bord.equals("orange"))	faceToTakeRight = 3;
				else if (bord.equals("bleu"))	faceToTakeRight = 4;	
				else if (bord.equals("rouge"))	faceToTakeRight = 0;
				else if (bord.equals("vert"))	faceToTakeRight = 1;					
				
				
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeRight, 1, message, "second edges"); // R
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeTop, 1, message, "second edges"); // U
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeRight, 3, message, "second edges"); // R'
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeTop, 1, message, "second edges"); // U
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeRight, 1, message, "second edges"); // R
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeTop, 2, message, "second edges"); // U2
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeRight, 3, message, "second edges"); // R'
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeTop, 2, message, "second edges"); // U2
			}
			else if (edgesState == "2 and 2") // You can only turn top so as only 0 or 2 edges are placed
			{ 
				message = "En tournant la face du haut, on peut seulement avoir 0 ou 2 bords corrects "
						+ "à la fois, donc il faut faire l'algorithme des bords de la dernière face. "
						+ "Où qu'on le fasse cela marchera, on va ici le faire avec la face orange devant "
						+ "(et toujours jaune en haut) :\n"
						+ "R U R' U R U2 R' U2";
				
				faceToTakeRight = 1; // Orange in front, right face is blue
				
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeRight, 1, message, "second edges"); // R
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeTop, 1, message, "second edges"); // U
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeRight, 3, message, "second edges"); // R'
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeTop, 1, message, "second edges"); // U
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeRight, 1, message, "second edges"); // R
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeTop, 2, message, "second edges"); // U2
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeRight, 3, message, "second edges"); // R'
				fillAnswerTab(solution, rubiksCubeSecondEdges, faceToTakeTop, 2, message, "second edges"); // U2
			}
		}
	}
}
