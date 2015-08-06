package com.mindblock.main;

import java.util.Random;

import com.mindblock.tools.MazeWriter;
import com.mindblock.tools.Room;

public class Main {

	
	
	public static void main(String[] args){
		new Main().createMaze();
	}
	
	
	public void createMaze(){
			int i = 99;
			int size = 4 + (int) Math.floor(i/25);
			Maze m = new Maze(size);
			Room[][] maze = m.generateMaze(size * (new Random().nextInt(2) + 5));
			MazeWriter.writeToFile("maze"+ (i+1) +".txt", maze);
	}
}
