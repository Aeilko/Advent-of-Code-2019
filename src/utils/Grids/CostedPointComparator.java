package utils.Grids;

import java.util.Comparator;

public class CostedPointComparator implements Comparator<PointWithCost> {
	@Override
	public int compare(PointWithCost o1, PointWithCost o2) {
		if(o1.getCost() == o2.getCost())
			return o1.getPoint().compareTo(o2.getPoint());
		else
			return o1.getCost()-o2.getCost();
	}
}
