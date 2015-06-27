package com.mindblock.main;

import com.mindblock.tools.MazeWriter;
import com.mindblock.tools.Room;

public class Main {

	
	
	public static void main(String[] args){
		new Main().createMaze();
	}
	
	
	public void createMaze(){
		Maze m = new Maze(6);
		Room[][] maze = m.generateMaze(40);
		MazeWriter.writeToFile("maze1.txt", maze);
	}
}
