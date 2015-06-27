package com.mindblock.tools;

public class Room {

	
	private boolean start, treasure, enemy;
	private boolean left, right, up, down;
	
	public Room(boolean left, boolean right, boolean up, boolean down){
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
		
		this.start = false;
		this.treasure = false;
		this.enemy = false;
	}
	
	public void setIsStartRoom(){
		this.start = true;
	}
	
	public void setHasEnemy(){
		this.enemy = true;
	}
	
	public boolean hasEnemy(){
		return this.enemy;
	}
	
	public void setHasTreasure(){
		this.treasure = true;
	}
	
	public boolean isStartRoom(){
		return this.start;
	}
	
	public boolean hasTreasure(){
		return this.treasure;
	}
	
	public boolean getLeftRoom(){
		return this.left;
	}
	
	public boolean getRightRoom(){
		return this.right;
	}
	
	public boolean getUpRoom(){
		return this.up;
	}
	
	public boolean getDownRoom(){
		return this.down;
	}
	
	public void setLeftRoom(boolean left){
		this.left = left;
	}
	
	public void setRightRoom(boolean right){
		this.right = right;
	}
	
	public void setUpRoom(boolean up){
		this.up = up;
	}
	
	public void setDownRoom(boolean down){
		this.down = down;
	}
	
	public boolean hasAnyDoors(){
		return (this.left || this.right || this.up || this.down);
	}
	
	public String toFileFormat(){
		
		String l = this.left ? "1" : "0";
		String r = this.right ? "1" : "0";
		String u = this.up ? "1" : "0";
		String d = this.down ? "1" : "0";
		
		String t = this.treasure ? "1" : "0";
		String s = this.start ? "1" : "0";
		String e = this.enemy ? "1" : "0";
		
		return ""+l+""+r+""+u+""+d+""+s+""+t+""+e+"";
	}
}
