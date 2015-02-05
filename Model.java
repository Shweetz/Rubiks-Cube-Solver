﻿
import java.awt.*;

public class Model {

	Color[][][] rubiksCube = new Color[6][3][3];
	
	public Model() 
	{
		InitializeGrid();
		//FillGrid();
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
	
	void FillGrid()
	{
		rubiksCube[0][0][0] = Color.white;
		rubiksCube[0][0][1] = Color.white;
		rubiksCube[0][0][2] = Color.blue;
		rubiksCube[0][1][0] = Color.red;
		rubiksCube[0][1][1] = Color.white;
		rubiksCube[0][1][2] = Color.yellow;
		rubiksCube[0][2][0] = Color.blue;
		rubiksCube[0][2][1] = Color.orange;
		rubiksCube[0][2][2] = Color.red;
		
		rubiksCube[1][0][0] = Color.red;
		rubiksCube[1][0][1] = Color.orange;
		rubiksCube[1][0][2] = Color.orange;
		rubiksCube[1][1][0] = Color.green;
		rubiksCube[1][1][1] = Color.blue;
		rubiksCube[1][1][2] = Color.blue;
		rubiksCube[1][2][0] = Color.white;
		rubiksCube[1][2][1] = Color.yellow;
		rubiksCube[1][2][2] = Color.green;
		
		rubiksCube[2][0][0] = Color.yellow;
		rubiksCube[2][0][1] = Color.green;
		rubiksCube[2][0][2] = Color.yellow;
		rubiksCube[2][1][0] = Color.white;
		rubiksCube[2][1][1] = Color.orange;
		rubiksCube[2][1][2] = Color.blue;
		rubiksCube[2][2][0] = Color.green;
		rubiksCube[2][2][1] = Color.red;
		rubiksCube[2][2][2] = Color.yellow;
		
		rubiksCube[3][0][0] = Color.blue;
		rubiksCube[3][0][1] = Color.red;
		rubiksCube[3][0][2] = Color.green;
		rubiksCube[3][1][0] = Color.yellow;
		rubiksCube[3][1][1] = Color.yellow;
		rubiksCube[3][1][2] = Color.blue;
		rubiksCube[3][2][0] = Color.orange;
		rubiksCube[3][2][1] = Color.blue;
		rubiksCube[3][2][2] = Color.green;
		
		rubiksCube[4][0][0] = Color.orange;
		rubiksCube[4][0][1] = Color.green;
		rubiksCube[4][0][2] = Color.red;
		rubiksCube[4][1][0] = Color.white;
		rubiksCube[4][1][1] = Color.green;
		rubiksCube[4][1][2] = Color.yellow;
		rubiksCube[4][2][0] = Color.red;
		rubiksCube[4][2][1] = Color.green;
		rubiksCube[4][2][2] = Color.orange;
		
		rubiksCube[5][0][0] = Color.white;
		rubiksCube[5][0][1] = Color.white;
		rubiksCube[5][0][2] = Color.blue;
		rubiksCube[5][1][0] = Color.orange;
		rubiksCube[5][1][1] = Color.red;
		rubiksCube[5][1][2] = Color.orange;
		rubiksCube[5][2][0] = Color.yellow;
		rubiksCube[5][2][1] = Color.red;
		rubiksCube[5][2][2] = Color.white;
		
	}
	
	void turnRubiksCube(Color[][][] rubiksCubeToTurn, int faceTurnNumber, boolean clockwise)
	{
		int timesTurn = 1;
		if (clockwise == false)
			timesTurn = 3;
		
		for (int i = 0; i < timesTurn; ++i)
		{
			// Update the face that was turned
			setFace(faceTurnNumber, turnFace(rubiksCubeToTurn[faceTurnNumber]));

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
	
	void setFace(int faceNumber, Color[][] face)
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				rubiksCube[faceNumber][i][j] = face[i][j];
			}
		}
	}
	
	Color[][] turnFace(Color[][] face) // to the left : face[0][0] = face[2][0]
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
	
	SolveFirstCross solve(String solvingStep) // rubiksCube table doesn't change, just return a table with moves.
	{				
		// Do first face with first layer
		SolveFirstCross answer1 = new SolveFirstCross(rubiksCube);
		answer1.doFirstCross();
		
		if (answer1.isSolvable == false || solvingStep.equals("first face"))
			return answer1;
		
		// Finish the cube
		
		return answer1;
	}	
}
