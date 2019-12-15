package utils.Grids;

import java.util.TreeSet;

public class Point implements Comparable {

	public final Coordinate coords;
	public final int x;
	public final int y;

	private Point NORTH;
	private Point EAST;
	private Point SOUTH;
	private Point WEST;

	public Point(Coordinate c){
		this.coords = c;
		this.x = c.getX();
		this.y = c.getY();

		NORTH = EAST = SOUTH = WEST = null;
	}

	public Point(int x, int y){
		this(new Coordinate(x, y));
	}

	public void disconnect(){
		if(NORTH != null){
			NORTH.removeSouth();
			NORTH = null;
		}
		if(EAST != null){
			EAST.removeWest();
			EAST = null;
		}
		if(SOUTH != null){
			SOUTH.removeNorth();
			SOUTH = null;
		}
		if(WEST != null){
			WEST.removeEast();
			WEST = null;
		}
	}

	public TreeSet<Point> getNeighbours(){
		TreeSet<Point> result = new TreeSet<>();

		if(NORTH != null)
			result.add(NORTH);
		if(EAST != null)
			result.add(EAST);
		if(SOUTH != null)
			result.add(SOUTH);
		if(WEST != null)
			result.add(WEST);

		return result;
	}

	public void setNorth(Point p){
		this.NORTH = p;
	}
	public void removeNorth(){
		this.NORTH = null;
	}
	public Point getNorth(){
		return this.NORTH;
	}
	public void setEast(Point p){
		this.EAST = p;
	}
	public void removeEast(){
		this.EAST = null;
	}
	public Point getEast(){
		return this.EAST;
	}
	public void setSouth(Point p){
		this.SOUTH = p;
	}
	public void removeSouth(){
		this.SOUTH = null;
	}
	public Point getSouth(){
		return this.SOUTH;
	}
	public void setWest(Point p){
		this.WEST = p;
	}
	public void removeWest(){
		this.WEST = null;
	}
	public Point getWest(){
		return this.WEST;
	}

	public static int manhattanDistance(Point p1, Point p2){
		return Coordinate.manhattanDistance(p1.coords, p2.coords);
	}

	@Override
	public boolean equals(Object obj) {
		Point p = (Point) obj;
		return (p.x == this.x && p.y == this.y);
	}

	@Override
	public int compareTo(Object o) {
		Point p = (Point) o;
		if(x < p.x)
			return -1;
		else if(x > p.x)
			return 1;
		else{
			if(y < p.y)
				return -1;
			else if(y > p.y)
				return 1;
			else
				return 0;
		}
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
