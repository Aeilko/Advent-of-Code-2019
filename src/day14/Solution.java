package day14;

import java.util.TreeMap;

public class Solution {

	public static final String[] INPUT = {"11 TDFGK, 1 LKTZ => 5 DMLM","2 PLWS, 10 CQRWX, 1 DQRM, 1 DXDTM, 1 GBNH, 5 FKPL, 1 JCSDM => 4 LMPH","2 FXBZT, 1 VRZND => 5 QKCQW","3 VRZND => 4 LKTZ","15 FKPL, 6 DNXHG => 6 ZFBTC","7 QFBZN => 3 FXBZT","151 ORE => 1 QZNXC","16 WCHD, 15 LWBQL => 3 MBXSW","13 DXDTM => 6 RCNV","1 MSXF, 1 VRZND => 9 SWBRL","109 ORE => 9 LSLQW","5 DNXHG => 5 GBNH","2 DZXGB => 6 VRZND","1 FKPL, 1 XPGX, 2 RCNV, 1 LGXK, 3 QBVQ, 7 GBJC => 9 SCXQ","3 DVHQD => 3 QXWFM","1 XKXPK, 1 DMLM => 9 HGNW","1 TSMCQ, 6 ZFBTC, 1 WCHD, 3 QBVQ, 7 QXWFM, 14 LWBQL => 9 TFMNM","17 NBVPR, 7 LJQGC => 9 LWBQL","3 NBVPR => 4 ZGVC","4 DNXHG => 2 CQRWX","1 RCKS, 3 LWBQL => 3 TSMCQ","3 LJCR, 15 JBRG => 7 TWBN","7 WZSH, 4 QXWFM => 3 JMCQ","9 SWBRL, 8 LJCR, 33 NLJH => 3 JMVG","1 CQRWX => 4 FZVM","6 LJQGC, 12 DVHQD, 15 HGNW => 4 RCKS","3 WCHD => 3 XPGX","6 JBRG, 1 NQXZM, 1 LJCR => 2 LJQGC","16 SDQK => 9 PLWS","2 QFBZN, 2 LSLQW => 4 MSXF","8 QZNXC => 6 NBVPR","1 NBVPR, 1 LKTZ => 5 LJCR","11 SWBRL, 2 QKCQW => 7 JBRG","7 JMCQ, 7 DVHQD, 4 BXPB => 8 DXDTM","1 WCHD => 7 QBVQ","2 CQRWX => 5 GBJC","4 JMVG => 4 BXPB","7 WZSH => 8 TDFGK","5 XLNR, 10 ZGVC => 6 DNXHG","7 RCNV, 4 MLPH, 25 QBVQ => 2 LGXK","1 DMLM => 3 XLNR","6 FZVM, 4 BGKJ => 9 JCSDM","7 LWBQL, 1 JCSDM, 6 GBJC => 4 DQRM","2 FXBZT, 2 QKCQW => 5 XKXPK","3 LMPH, 33 NQXZM, 85 MBXSW, 15 LWBQL, 5 SCXQ, 13 QZNXC, 6 TFMNM, 7 MWQTH => 1 FUEL","8 NQXZM, 6 TDFGK => 4 DVHQD","5 NQXZM, 2 TWBN => 7 CFKF","132 ORE => 3 DZXGB","6 QZNXC, 10 QFBZN => 3 NLJH","15 SWBRL, 1 QZNXC, 4 NBVPR => 7 WZSH","20 DNXHG => 3 SDQK","1 LJCR, 1 JBRG, 1 LKTZ => 4 NQXZM","16 JMVG, 1 LJQGC => 9 BGKJ","4 TSMCQ => 3 FKPL","1 CFKF => 2 WCHD","162 ORE => 3 QFBZN","18 WCHD => 5 MLPH","13 LJQGC, 1 SDQK => 9 MWQTH"};

