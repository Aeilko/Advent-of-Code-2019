package utils;

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