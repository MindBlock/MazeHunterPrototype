package com.mindblock.main;

import java.util.Random;

import com.mindblock.tools.Coordinate;
import com.mindblock.tools.Room;

public class Maze {

	private int width, height;
	private Coordinate start, treasure1, treasure2, treasure3, enemy;
	private static final double DOOR_GENERATION_PROBABILITY = 0.70;

	public Maze(int size){
		this.width = size;
		this.height = size;
	}

	public Room[][] generateMaze(int difficulty){
		Room[][] maze = new Room[this.width][this.height];

		//Generate a path from start to all treasures
		//Difficulty determined by shortest path from start to all treasures.
		this.fillMaze(maze);

		do {
			this.addStartAndTreasures(maze);
		} while(this.calculatePathLength() != difficulty);
		
		this.setArtifactsInMaze(maze);

		for (int x = 0; x < maze.length; x++){
			for (int y = 0; y < maze[0].length; y++){

				//purely for printing:
				String l = maze[y][x].getLeftRoom() ? "<" : " ";
				String u = maze[y][x].getUpRoom() ? "^" : " ";
				String d = maze[y][x].getDownRoom() ? "v" : " ";
				String r = maze[y][x].getRightRoom() ? ">" : " ";

				if(maze[y][x].isStartRoom())
					System.out.print("[S"+ l + "" + u + "" + d + "" + r + "S]");
				else if(maze[x][y].hasTreasure())
					System.out.print("[T"+ l + "" + u + "" + d + "" + r + "T]");
				else if(maze[y][x].hasEnemy())
					System.out.print("[E"+ l + "" + u + "" + d + "" + r + "E]");
				else
					System.out.print(" ["+ l + "" + u + "" + d + "" + r + "] ");
			}
			System.out.println();
		}

		System.out.println("min path from start -> treasure 1: " + this.calculatePathLength());

		return maze;
	}


	private void fillMaze(Room[][] maze){

		//Randomly create doors per room in a zigzag pattern
		int shift = 0;
		for (int x = 0; x < maze.length; x++){
			for (int y = 0 + shift; y < maze[0].length; y+=2){
				this.addInitialDoors(maze, new Coordinate(y,x));
			}
			shift = (shift+1)%2;
		}

		//Fill up the other rooms
		for (int x = 0; x < maze.length; x++){
			for (int y = 0; y < maze[0].length; y++){
				this.addFillingDoors(maze, new Coordinate(y,x));
			}
		}
	}



	private void addInitialDoors(Room[][] maze, Coordinate room){
		Random r = new Random();

		//Initialize a closed room
		Room newRoom = new Room(false, false, false, false);
		maze[room.getX()][room.getY()] = newRoom;

		//Can place a door left
		if (room.getX() > 0){
			maze[room.getX()][room.getY()].setLeftRoom(r.nextDouble() < DOOR_GENERATION_PROBABILITY);
		}
		//Can place a door right
		if (room.getX() < maze.length-1){
			maze[room.getX()][room.getY()].setRightRoom(r.nextDouble() < DOOR_GENERATION_PROBABILITY);
		}
		//Can place a door up
		if (room.getY() > 0){
			maze[room.getX()][room.getY()].setUpRoom(r.nextDouble() < DOOR_GENERATION_PROBABILITY);
		}
		//Can place a door down
		if (room.getY() < maze[0].length-1){
			maze[room.getX()][room.getY()].setDownRoom(r.nextDouble() < DOOR_GENERATION_PROBABILITY);
		}
	}



