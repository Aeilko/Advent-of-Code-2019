package utils.Grids;

public class PointWithCost {

	private PointWithCost prevPoint;
	private Point point;
	private int cost;

	public PointWithCost(Point p, PointWithCost prev, int c){
		this.prevPoint = prev;
		this.point = p;
		this.cost = c;
	}

	public Point getPoint() {
		return point;
	}

	public PointWithCost getPrevPoint() {
		return prevPoint;
	}

	public int getCost() {
		return cost;
	}
}
