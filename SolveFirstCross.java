
import java.awt.Color;

public class SolveFirstCross extends Model {

	Color[][][] rubiksCubeFirstCross = new Color[6][3][3];
			
	public SolveFirstCross(Color[][][] rubiksCube) 
	{
		InitializeGrid(rubiksCube);
		InitializeOtherSideTab();
	}
	
	void InitializeGrid(Color[][][] rubiksCube)
	{
		// White was front face and orange top face, now red is front and white is top
		// Swap faces
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				rubiksCubeFirstCross[0][i][j] = rubiksCube[5][i][j];				
				rubiksCubeFirstCross[1][i][j] = rubiksCube[1][i][j];
				rubiksCubeFirstCross[2][i][j] = rubiksCube[0][i][j];
				rubiksCubeFirstCross[3][i][j] = rubiksCube[2][i][j];				
				rubiksCubeFirstCross[4][i][j] = rubiksCube[4][i][j];
				rubiksCubeFirstCross[5][i][j] = rubiksCube[3][i][j];
			}
		}
		
		// Orient faces
		rubiksCubeFirstCross[1] = turnFace(rubiksCubeFirstCross[1]);		
		rubiksCubeFirstCross[3] = turnFace(rubiksCubeFirstCross[3]);
		rubiksCubeFirstCross[3] = turnFace(rubiksCubeFirstCross[3]);
		rubiksCubeFirstCross[4] = turnFace(rubiksCubeFirstCross[4]);
		rubiksCubeFirstCross[4] = turnFace(rubiksCubeFirstCross[4]);
		rubiksCubeFirstCross[4] = turnFace(rubiksCubeFirstCross[4]);
		rubiksCubeFirstCross[5] = turnFace(rubiksCubeFirstCross[5]);
		rubiksCubeFirstCross[5] = turnFace(rubiksCubeFirstCross[5]);		
	}
	
	void doOneEdge(Color c1, Color c2)
	{
		String message = "";
		String bord = "blanc-";
		int[] edgePos = findUnitaryEdge(rubiksCubeFirstCross, c1, c2);
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
			fillAnswerTab(rubiksCubeFirstCross, 2, 3, message, "first cross"); // fully placed
		}
		else if (edgePos[0] == 2)
		{
			message = "Le bord " + bord + "est sur la bonne face, mais pas où il faut. Pour ne pas déranger le "
					+ "reste, on va le mettre sur la face opposée, la tourner pour positionner le bord "
					+ "puis le remettre sur la bonne face. Ensuite on le met à droite pour laisser la place "
					+ "au prochain bord.";
			if (edgePos[1] == 1) // he's left of white face
			{
				fillAnswerTab(rubiksCubeFirstCross, 4, 2, message, "first cross"); // put on opposite face
				fillAnswerTab(rubiksCubeFirstCross, 5, 1, message, "first cross"); // move opposite face
				fillAnswerTab(rubiksCubeFirstCross, 0, 2, message, "first cross"); // placed (2, 1, 2)
				fillAnswerTab(rubiksCubeFirstCross, 2, 3, message, "first cross"); // fully placed (2, 2, 1)
			}
			else if (edgePos[1] == 0) // he's top of white face
			{
				fillAnswerTab(rubiksCubeFirstCross, 3, 2, message, "first cross"); // put on opposite face
				fillAnswerTab(rubiksCubeFirstCross, 5, 2, message, "first cross"); // move opposite face
				fillAnswerTab(rubiksCubeFirstCross, 0, 2, message, "first cross"); // placed
				fillAnswerTab(rubiksCubeFirstCross, 2, 3, message, "first cross"); // fully placed
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
				fillAnswerTab(rubiksCubeFirstCross, 5, 1, message, "first cross"); // move opposite face
				edgePos = findUnitaryEdge(rubiksCubeFirstCross, c1, c2);
			}
			fillAnswerTab(rubiksCubeFirstCross, 0, 2, message, "first cross"); // placed
			fillAnswerTab(rubiksCubeFirstCross, 2, 3, message, "first cross"); // fully placed
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
				
				fillAnswerTab(rubiksCubeFirstCross, edgePos[0], 1, message, "first cross"); 				
				edgePos = findUnitaryEdge(rubiksCubeFirstCross, c1, c2);
			}
			
			int timesToTurn = -1;
			
			int faceOfOtherSideOfEdge = otherSideOfUnitaryCube[edgePos[0]][edgePos[1]][edgePos[2]]/100;
			if 	    (faceOfOtherSideOfEdge==0) timesToTurn = 0;
			else if (faceOfOtherSideOfEdge==1) timesToTurn = 3;
			else if (faceOfOtherSideOfEdge==3) timesToTurn = 2;
			else if (faceOfOtherSideOfEdge==4) timesToTurn = 1;
				
			fillAnswerTab(rubiksCubeFirstCross, 2, timesToTurn, message, "first cross"); // move top face				
			edgePos = findUnitaryEdge(rubiksCubeFirstCross, c1, c2);
			
			while (edgePos[0] != 2) // place it
			{
				fillAnswerTab(rubiksCubeFirstCross, faceOfOtherSideOfEdge, 1, message, "first cross");
				edgePos = findUnitaryEdge(rubiksCubeFirstCross, c1, c2);
			}
			
			while (edgePos[2] != 2) // fully place it
			{
				fillAnswerTab(rubiksCubeFirstCross, 2, 1, message, "first cross");
				edgePos = findUnitaryEdge(rubiksCubeFirstCross, c1, c2);
			}
		}
	}
	
	Solution doFirstCross()
	{
		Solution firstCross = new Solution(25); // 25 is max number of moves to solve the cross
		
		// Start with blue so that it doesn't scramble a solved first cross
		doOneEdge(Color.white, Color.blue); 
		doOneEdge(Color.white, Color.red);
		doOneEdge(Color.white, Color.green);
		doOneEdge(Color.white, Color.orange);

		String message = "On tourne la face blanche pour placer les bords avec leur centre.";
		// Below, "firstCross.turn, 3" only works if last edge placed is white-orange
		fillAnswerTab(rubiksCubeFirstCross, 2, 3, message, "first cross");
		
		return firstCross;
	}
}
