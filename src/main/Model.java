package main;


import java.awt.*;

import solve.*;

public class Model {

	private Solution solution;
	
	Color[][][] rubiksCube = new Color[6][3][3];
	protected int[][][] otherSideOfUnitaryCube = new int[6][3][3];
	
	public Model() 
	{
		InitializeGrid();
	}
	
	void InitializeGrid()
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				rubiksCube[0][i][j] = Color.white;
				rubiksCube[1][i][j] = Color.blue;
				rubiksCube[2][i][j] = Color.orange;
				rubiksCube[3][i][j] = Color.yellow;
				rubiksCube[4][i][j] = Color.green;
				rubiksCube[5][i][j] = Color.red;
			}
		}
	}
	
	protected void InitializeOtherSideTab() // For corners, other side is the next side clockwise
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
	
	protected Color getOtherSideOfUnitaryCube(Color[][][] rubiksCubeToCheck, int i, int j, int k)
	{
		int int_otherSide = otherSideOfUnitaryCube[i][j][k];
		
		int tab[] = new int[3];
		tab[0] = int_otherSide/100;
		tab[1] = (int_otherSide/10)%10;
		tab[2] = int_otherSide%10;
		
		return rubiksCubeToCheck[tab[0]][tab[1]][tab[2]];	
	}
	
	protected boolean checkUnitaryCube(Color[][][] rubiksCubeToCheck, int i, int j, int k, Color c1, Color c2)
	{
		if (rubiksCubeToCheck[i][j][k].equals(c1) && (getOtherSideOfUnitaryCube(rubiksCubeToCheck,i,j,k)).equals(c2))
			return true;
		
		return false;
	}
	
	protected int[] findUnitaryEdge(Color[][][] rubiksCubeToCheck, Color c1, Color c2)
	{
		int edgePos[] = {-1,-1,-1}; 
		
		for (int i = 0; i < 6; ++i)
		{
			for (int j = 0; j < 3; ++j)
			{
				for (int k = 0; k < 3; ++k)
				{
					// Find the good edge
					if ((j+k)%2 == 1 && checkUnitaryCube(rubiksCubeToCheck, i, j, k, c1, c2))
					{
						edgePos[0] = i;
						edgePos[1] = j;
						edgePos[2] = k;
						return edgePos;
					}						
				}
			}
		}
		// if we get here, {-1,-1,-1} is returned and the edge wasn't found in the cube (unsolvable cube)
		solution.isSolvable = false;
		return edgePos; 
	}
	
	protected int[] findUnitaryCorner(Color[][][] rubiksCubeToCheck, Color c1, Color c2) // Give c2 clockwise to c1
	{		
		int cornerPos[] = new int[3];
		
		for (int i = 0; i < 6; ++i)
		{
			for (int j = 0; j < 3; ++j)
			{
				for (int k = 0; k < 3; ++k)
				{
					// Find the good corner (j!=1 so that a center can't be considered as corner)
					if ((j+k)%2 == 0 && checkUnitaryCube(rubiksCubeToCheck, i, j, k, c1, c2) && j!=1)
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
	
	protected void fillAnswerTab(Solution solution, Color[][][] rubiksCubeToCheck, int faceToTurn, int timesToTurn, String messageToShow, String solveStep)
	{
		// Turn the cube to solve it
		for (int i = 0; i < timesToTurn; i++)
		{
			turnRubiksCube(rubiksCubeToCheck, faceToTurn, true);
		}
		
		// Write down how we turned it
		int j = 0;
		while (solution.turn[j] != 0) // Find where is the next free space in tabs
			j++;
		
		// Don't use another space if last face turned is turned again
		if (j != 0 && solution.move[j-1] == faceToTurn && solveStep.equals(solution.step[j-1]))
			j = j-1;
		
		if (timesToTurn%4 != 0) // Add values
		{
			solution.move[j] = faceToTurn;
			solution.turn[j] += timesToTurn;
			solution.turn[j] = solution.turn[j]%4;	
			solution.message[j] = messageToShow;
			solution.step[j] = solveStep;
		}
	}
	
	void turnRubiksCube(Color[][][] rubiksCubeToTurn, int faceTurnNumber, boolean clockwise)
	{
		int timesTurn = 1;
		if (clockwise == false)
			timesTurn = 3;
		
		for (int i = 0; i < timesTurn; ++i)
		{
			// Update the face that was turned
			setFace(rubiksCubeToTurn, faceTurnNumber, turnFace(rubiksCubeToTurn[faceTurnNumber]));

			// Update the 4 others impacted faces
			Color temp1 = null;
			Color temp2 = null;
			Color temp3 = null;
			
			if (faceTurnNumber == 0)
			{
				temp1 = rubiksCubeToTurn[2][2][0];
				temp2 = rubiksCubeToTurn[2][2][1];
				temp3 = rubiksCubeToTurn[2][2][2];
				
				rubiksCubeToTurn[2][2][0] = rubiksCubeToTurn[4][2][2];
				rubiksCubeToTurn[2][2][1] = rubiksCubeToTurn[4][1][2];
				rubiksCubeToTurn[2][2][2] = rubiksCubeToTurn[4][0][2];
				
				rubiksCubeToTurn[4][2][2] = rubiksCubeToTurn[5][0][2];
				rubiksCubeToTurn[4][1][2] = rubiksCubeToTurn[5][0][1];
				rubiksCubeToTurn[4][0][2] = rubiksCubeToTurn[5][0][0];
				
				rubiksCubeToTurn[5][0][2] = rubiksCubeToTurn[1][0][0];
				rubiksCubeToTurn[5][0][1] = rubiksCubeToTurn[1][1][0];
				rubiksCubeToTurn[5][0][0] = rubiksCubeToTurn[1][2][0];
				
				rubiksCubeToTurn[1][0][0] = temp1;
				rubiksCubeToTurn[1][1][0] = temp2;
				rubiksCubeToTurn[1][2][0] = temp3;
			}
			
			else if (faceTurnNumber == 1)
			{
				temp1 = rubiksCubeToTurn[2][2][2];
				temp2 = rubiksCubeToTurn[2][1][2];
				temp3 = rubiksCubeToTurn[2][0][2];
				
				rubiksCubeToTurn[2][2][2] = rubiksCubeToTurn[0][2][2];
				rubiksCubeToTurn[2][1][2] = rubiksCubeToTurn[0][1][2];
				rubiksCubeToTurn[2][0][2] = rubiksCubeToTurn[0][0][2];
				
				rubiksCubeToTurn[0][2][2] = rubiksCubeToTurn[5][2][2];
				rubiksCubeToTurn[0][1][2] = rubiksCubeToTurn[5][1][2];
				rubiksCubeToTurn[0][0][2] = rubiksCubeToTurn[5][0][2];
				
				rubiksCubeToTurn[5][2][2] = rubiksCubeToTurn[3][0][0];
				rubiksCubeToTurn[5][1][2] = rubiksCubeToTurn[3][1][0];
				rubiksCubeToTurn[5][0][2] = rubiksCubeToTurn[3][2][0];
				
				rubiksCubeToTurn[3][0][0] = temp1;
				rubiksCubeToTurn[3][1][0] = temp2;
				rubiksCubeToTurn[3][2][0] = temp3;
			}
			
			else if (faceTurnNumber == 2)
			{
				temp1 = rubiksCubeToTurn[3][0][0];
				temp2 = rubiksCubeToTurn[3][0][1];
				temp3 = rubiksCubeToTurn[3][0][2];
				
				rubiksCubeToTurn[3][0][0] = rubiksCubeToTurn[4][0][0];
				rubiksCubeToTurn[3][0][1] = rubiksCubeToTurn[4][0][1];
				rubiksCubeToTurn[3][0][2] = rubiksCubeToTurn[4][0][2];
				
				rubiksCubeToTurn[4][0][0] = rubiksCubeToTurn[0][0][0];
				rubiksCubeToTurn[4][0][1] = rubiksCubeToTurn[0][0][1];
				rubiksCubeToTurn[4][0][2] = rubiksCubeToTurn[0][0][2];
				
				rubiksCubeToTurn[0][0][0] = rubiksCubeToTurn[1][0][0];
				rubiksCubeToTurn[0][0][1] = rubiksCubeToTurn[1][0][1];
				rubiksCubeToTurn[0][0][2] = rubiksCubeToTurn[1][0][2];
				
				rubiksCubeToTurn[1][0][0] = temp1;
				rubiksCubeToTurn[1][0][1] = temp2;
				rubiksCubeToTurn[1][0][2] = temp3;
			}
			
			else if (faceTurnNumber == 3)
			{
				temp1 = rubiksCubeToTurn[2][0][2];
				temp2 = rubiksCubeToTurn[2][0][1];
				temp3 = rubiksCubeToTurn[2][0][0];
				
				rubiksCubeToTurn[2][0][2] = rubiksCubeToTurn[1][2][2];
				rubiksCubeToTurn[2][0][1] = rubiksCubeToTurn[1][1][2];
				rubiksCubeToTurn[2][0][0] = rubiksCubeToTurn[1][0][2];
				
				rubiksCubeToTurn[1][2][2] = rubiksCubeToTurn[5][2][0];
				rubiksCubeToTurn[1][1][2] = rubiksCubeToTurn[5][2][1];
				rubiksCubeToTurn[1][0][2] = rubiksCubeToTurn[5][2][2];
				
				rubiksCubeToTurn[5][2][0] = rubiksCubeToTurn[4][0][0];
				rubiksCubeToTurn[5][2][1] = rubiksCubeToTurn[4][1][0];
				rubiksCubeToTurn[5][2][2] = rubiksCubeToTurn[4][2][0];
				
				rubiksCubeToTurn[4][0][0] = temp1;
				rubiksCubeToTurn[4][1][0] = temp2;
				rubiksCubeToTurn[4][2][0] = temp3;
			}
			
			else if (faceTurnNumber == 4)
			{
				temp1 = rubiksCubeToTurn[2][0][0];
				temp2 = rubiksCubeToTurn[2][1][0];
				temp3 = rubiksCubeToTurn[2][2][0];
				
				rubiksCubeToTurn[2][0][0] = rubiksCubeToTurn[3][2][2];
				rubiksCubeToTurn[2][1][0] = rubiksCubeToTurn[3][1][2];
				rubiksCubeToTurn[2][2][0] = rubiksCubeToTurn[3][0][2];
				
				rubiksCubeToTurn[3][2][2] = rubiksCubeToTurn[5][0][0];
				rubiksCubeToTurn[3][1][2] = rubiksCubeToTurn[5][1][0];
				rubiksCubeToTurn[3][0][2] = rubiksCubeToTurn[5][2][0];
				
				rubiksCubeToTurn[5][0][0] = rubiksCubeToTurn[0][0][0];
				rubiksCubeToTurn[5][1][0] = rubiksCubeToTurn[0][1][0];
				rubiksCubeToTurn[5][2][0] = rubiksCubeToTurn[0][2][0];
				
				rubiksCubeToTurn[0][0][0] = temp1;
				rubiksCubeToTurn[0][1][0] = temp2;
				rubiksCubeToTurn[0][2][0] = temp3;
			}
			
			else if (faceTurnNumber == 5)
			{
				temp1 = rubiksCubeToTurn[3][2][0];
				temp2 = rubiksCubeToTurn[3][2][1];
				temp3 = rubiksCubeToTurn[3][2][2];
				
				rubiksCubeToTurn[3][2][0] = rubiksCubeToTurn[1][2][0];
				rubiksCubeToTurn[3][2][1] = rubiksCubeToTurn[1][2][1];
				rubiksCubeToTurn[3][2][2] = rubiksCubeToTurn[1][2][2];
				
				rubiksCubeToTurn[1][2][0] = rubiksCubeToTurn[0][2][0];
				rubiksCubeToTurn[1][2][1] = rubiksCubeToTurn[0][2][1];
				rubiksCubeToTurn[1][2][2] = rubiksCubeToTurn[0][2][2];
				
				rubiksCubeToTurn[0][2][0] = rubiksCubeToTurn[4][2][0];
				rubiksCubeToTurn[0][2][1] = rubiksCubeToTurn[4][2][1];
				rubiksCubeToTurn[0][2][2] = rubiksCubeToTurn[4][2][2];
				
				rubiksCubeToTurn[4][2][0] = temp1;
				rubiksCubeToTurn[4][2][1] = temp2;
				rubiksCubeToTurn[4][2][2] = temp3;
			}
		}
	}
	
	Color[][] getFace(int faceNumber)
	{
		return rubiksCube[faceNumber];
	}
	
	void setFace(Color[][][] rubiksCubeToTurn, int faceNumber, Color[][] face)
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				rubiksCubeToTurn[faceNumber][i][j] = face[i][j];
			}
		}
	}
	
	protected Color[][] turnFace(Color[][] face) // to the left : face[0][0] = face[2][0]
	{
		Color[][] face2 = new Color[3][3];
		
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				face2[i][j] = face[2-j][i];
			}
		}
		return face2;
	}	
	
	public Solution solve(String solvingStep) // rubiksCube table doesn't change, just return a table with moves.
	{				
		solution = new Solution(500);
		
		SolveFirstCross answer1 = new SolveFirstCross(rubiksCube);
		answer1.doFirstCross(solution);
		
		if (solution.isSolvable == false || solvingStep.equals("first cross"))
			return solution;
		
		SolveFirstCorners answer2 = new SolveFirstCorners(answer1.rubiksCubeFirstCross);
		answer2.doFirstCorners(solution);
		
		if (solution.isSolvable == false || solvingStep.equals("first corners"))
			return solution;
		
		SolveSecondLayer answer3 = new SolveSecondLayer(answer2.rubiksCubeFirstCorners);
		answer3.doSecondLayer(solution);
		
		if (solution.isSolvable == false || solvingStep.equals("second layer"))
			return solution;
		
		SolveSecondCross answer4 = new SolveSecondCross(answer3.rubiksCubeSecondLayer);
		answer4.doSecondCross(solution);
		
		if (solution.isSolvable == false || solvingStep.equals("second cross"))
			return solution;
		
		SolveSecondEdges answer5 = new SolveSecondEdges(answer4.rubiksCubeSecondCross);
		answer5.doSecondEdges(solution);
		
		if (solution.isSolvable == false || solvingStep.equals("second edges"))
			return solution;
		
		SolveSecondCornersPosition answer6 = new SolveSecondCornersPosition(answer5.rubiksCubeSecondEdges);
		answer6.doSecondCornersPosition(solution);
		
		if (solution.isSolvable == false || solvingStep.equals("second corners position"))
			return solution;
		
		SolveSecondCornersOrientation answer7 = new SolveSecondCornersOrientation(answer6.rubiksCubeSecondCornersPosition);
		answer7.doSecondCornersOrientation(solution);
		
		// If we get here, we went for a full solve
		int lastMove = 0;
		while (solution.turn[lastMove+1] != 0)
			lastMove++;
		solution.step[lastMove] = "finish";
		
		return solution;
	}	
}
