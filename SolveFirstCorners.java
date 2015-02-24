
import java.awt.Color;

public class SolveFirstCorners extends Model {

	Color[][][] rubiksCubeFirstCorners = new Color[6][3][3];
			
	public SolveFirstCorners(Color[][][] rubiksCubeFirstCross) 
	{
		InitializeGrid(rubiksCubeFirstCross);
		InitializeOtherSideTab();
	}
	
	void InitializeGrid(Color[][][] rubiksCubeFirstCross)
	{
		// Red was front face and white top face, now orange is front and yellow is top
		// Swap faces
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				rubiksCubeFirstCorners[0][i][j] = rubiksCubeFirstCross[3][i][j];				
				rubiksCubeFirstCorners[1][i][j] = rubiksCubeFirstCross[1][i][j];
				rubiksCubeFirstCorners[2][i][j] = rubiksCubeFirstCross[5][i][j];
				rubiksCubeFirstCorners[3][i][j] = rubiksCubeFirstCross[0][i][j];				
				rubiksCubeFirstCorners[4][i][j] = rubiksCubeFirstCross[4][i][j];
				rubiksCubeFirstCorners[5][i][j] = rubiksCubeFirstCross[2][i][j];
			}
		}
		
		// Orient faces
		rubiksCubeFirstCorners[0] = turnFace(rubiksCubeFirstCorners[0]);
		rubiksCubeFirstCorners[0] = turnFace(rubiksCubeFirstCorners[0]);
		rubiksCubeFirstCorners[1] = turnFace(rubiksCubeFirstCorners[1]);	
		rubiksCubeFirstCorners[1] = turnFace(rubiksCubeFirstCorners[1]);		
		rubiksCubeFirstCorners[3] = turnFace(rubiksCubeFirstCorners[3]);
		rubiksCubeFirstCorners[3] = turnFace(rubiksCubeFirstCorners[3]);
		rubiksCubeFirstCorners[4] = turnFace(rubiksCubeFirstCorners[4]);
		rubiksCubeFirstCorners[4] = turnFace(rubiksCubeFirstCorners[4]);	
	}
	
	void doOneCorner(Solution solution, Color c1, Color c2)
	{
		String message = "";
		String coin = "blanc-";
		int[] cornerPos = findUnitaryCorner(rubiksCubeFirstCorners, c1, c2);
		if (c2.equals(Color.green))
			coin += "vert-";
		else if (c2.equals(Color.orange))
			coin += "orange-";
		else if (c2.equals(Color.blue))
			coin += "bleu-";
		else if (c2.equals(Color.red))
			coin += "rouge-";
		
		int cornerPosC2 = otherSideOfUnitaryCube[cornerPos[0]][cornerPos[1]][cornerPos[2]];
		Color c3 = getOtherSideOfUnitaryCube(rubiksCubeFirstCorners, cornerPosC2/100, (cornerPosC2/10)%10, cornerPosC2%10);
		if (c3.equals(Color.green))
			coin += "vert";
		else if (c3.equals(Color.orange))
			coin += "orange";
		else if (c3.equals(Color.blue))
			coin += "bleu";
		else if (c3.equals(Color.red))
			coin += "rouge";
		
		// The corner is already placed
		if (cornerPos[0] == 5 && cornerPos[1] == 0 && cornerPos[2] == 2) // the corner is placed
		{
			message = "Le coin " + coin + " est bien positionné, tournons la face du bas vers la droite "
					+ "pour placer le prochain coin";
			fillAnswerTab(solution, rubiksCubeFirstCorners, 5, 1, message, "first corners"); // fully placed
		}
		else // Need to place it
		{
			// If the corner isn't on a side face, top side, place it there	
			
			if (cornerPos[0] == 2 || cornerPos[0] == 5 || cornerPos[1] != 0) 
			{
				if (cornerPos[0] == 2) // Corner is on top face
				{
					message = "Le coin " + coin + " est sur la face d'en haut, on va commencer par le placer "
							+ "sur une face latérale, couronne du haut. Pour cela, on le place au-dessus "
							+ "de l'endroit où on le placera, on déplace le bord associé, on sort le coin de "
							+ "la face tournée et on replace le bord.";
					while (cornerPos[1] != 2 && cornerPos[2] != 2)
					{
						fillAnswerTab(solution, rubiksCubeFirstCorners, 2, 1, message, "first corners"); // put corner on bottom-left
						cornerPos = findUnitaryCorner(rubiksCubeFirstCorners, c1, c2);
					}
					fillAnswerTab(solution, rubiksCubeFirstCorners, 1, 1, message, "first corners"); // Unplace it in 3 moves
					fillAnswerTab(solution, rubiksCubeFirstCorners, 2, 2, message, "first corners");
					fillAnswerTab(solution, rubiksCubeFirstCorners, 1, 3, message, "first corners");
				}
				else if (cornerPos[0] == 5) // Corner is on bottom face
				{
					message = "Le coin " + coin + " est sur la face d'en bas, on va commencer par le placer "
							+ "sur une face latérale, couronne du haut. Pour cela, on déplace le bord associé, "
							+ "on sort le coin de la face tournée et on replace le bord.";
					
					int faceOfOtherSideOfCorner = otherSideOfUnitaryCube[cornerPos[0]][cornerPos[1]][cornerPos[2]]/100;
					
					fillAnswerTab(solution, rubiksCubeFirstCorners, faceOfOtherSideOfCorner, 3, message, "first corners"); // Unplace it in 3 moves
					fillAnswerTab(solution, rubiksCubeFirstCorners, 2, 2, message, "first corners");
					fillAnswerTab(solution, rubiksCubeFirstCorners, faceOfOtherSideOfCorner, 1, message, "first corners");
				}
				else // Corner is on a side face
				{
					message = "Le coin " + coin + " est sur la face latérale mais sur la 1e couronne, on va "
							+ "commencer par le placer sur une face latérale, couronne du haut, sans casser la croix. "
							+ "Pour cela, "
							+ "on déplace le bord associé, on sort le coin de la face tournée et on replace le bord.";
					
					fillAnswerTab(solution, rubiksCubeFirstCorners, cornerPos[0], 1, message, "first corners"); // Unplace it in 3 moves
					if (cornerPos[2] == 2)
						fillAnswerTab(solution, rubiksCubeFirstCorners, cornerPos[0], 2, message, "first corners");
					fillAnswerTab(solution, rubiksCubeFirstCorners, 2, 2, message, "first corners");
					fillAnswerTab(solution, rubiksCubeFirstCorners, cornerPos[0], 1, message, "first corners");
					if (cornerPos[2] == 0)
						fillAnswerTab(solution, rubiksCubeFirstCorners, cornerPos[0], 2, message, "first corners");
				}
			}
			
			cornerPos = findUnitaryCorner(rubiksCubeFirstCorners, c1, c2);
			
			// The corner on a side face, top side	
			
			message = "Le coin " + coin + " est prêt à être placé, on va le mettre au-dessus de l'endroit "
					+ "où il faut l'insérer, puis sortir le bord associé, ramener le coin, et replacer le tout. "
					+ "Ensuite on tourne la face du bas vers la droite pour placer le prochain coin.";
			if (cornerPos[2] == 0) // on the top-left of a side face
			{
				while (cornerPos[0] != 1)
				{
					fillAnswerTab(solution, rubiksCubeFirstCorners, 2, 1, message, "first corners"); // put corner on right face
					cornerPos = findUnitaryCorner(rubiksCubeFirstCorners, c1, c2);
				}
				fillAnswerTab(solution, rubiksCubeFirstCorners, 1, 1, message, "first corners"); // Place it in 3 moves
				fillAnswerTab(solution, rubiksCubeFirstCorners, 2, 1, message, "first corners");
				fillAnswerTab(solution, rubiksCubeFirstCorners, 1, 3, message, "first corners");
			}
			else if (cornerPos[2] == 2) // on the top-right of a side face
			{
				while (cornerPos[0] != 0)
				{
					fillAnswerTab(solution, rubiksCubeFirstCorners, 2, 1, message, "first corners"); // put corner on right face
					cornerPos = findUnitaryCorner(rubiksCubeFirstCorners, c1, c2);
				}
				fillAnswerTab(solution, rubiksCubeFirstCorners, 0, 3, message, "first corners"); // Place it in 3 moves
				fillAnswerTab(solution, rubiksCubeFirstCorners, 2, 3, message, "first corners");
				fillAnswerTab(solution, rubiksCubeFirstCorners, 0, 1, message, "first corners");
			}
			fillAnswerTab(solution, rubiksCubeFirstCorners, 5, 1, message, "first corners"); // fully placed			
		}
	}
	
	void doFirstCorners(Solution solution)
	{
		// Start with orange so that it doesn't scramble a solved first corners
		doOneCorner(solution, Color.white, Color.orange);
		doOneCorner(solution, Color.white, Color.green);
		doOneCorner(solution, Color.white, Color.red);
		doOneCorner(solution, Color.white, Color.blue); 
	}
}
