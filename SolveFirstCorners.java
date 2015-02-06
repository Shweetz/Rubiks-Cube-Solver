import java.awt.Color;


public class SolveFirstCorners extends Model {

	Color[][][] rubiksCubeFirstCorners = new Color[6][3][3];
	int[][][] otherSideOfUnitaryCube = new int[6][3][3];
	
	boolean isSolvable = true;
	SubSolution solutionFirstCross;
			
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
	
	void doOneEdge(Color c1, Color c2, SubSolution solution)
	{
		String message = "";
		String bord = "blanc-";
		int[] edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
		if (c2.equals(Color.green))
			bord += "vert";
		else if (c2.equals(Color.orange))
			bord += "orange";
		else if (c2.equals(Color.blue))
			bord += "bleu";
		else if (c2.equals(Color.red))
			bord += "rouge";
		
		// If the white part of the edge we want is on the white face
		if (edgePos[0] == 2 && edgePos[1] == 1 && edgePos[2] == 2) // the edge is fully placed
		{ }
		else if (edgePos[0] == 2 && edgePos[1] == 2 && edgePos[2] == 1) // the edge is placed
		{
			message = "Le bord " + bord + "est bien positionné, mettons-le à droite "
					+ "pour placer le prochain en bas de la face blanche";
			fillAnswerTab(rubiksCubeFirstCorners, solution.move, 2, solution.turn, 3, solution.message, message); // fully placed
		}
		else if (edgePos[0] == 2)
		{
			message = "Le bord " + bord + "est sur la bonne face, mais pas où il faut. Pour ne pas déranger le "
					+ "reste, on va le mettre sur la face opposée, la tourner pour positionner le bord "
					+ "puis le remettre sur la bonne face. Ensuite on le met à droite pour laisser la place "
					+ "au prochain bord.";
			if (edgePos[1] == 1) // he's left of white face
			{
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 4, solution.turn, 2, solution.message, message);
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 5, solution.turn, 1, solution.message, message);
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 0, solution.turn, 2, solution.message, message); // placed (2, 1, 2)
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 2, solution.turn, 3, solution.message, message); // fully placed (2, 2, 1)
			}
			else if (edgePos[1] == 0) // he's top of white face
			{
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 3, solution.turn, 2, solution.message, message);
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 5, solution.turn, 2, solution.message, message);
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 0, solution.turn, 2, solution.message, message); // placed
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 2, solution.turn, 3, solution.message, message); // fully placed
			}
		}
		// If the white part of the edge we want is on the yellow face
		else if (edgePos[0] == 5) 
		{
			message = "Le bord " + bord + "est sur la face opposée, on va donc la tourner pour positionner le bord "
					+ "puis le remettre sur la bonne face. Ensuite on le met à droite pour laisser la place "
					+ "au prochain bord.";
			while (edgePos[1] != 0)
			{
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 5, solution.turn, 1, solution.message, message);
				edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
			}
			fillAnswerTab(rubiksCubeFirstCorners, solution.move, 0, solution.turn, 2, solution.message, message); // placed
			fillAnswerTab(rubiksCubeFirstCorners, solution.move, 2, solution.turn, 3, solution.message, message); // fully placed
		}
		// If the white part of the edge we want is on other faces
		else
		{
			if (edgePos[1] == 1) 
			{
				message = "Le bord " + bord + "est sur une face latérale, on va prendre en face de nous la face sur "
						+ "laquelle se trouve la vignette non blanche du bord que l'on veut placer (toujours avec "
						+ "la face blanche en haut) puis on tourne la face blanche pour la ramener comme on la voyait,"
						+ "et finalement placer le bord. Ensuite on le met à droite pour laisser la place "
						+ "au prochain bord.";
				
				int timesToTurn = -1;
				
				int faceOfOtherSideOfEdge = otherSideOfUnitaryCube[edgePos[0]][edgePos[1]][edgePos[2]]/100;
				if 	    (faceOfOtherSideOfEdge==0) timesToTurn = 0;
				else if (faceOfOtherSideOfEdge==1) timesToTurn = 3;
				else if (faceOfOtherSideOfEdge==3) timesToTurn = 2;
				else if (faceOfOtherSideOfEdge==4) timesToTurn = 1;
					
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 2, solution.turn, timesToTurn, solution.message, message); 				
				edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
				
				while (edgePos[0] != 2)
				{
					fillAnswerTab(rubiksCubeFirstCorners, solution.move, faceOfOtherSideOfEdge, solution.turn, 1, solution.message, message);
					edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
				}
			}
			else
			{
				message = "Le bord " + bord + "est sur une face latérale mais pas sur la tranche du milieu, on va "
						+ "tourner une fois la face sur laquelle se trouve la vignette blanche du bord que l'on "
						+ "veut placer. Ensuite on va prendre en face de nous la face sur "
						+ "laquelle se trouve la vignette non blanche du bord que l'on veut placer (toujours avec "
						+ "la face blanche en haut) puis on tourne la face blanche pour la ramener comme on la voyait,"
						+ "et finalement placer le bord. Ensuite on le met à droite pour laisser la place "
						+ "au prochain bord.";
				
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, edgePos[0], solution.turn, 1, solution.message, message); 				
				edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
				
				// Comme dans le if
				int timesToTurn = -1;
				
				int faceOfOtherSideOfEdge = otherSideOfUnitaryCube[edgePos[0]][edgePos[1]][edgePos[2]]/100;
				if 	    (faceOfOtherSideOfEdge==0) timesToTurn = 0;
				else if (faceOfOtherSideOfEdge==1) timesToTurn = 3;
				else if (faceOfOtherSideOfEdge==3) timesToTurn = 2;
				else if (faceOfOtherSideOfEdge==4) timesToTurn = 1;
					
				fillAnswerTab(rubiksCubeFirstCorners, solution.move, 2, solution.turn, timesToTurn, solution.message, message); 				
				edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
				
				while (edgePos[0] != 2)
				{
					fillAnswerTab(rubiksCubeFirstCorners, solution.move, faceOfOtherSideOfEdge, solution.turn, 1, solution.message, message);
					edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
				}
			}
			fillAnswerTab(rubiksCubeFirstCorners, solution.move, 2, solution.turn, 3, solution.message, message); // fully placed
		}
	}
	
	void doFirstCorners()
	{
		SubSolution solution = new SubSolution(25); // 25 is max number of moves to solve the cross
		
		doOneEdge(Color.white, Color.green, solution);
		doOneEdge(Color.white, Color.orange, solution);
		doOneEdge(Color.white, Color.blue, solution);
		doOneEdge(Color.white, Color.red, solution);

		String message = "On tourne la face blanche pour placer les bords avec leur centre.";
		int[] edgePos = findUnitaryEdge(rubiksCubeFirstCorners, Color.white, Color.red);
		while (edgePos[1] != 2)
		{
			fillAnswerTab(rubiksCubeFirstCorners, solution.move, 2, solution.turn, 1, solution.message, message);
			edgePos = findUnitaryEdge(rubiksCubeFirstCorners, Color.white, Color.red);
		}
		
		solutionFirstCross = solution;
	}
}
