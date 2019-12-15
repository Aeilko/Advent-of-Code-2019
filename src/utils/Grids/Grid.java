package utils.Grids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.TreeSet;

public class Grid {

	private Point[][] grid;

	public Grid(int sizeX, int sizeY){
		this.grid = new Point[sizeX][sizeY];
		for(int x = 0; x < sizeX; x++){
			for(int y = 0; y < sizeY; y++){
				Point p = new Point(x, y);
				grid[x][y] = p;
				if(x > 0){
					grid[x-1][y].setEast(p);
					p.setWest(grid[x-1][y]);
				}
				if(y > 0){
					grid[x][y-1].setSouth(p);
					p.setNorth(grid[x][y-1]);
				}
			}
		}
	}

	public Point getPoint(int x, int y){
		return this.grid[x][y];
	}
	public Point getPoint(Coordinate c){
		return getPoint(c.getX(), c.getY());
	}

	public ArrayList<Point> getPath(Point start, Point finish){
		// Overview of exact costs to get to a certain point
		TreeMap<Point, Integer> exactCost = new TreeMap<>();
		exactCost.put(start, 0);

		// Visited, and yet to visit points
		TreeSet<Point> visited = new TreeSet<>();
		TreeSet<PointWithCost> toVisit = new TreeSet<PointWithCost>(new CostedPointComparator());
		toVisit.add(new PointWithCost(start, null, 0));

		// Final answer
		PointWithCost fin = null;

		while(toVisit.size() > 0){
			// Set this point as visited
			PointWithCost pc = toVisit.first();
			Point cur = pc.getPoint();
			toVisit.remove(pc);
			if(!exactCost.containsKey(cur))
				exactCost.put(cur, exactCost.get(pc.getPrevPoint().getPoint())+1);
			if(cur.equals(finish)){
				fin = pc;
				break;
			}

			// Sometimes points get added multiple times (like with multiple costs), so we can skip it
			if(visited.contains(cur))
				continue;
			visited.add(cur);

			TreeSet<Point> nexts = cur.getNeighbours();
			for(Point p: nexts){
				if(!visited.contains(p)){
					int predictedCost = exactCost.get(cur) + Point.manhattanDistance(p, finish);
					toVisit.add(new PointWithCost(p, pc, predictedCost));
				}
			}
		}

		ArrayList<Point> result = null;
		if(fin != null){
			result = new ArrayList<>();
			while(fin.getPrevPoint() != null) {
				result.add(fin.getPoint());
				fin = fin.getPrevPoint();
			}
			Collections.reverse(result);
		}

		return result;
	}

	public int longestDistanceFrom(int x, int y){
		TreeSet<Point> visitedPoints = new TreeSet<>();
		TreeSet<Point> pointsToVisit = new TreeSet<>();

		pointsToVisit.add(this.grid[x][y]);

		// Start at -1 because we start with the start position unvisited
		int i = -1;
		while(pointsToVisit.size() > 0){
			TreeSet<Point> newPoints = new TreeSet<>();

			for(Point p: pointsToVisit){
				for(Point p2: p.getNeighbours()){
					if(!visitedPoints.contains(p2) && !pointsToVisit.contains(p2))
						newPoints.add(p2);
				}
				visitedPoints.add(p);
			}

			pointsToVisit = newPoints;

			i++;
		}

		return i;
	}

	@Override
	public String toString() {
		String result = "";

		for(int y = 0; y < this.grid[0].length; y++){
			for(int x = 0; x < this.grid.length; x++){
				Point p = grid[x][y];
				if(p.getNeighbours().size() == 0)
					result += "#";
				else
					result += " ";
			}
			result += "\n";
		}

		return result;
	}
}
