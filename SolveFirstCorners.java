
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
	
	void doOneEdge(Color c1, Color c2, Solution firstCross)
	{
		String message = "";
		String bord = "blanc-";
		int[] edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
		if (c2.equals(Color.green))
			bord += "vert ";
		else if (c2.equals(Color.orange))
			bord += "orange ";
		else if (c2.equals(Color.blue))
			bord += "bleu ";
		else if (c2.equals(Color.red))
			bord += "rouge ";
		
		// If the white part of the edge we want is on the white face
		if (edgePos[0] == 2 && edgePos[1] == 1 && edgePos[2] == 2) // the edge is fully placed
		{ }
		else if (edgePos[0] == 2 && edgePos[1] == 2 && edgePos[2] == 1) // the edge is placed
		{
			message = "Le bord " + bord + "est bien positionné, mettons-le à droite "
					+ "pour placer le prochain en bas de la face blanche";
			fillAnswerTab(rubiksCubeFirstCorners, 2, 3, message, "first corners"); // fully placed
		}
		else if (edgePos[0] == 2)
		{
			message = "Le bord " + bord + "est sur la bonne face, mais pas où il faut. Pour ne pas déranger le "
					+ "reste, on va le mettre sur la face opposée, la tourner pour positionner le bord "
					+ "puis le remettre sur la bonne face. Ensuite on le met à droite pour laisser la place "
					+ "au prochain bord.";
			if (edgePos[1] == 1) // he's left of white face
			{
				fillAnswerTab(rubiksCubeFirstCorners, 4, 2, message, "first corners"); // put on opposite face
				fillAnswerTab(rubiksCubeFirstCorners, 5, 1, message, "first corners"); // move opposite face
				fillAnswerTab(rubiksCubeFirstCorners, 0, 2, message, "first corners"); // placed (2, 1, 2)
				fillAnswerTab(rubiksCubeFirstCorners, 2, 3, message, "first corners"); // fully placed (2, 2, 1)
			}
			else if (edgePos[1] == 0) // he's top of white face
			{
				fillAnswerTab(rubiksCubeFirstCorners, 3, 2, message, "first corners"); // put on opposite face
				fillAnswerTab(rubiksCubeFirstCorners, 5, 2, message, "first corners"); // move opposite face
				fillAnswerTab(rubiksCubeFirstCorners, 0, 2, message, "first corners"); // placed
				fillAnswerTab(rubiksCubeFirstCorners, 2, 3, message, "first corners"); // fully placed
			}
		}
		// If the white part of the edge we want is on the yellow face
		else if (edgePos[0] == 5) 
		{
			message = "Le bord " + bord + "est sur la face opposée, on va donc la tourner pour positionner le bord "
					+ "(si besoin) puis le remettre sur la bonne face. Ensuite on le met à droite pour "
					+ "laisser la place au prochain bord.";
			while (edgePos[1] != 0)
			{
				fillAnswerTab(rubiksCubeFirstCorners, 5, 1, message, "first corners"); // move opposite face
				edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
			}
			fillAnswerTab(rubiksCubeFirstCorners, 0, 2, message, "first corners"); // placed
			fillAnswerTab(rubiksCubeFirstCorners, 2, 3, message, "first corners"); // fully placed
		}
		// If the white part of the edge we want is on other faces
		else
		{
			if (edgePos[1] == 1) // On the middle slice (tranche du milieu)
			{
				message = "Le bord " + bord + "est sur une face latérale, on va prendre en face de nous la face sur "
						+ "laquelle se trouve la vignette non blanche du bord que l'on veut placer (toujours avec "
						+ "la face blanche en haut) puis on tourne la face blanche pour la ramener comme on la voyait, "
						+ "et finalement placer le bord. Ensuite on le met à droite pour laisser la place "
						+ "au prochain bord.";
			}
			else // In this case, before algorithm we need to move the face of non-white part of the edge we want
			{
				message = "Le bord " + bord + "est sur une face latérale mais pas sur la tranche du milieu, on va "
						+ "tourner une fois la face sur laquelle se trouve la vignette blanche du bord que l'on "
						+ "veut placer. Ensuite on va prendre en face de nous la face sur "
						+ "laquelle se trouve la vignette non blanche du bord que l'on veut placer (toujours avec "
						+ "la face blanche en haut) puis on tourne la face blanche pour la ramener comme on la voyait, "
						+ "et finalement placer le bord. Ensuite on le met à droite pour laisser la place "
						+ "au prochain bord.";
				
				fillAnswerTab(rubiksCubeFirstCorners, edgePos[0], 1, message, "first corners"); 				
				edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
			}
			
			int timesToTurn = -1;
			
			int faceOfOtherSideOfEdge = otherSideOfUnitaryCube[edgePos[0]][edgePos[1]][edgePos[2]]/100;
			if 	    (faceOfOtherSideOfEdge==0) timesToTurn = 0;
			else if (faceOfOtherSideOfEdge==1) timesToTurn = 3;
			else if (faceOfOtherSideOfEdge==3) timesToTurn = 2;
			else if (faceOfOtherSideOfEdge==4) timesToTurn = 1;
				
			fillAnswerTab(rubiksCubeFirstCorners, 2, timesToTurn, message, "first corners"); // move top face				
			edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
			
			while (edgePos[0] != 2) // place it
			{
				fillAnswerTab(rubiksCubeFirstCorners, faceOfOtherSideOfEdge, 1, message, "first corners");
				edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
			}
			
			while (edgePos[2] != 2) // fully place it
			{
				fillAnswerTab(rubiksCubeFirstCorners, 2, 1, message, "first corners");
				edgePos = findUnitaryEdge(rubiksCubeFirstCorners, c1, c2);
			}
		}
	}
	
	Solution doFirstCross()
	{
		Solution firstCross = new Solution(25); // 25 is max number of moves to solve the cross
		
		// Start with blue so that it doesn't scramble a solved first cross
		doOneEdge(Color.white, Color.blue, firstCross); 
		doOneEdge(Color.white, Color.red, firstCross);
		doOneEdge(Color.white, Color.green, firstCross);
		doOneEdge(Color.white, Color.orange, firstCross);

		String message = "On tourne la face blanche pour placer les bords avec leur centre.";
		// Below, "firstCross.turn, 3" only works if last edge placed is white-orange
		fillAnswerTab(rubiksCubeFirstCorners, 2, 3, message, "first corners");
		
		return firstCross;
	}
}
