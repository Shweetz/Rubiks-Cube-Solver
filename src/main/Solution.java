package main;

public class Solution {

	public int[] move;
	public int[] turn;
	public String[] message;
	public String[] step;
	public boolean isSolvable;
	
	public Solution(int size)
	{
		move = new int[size];
		turn = new int[size];
		message = new String[size];
		step = new String[size];
		isSolvable = true;
	}
}
