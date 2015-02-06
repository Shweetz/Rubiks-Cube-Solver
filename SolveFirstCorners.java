import java.awt.Color;


public class SolveFirstCorners extends Model {

	Color[][][] rubiksCubeSolve1 = new Color[6][3][3];
	int[][][] otherSideOfUnitaryCube = new int[6][3][3];
	
	String str_ihm_out = "SolveFirstCross instancie";	
	boolean isSolvable = true;
	SubSolution solutionFirstCross;
			
	public SolveFirstCorners(Color[][][] rubiksCube) 
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
				rubiksCubeSolve1[0][i][j] = rubiksCube[5][i][j];				
				rubiksCubeSolve1[1][i][j] = rubiksCube[1][i][j];
				rubiksCubeSolve1[2][i][j] = rubiksCube[0][i][j];
				rubiksCubeSolve1[3][i][j] = rubiksCube[2][i][j];				
				rubiksCubeSolve1[4][i][j] = rubiksCube[4][i][j];
				rubiksCubeSolve1[5][i][j] = rubiksCube[3][i][j];
			}
		}
		
		// Orient faces
		rubiksCubeSolve1[1] = turnFace(rubiksCubeSolve1[1]);		
		rubiksCubeSolve1[3] = turnFace(rubiksCubeSolve1[3]);
		rubiksCubeSolve1[3] = turnFace(rubiksCubeSolve1[3]);
		rubiksCubeSolve1[4] = turnFace(rubiksCubeSolve1[4]);
		rubiksCubeSolve1[4] = turnFace(rubiksCubeSolve1[4]);
		rubiksCubeSolve1[4] = turnFace(rubiksCubeSolve1[4]);
		rubiksCubeSolve1[5] = turnFace(rubiksCubeSolve1[5]);
		rubiksCubeSolve1[5] = turnFace(rubiksCubeSolve1[5]);		
	}
	
	void InitializeOtherSideTab() // For corners, other side is the next side clockwise
	{
		otherSideOfUnitaryCube[0][0][0] = 402; // 402 means [4][0][2]
		otherSideOfUnitaryCube[0][0][1] = 221;
		otherSideOfUnitaryCube[0][0][2] = 222;
		otherSideOfUnitaryCube[0][1][0] = 412;
		otherSideOfUnitaryCube[0][1][2] = 110;
		otherSideOfUnitaryCube[0][2][0] = 500;
		otherSideOfUnitaryCube[0][2][1] = 501;
		otherSideOfUnitaryCube[0][2][2] = 120;

		otherSideOfUnitaryCube[1][0][0] = 2;   // 002
		otherSideOfUnitaryCube[1][0][1] = 212;
		otherSideOfUnitaryCube[1][0][2] = 202;
		otherSideOfUnitaryCube[1][1][0] = 12;  // 012
		otherSideOfUnitaryCube[1][1][2] = 310;
		otherSideOfUnitaryCube[1][2][0] = 502;
		otherSideOfUnitaryCube[1][2][1] = 512;
		otherSideOfUnitaryCube[1][2][2] = 320;
		
		otherSideOfUnitaryCube[2][0][0] = 400; 
		otherSideOfUnitaryCube[2][0][1] = 301;
		otherSideOfUnitaryCube[2][0][2] = 300;
		otherSideOfUnitaryCube[2][1][0] = 401;
		otherSideOfUnitaryCube[2][1][2] = 101;
		otherSideOfUnitaryCube[2][2][0] = 0;   // 000
		otherSideOfUnitaryCube[2][2][1] = 1;   // 001
		otherSideOfUnitaryCube[2][2][2] = 100;
		
		otherSideOfUnitaryCube[3][0][0] = 102; 
		otherSideOfUnitaryCube[3][0][1] = 201;
		otherSideOfUnitaryCube[3][0][2] = 200;
		otherSideOfUnitaryCube[3][1][0] = 112;
		otherSideOfUnitaryCube[3][1][2] = 410;
		otherSideOfUnitaryCube[3][2][0] = 522;
		otherSideOfUnitaryCube[3][2][1] = 521;
		otherSideOfUnitaryCube[3][2][2] = 420;
		
		otherSideOfUnitaryCube[4][0][0] = 302; 
		otherSideOfUnitaryCube[4][0][1] = 210;
		otherSideOfUnitaryCube[4][0][2] = 220;
		otherSideOfUnitaryCube[4][1][0] = 312;
		otherSideOfUnitaryCube[4][1][2] = 10;  // 010
		otherSideOfUnitaryCube[4][2][0] = 520;
		otherSideOfUnitaryCube[4][2][1] = 510;
		otherSideOfUnitaryCube[4][2][2] = 20;  // 020
		
		otherSideOfUnitaryCube[5][0][0] = 422; 
		otherSideOfUnitaryCube[5][0][1] = 21;  // 021
		otherSideOfUnitaryCube[5][0][2] = 22;  // 022
		otherSideOfUnitaryCube[5][1][0] = 421;
		otherSideOfUnitaryCube[5][1][2] = 121;
		otherSideOfUnitaryCube[5][2][0] = 322;
		otherSideOfUnitaryCube[5][2][1] = 321;
		otherSideOfUnitaryCube[5][2][2] = 122;
	}
	
	Color getOtherSideOfUnitaryCube(int i, int j, int k)
	{
		int int_otherSide = otherSideOfUnitaryCube[i][j][k];
		
		int tab[] = new int[3];
		tab[0] = int_otherSide/100;
		tab[1] = (int_otherSide/10)%10;
		tab[2] = int_otherSide%10;
		
		return rubiksCubeSolve1[tab[0]][tab[1]][tab[2]];	
	}
	
	boolean checkUnitaryCube(int i, int j, int k, Color c1, Color c2)
	{
		if (rubiksCubeSolve1[i][j][k].equals(c1) && getOtherSideOfUnitaryCube(i,j,k).equals(c2))
			return true;
		
		return false;
	}
	
	int[] findUnitaryEdge(Color c1, Color c2)
	{
		int edgePos[] = new int[3];
		
		for (int i = 0; i < 6; ++i)
		{
			for (int j = 0; j < 3; ++j)
			{
				for (int k = 0; k < 3; ++k)
				{
					// Find the good edge
					if ((j+k)%2 == 1 && checkUnitaryCube(i, j, k, c1, c2))
					{
						edgePos[0] = i;
						edgePos[1] = j;
						edgePos[2] = k;
						return edgePos;
					}						
				}
			}
		}
		return edgePos;
	}
	
	int[] findUnitaryCorner(Color c1, Color c2) // Give c2 clockwise to c1
	{		
		int cornerPos[] = new int[3];
		
		for (int i = 0; i < 6; ++i)
		{
			for (int j = 0; j < 3; ++j)
			{
				for (int k = 0; k < 3; ++k)
				{
					// Find the good corner
					if ((j+k)%2 == 0 && checkUnitaryCube(i, j, k, c1, c2))
					{
						cornerPos[0] = i;
						cornerPos[1] = j;
						cornerPos[2] = k;
						return cornerPos;
					}						
				}
			}
		}
		return cornerPos;
	}
	
	void fillAnswerTab(int[] move, int faceToTurn, int[] turn, int timesToTurn, String[] solMessage, String message)
	{
		// Turn the cube to solve it
		for (int i = 0; i < timesToTurn; i++)
		{
			turnRubiksCube(rubiksCubeSolve1, faceToTurn, true);
		}
		
		// Write down how we turned it
		int j = 0;
		while (turn[j] != 0) // Find where is the next free space in tabs
			j++;
		
		if (j != 0 && move[j-1] == faceToTurn) // Don't use another space if last face turned is turned again
			j = j-1;
		
		if (timesToTurn%4 != 0) // Add values
		{
			move[j] = faceToTurn;
			turn[j] += timesToTurn;
			turn[j] = turn[j]%4;	
			solMessage[j] = message;
		}
	}
	
	void doOneEdge(Color c1, Color c2, SubSolution solution)
	{
		String message = "";
		String bord = "blanc-";
		int[] edgePos = findUnitaryEdge(c1, c2);
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
			fillAnswerTab(solution.move, 2, solution.turn, 3, solution.message, message); // fully placed
		}
		else if (edgePos[0] == 2)
		{
			message = "Le bord " + bord + "est sur la bonne face, mais pas où il faut. Pour ne pas déranger le "
					+ "reste, on va le mettre sur la face opposée, la tourner pour positionner le bord "
					+ "puis le remettre sur la bonne face. Ensuite on le met à droite pour laisser la place "
					+ "au prochain bord.";
			if (edgePos[1] == 1) // he's left of white face
			{
				fillAnswerTab(solution.move, 4, solution.turn, 2, solution.message, message);
				fillAnswerTab(solution.move, 5, solution.turn, 1, solution.message, message);
				fillAnswerTab(solution.move, 0, solution.turn, 2, solution.message, message); // placed (2, 1, 2)
				fillAnswerTab(solution.move, 2, solution.turn, 3, solution.message, message); // fully placed (2, 2, 1)
			}
			else if (edgePos[1] == 0) // he's top of white face
			{
				fillAnswerTab(solution.move, 3, solution.turn, 2, solution.message, message);
				fillAnswerTab(solution.move, 5, solution.turn, 2, solution.message, message);
				fillAnswerTab(solution.move, 0, solution.turn, 2, solution.message, message); // placed
				fillAnswerTab(solution.move, 2, solution.turn, 3, solution.message, message); // fully placed
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
				fillAnswerTab(solution.move, 5, solution.turn, 1, solution.message, message);
				edgePos = findUnitaryEdge(c1, c2);
			}
			fillAnswerTab(solution.move, 0, solution.turn, 2, solution.message, message); // placed
			fillAnswerTab(solution.move, 2, solution.turn, 3, solution.message, message); // fully placed
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
					
				fillAnswerTab(solution.move, 2, solution.turn, timesToTurn, solution.message, message); 				
				edgePos = findUnitaryEdge(c1, c2);
				
				while (edgePos[0] != 2)
				{
					fillAnswerTab(solution.move, faceOfOtherSideOfEdge, solution.turn, 1, solution.message, message);
					edgePos = findUnitaryEdge(c1, c2);
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
				
				fillAnswerTab(solution.move, edgePos[0], solution.turn, 1, solution.message, message); 				
				edgePos = findUnitaryEdge(c1, c2);
				
				// Comme dans le if
				int timesToTurn = -1;
				
				int faceOfOtherSideOfEdge = otherSideOfUnitaryCube[edgePos[0]][edgePos[1]][edgePos[2]]/100;
				if 	    (faceOfOtherSideOfEdge==0) timesToTurn = 0;
				else if (faceOfOtherSideOfEdge==1) timesToTurn = 3;
				else if (faceOfOtherSideOfEdge==3) timesToTurn = 2;
				else if (faceOfOtherSideOfEdge==4) timesToTurn = 1;
					
				fillAnswerTab(solution.move, 2, solution.turn, timesToTurn, solution.message, message); 				
				edgePos = findUnitaryEdge(c1, c2);
				
				while (edgePos[0] != 2)
				{
					fillAnswerTab(solution.move, faceOfOtherSideOfEdge, solution.turn, 1, solution.message, message);
					edgePos = findUnitaryEdge(c1, c2);
				}
			}
			fillAnswerTab(solution.move, 2, solution.turn, 3, solution.message, message); // fully placed
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
		int[] edgePos = findUnitaryEdge(Color.white, Color.red);
		while (edgePos[1] != 2)
		{
			fillAnswerTab(solution.move, 2, solution.turn, 1, solution.message, message);
			edgePos = findUnitaryEdge(Color.white, Color.red);
		}
		
		solutionFirstCross = solution;
	}
}