	private void addFillingDoors(Room[][] maze, Coordinate room){
		//If room has been initialized, do nothing
		if (maze[room.getX()][room.getY()] != null){
			return;
		}

		//Initiate all doors to false;
		boolean left = false;
		boolean right = false;
		boolean up = false;
		boolean down = false;

		//Left:
		if (room.getX() > 0 && maze[room.getX()-1][room.getY()].getRightRoom()){
			left = true;
		}

		//right
		if (room.getX() < maze.length-1 && maze[room.getX()+1][room.getY()].getLeftRoom()){
			right = true;
		}

		//up
		if (room.getY() > 0 && maze[room.getX()][room.getY()-1].getDownRoom()){
			up = true;
		}

		//down
		if (room.getY() < maze[0].length-1 && maze[room.getX()][room.getY()+1].getUpRoom()){
			down = true;
		}

		//Set the room in the maze
		maze[room.getX()][room.getY()] = new Room(left, right, up, down);
	}


	private int calculatePathLength(){
		int path = 0;

		//start to T1
		path += this.manhattenDistance(start, treasure1);

		//start to T2
		path += this.manhattenDistance(start, treasure2);

		//start to T3
		path += this.manhattenDistance(start, treasure3);

		//T1 to T2
		path += this.manhattenDistance(treasure1, treasure2);

		//T1 to T3
		path += this.manhattenDistance(treasure1, treasure3);

		//T2 to T3
		path += this.manhattenDistance(treasure2, treasure3);

		//start to enemy
		path -= this.manhattenDistance(start, enemy);
		
		//size of maze
		path += this.width;
		
		return path;
	}

	private void addStartAndTreasures(Room[][] maze){
		Random r = new Random();
		int x = 0;
		int y = 0;

		//start
		do {
			x = r.nextInt(maze.length);
			y = r.nextInt(maze[0].length);
			this.start = new Coordinate(y, x);
		} while (!maze[this.start.getX()][this.start.getY()].hasAnyDoors());
		
		//treasure 1
		do {
			x = r.nextInt(maze.length);
			y = r.nextInt(maze[0].length);
			this.treasure1 = new Coordinate(y, x);
		} while (!maze[this.treasure1.getX()][this.treasure1.getY()].hasAnyDoors() 
				|| this.isSameCoordinate(this.start, this.treasure1));
		

		//treasure 2
		do {
			x = r.nextInt(maze.length);
			y = r.nextInt(maze[0].length);
			this.treasure2 = new Coordinate(y, x);
		} while (!maze[this.treasure2.getX()][this.treasure2.getY()].hasAnyDoors() 
				|| this.isSameCoordinate(this.start, this.treasure2)
				|| this.isSameCoordinate(this.treasure1, this.treasure2));

		//treasure 3
		do {
			x = r.nextInt(maze.length);
			y = r.nextInt(maze[0].length);
			this.treasure3 = new Coordinate(y, x);
		} while (!maze[this.treasure3.getX()][this.treasure3.getY()].hasAnyDoors() 
				|| this.isSameCoordinate(this.start, this.treasure3)
				|| this.isSameCoordinate(this.treasure1, this.treasure3)
				|| this.isSameCoordinate(this.treasure2, this.treasure3));
		
		//enemy
		do {
			x = r.nextInt(maze.length);
			y = r.nextInt(maze[0].length);
			this.enemy = new Coordinate(y, x);
		} while (!maze[this.enemy.getX()][this.enemy.getY()].hasAnyDoors() 
				|| this.isSameCoordinate(this.start, this.enemy)
				|| this.manhattenDistance(this.start, this.enemy) < (int) ((double) this.height/2));
	}
	
	
	private void setArtifactsInMaze(Room[][] maze){
		
		maze[this.start.getX()][start.getY()].setIsStartRoom();
		maze[this.treasure1.getX()][treasure1.getY()].setHasTreasure();
		maze[this.treasure2.getX()][treasure2.getY()].setHasTreasure();
		maze[this.treasure3.getX()][treasure3.getY()].setHasTreasure();
		maze[this.enemy.getX()][enemy.getY()].setHasEnemy();
	}

	private boolean isSameCoordinate(Coordinate a, Coordinate b){
		return (a.getX() == b.getX() && a.getY() == b.getY());
	}

	private int manhattenDistance(Coordinate a, Coordinate b){
		return (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
	}
}
