
public class Solution {

	int[] move;
	int[] turn;
	String[] message;
	String[] step;
	boolean isSolvable;
	
	// firstCross : 25 moves max
	
	public Solution(int size)
	{
		move = new int[size];
		turn = new int[size];
		message = new String[size];
		step = new String[size];
		isSolvable = true;
	}
}
