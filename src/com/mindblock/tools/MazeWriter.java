package com.mindblock.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MazeWriter {
	
	
	public static void writeToFile(String filename, Room[][] maze){
		try {
			 
			File file = new File(filename);
 
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int y = 0; y < maze[0].length; y++){
				for (int x = 0; x < maze.length-1; x++){
					bw.write(maze[x][y].toFileFormat() + "|");
				}
				bw.write(maze[maze.length-1][y].toFileFormat());
				bw.newLine();
			}
			
			bw.close();
 
			System.out.println("Done Writing " + filename);
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
