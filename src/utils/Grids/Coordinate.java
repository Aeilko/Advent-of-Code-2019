package utils.Grids;

import java.util.Comparator;

public class Coordinate implements Comparable<Coordinate> {
	// Coordinaten
	private int x;
	private int y;
	
	// Constructor
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	// Queries
	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public static int manhattanDistance(Coordinate c1, Coordinate c2){
		return Math.abs(c1.getX()-c2.getX()) + Math.abs(c1.getY()-c2.getY());
	}
	
	// Override's
	@Override
	public String toString(){
		return "(" + this.x + ", " + this.y + ")";
	}
	
	@Override
	public int compareTo(Coordinate c) {
		if(c.getX() == this.x){
			if(c.getY() == this.y){
				return 0;
			}
			else if(c.getY() < this.y){
				return 1;
			}
			else{
				return -1;
			}
		}
		else if(c.getX() < this.x){
			return 1;
		}
		else{
			return -1;
		}
	}
}