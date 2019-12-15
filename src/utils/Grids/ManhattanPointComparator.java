package utils.Grids;

import java.util.Comparator;

public class ManhattanPointComparator implements Comparator<Point> {

	private int baseX = 0;
	private int baseY = 0;
	private Coordinate baseCoords = new Coordinate(0, 0);

	public ManhattanPointComparator(){ }
	public ManhattanPointComparator(int x, int y){
		this.baseX = x;
		this.baseY = y;
		this.baseCoords = new Coordinate(x, y);
	}

	@Override
	public int compare(Point p1, Point p2) {
		int man1 = Coordinate.manhattanDistance(p1.coords, baseCoords);
		int man2 = Coordinate.manhattanDistance(p2.coords, baseCoords);

		if(man1 == man2)
			return p1.compareTo(p2);
		else
			return man1-man2;
	}
}