	public static final String[] TINPUT1 = {"10 ORE => 10 A","1 ORE => 1 B","7 A, 1 B => 1 C","7 A, 1 C => 1 D","7 A, 1 D => 1 E","7 A, 1 E => 1 FUEL"};
	public static final String[] TINPUT2 = {"9 ORE => 2 A","8 ORE => 3 B","7 ORE => 5 C","3 A, 4 B => 1 AB","5 B, 7 C => 1 BC","4 C, 1 A => 1 CA","2 AB, 3 BC, 4 CA => 1 FUEL"};
	public static final String[] TINPUT3 = {"157 ORE => 5 NZVS","165 ORE => 6 DCFZ","44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL","12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ","179 ORE => 7 PSHF","177 ORE => 5 HKGWZ","7 DCFZ, 7 PSHF => 2 XJWVT","165 ORE => 2 GPVTF","3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"};
	public static final String[] TINPUT4 = {"2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG","17 NVRVD, 3 JNWZP => 8 VPVL","53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL","22 VJHF, 37 MNCFX => 5 FWMGM","139 ORE => 4 NVRVD","144 ORE => 7 JNWZP","5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC","5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV","145 ORE => 6 MNCFX","1 NVRVD => 8 CXFTF","1 VJHF, 6 MNCFX => 4 RFSQX","176 ORE => 6 VJHF"};
	public static final String[] TINPUT5 = {"171 ORE => 8 CNZTR","7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL","114 ORE => 4 BHXH","14 VRPVC => 6 BMBT","6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL","6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT","15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW","13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW","5 BMBT => 4 WPTQ","189 ORE => 9 KTJDG","1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP","12 VRPVC, 27 CNZTR => 2 XDBXC","15 KTJDG, 12 BHXH => 5 XCVML","3 BHXH, 2 VRPVC => 7 MZWV","121 ORE => 7 VRPVC","7 XCVML => 6 RJRHP","5 BHXH, 4 VRPVC => 5 LTCX"};

	public static void main(String[] args){
		TreeMap<String, Reaction> reactions = new TreeMap<>();
		for(String s: INPUT){
			Reaction r = new Reaction(s);
			reactions.put(r.getResult(), r);
		}

		long oneFuelCost = reactions.get("FUEL").getOreCostFor(1, reactions);

		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + oneFuelCost + "\n");

		reset(reactions);
		// Some useful variables
		long TRILLION = 1000000000000L;
		long thousandFuel = oneFuelCost*1000;
		long hundredFuel = oneFuelCost*100;
		long tenFuel = oneFuelCost*10;


		long ballPark = TRILLION/oneFuelCost;
		long count = ballPark;
		long oreLeft = TRILLION - reactions.get("FUEL").getOreCostFor(count, reactions);

		while(oreLeft > 0){
			if(oreLeft > thousandFuel){
				oreLeft -= reactions.get("FUEL").getOreCostFor(1000, reactions);
				count += 1000;
			}
			else if(oreLeft > hundredFuel){
				oreLeft -= reactions.get("FUEL").getOreCostFor(100, reactions);
				count += 100;
			}
			else if(oreLeft > tenFuel) {
				oreLeft -= reactions.get("FUEL").getOreCostFor(10, reactions);
				count += 10;
			}
			else{
				oreLeft -= reactions.get("FUEL").getOreCostFor(1, reactions);
				count ++;
			}
		}

		System.out.println("Part 2 - Success");
		System.out.println("Answer\t" + (count-1));
	}

	public static void reset(TreeMap<String, Reaction> in){
		for(String s: in.keySet())
			in.get(s).reset();
	}
}

class Reaction {

	// Result of reaction
	private String result;
	private int resultQuantity;

	// Our current stock of the resulting product
	private int stock = 0;

	// Cost of reaction
	private TreeMap<String, Integer> costs;

	public Reaction(String r) {
		// Split into costs and results
		String[] split = r.split(" => ");

		// Extract results
		String[] res = split[1].split(" ");
		this.result = res[1];
		this.resultQuantity = Integer.parseInt(res[0]);

		// Extract costs
		String[] costs = split[0].split(", ");
		this.costs = new TreeMap<>();
		for (String s : costs) {
			String[] sa = s.split(" ");
			this.costs.put(sa[1], Integer.parseInt(sa[0]));
		}
	}

	public long getOreCostFor(long quantity, TreeMap<String, Reaction> reactions) {
		long result = 0;

		long runs = (long) Math.ceil(((double) quantity) / this.resultQuantity);

		// Check if can use the stock
		if (this.stock > quantity) {
			this.stock -= quantity;
		}
		else {
			// Check if we have enough stock to reduce the amount of runs
			if (this.stock >= this.resultQuantity)
				runs--;
			else if (quantity % this.resultQuantity != 0 && quantity % this.resultQuantity <= this.stock)
				runs--;

			// Calculate the ORE cost of each product
			for (String k : this.costs.keySet()) {
				if (k.equals("ORE"))
					result += this.costs.get(k) * runs;
				else
					result += reactions.get(k).getOreCostFor(this.costs.get(k) * runs, reactions);
			}

			// Update the stock
			this.stock += (runs * this.resultQuantity) - quantity;
		}

		return result;
	}

	public String getResult() {
		return result;
	}

	public void reset() {
		this.stock = 0;
	}

	@Override
	public String toString() {
		String result = this.resultQuantity + " " + this.result + "\t\t";

		for (String k : this.costs.keySet()) {
			result += this.costs.get(k) + " " + k + ", ";
		}

		return result;
	}
}