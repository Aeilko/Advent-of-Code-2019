package day23;

import utils.IntcodeComputer.IntcodeComputer;
import utils.IntcodeComputer.IntcodeComputerThread;
import utils.IntcodeComputer.NetworkNode;

import java.util.concurrent.ArrayBlockingQueue;

public class Solution {

	public static final String PROGRAM = "3,62,1001,62,11,10,109,2215,105,1,0,942,2019,1365,1109,2118,1957,1080,2151,907,2056,2087,1685,1394,1821,1336,876,1615,1485,1920,808,1549,602,983,703,1047,1650,748,1580,1986,1454,1012,779,1239,1518,1303,1747,1718,1177,633,571,1425,2186,1885,1852,1788,670,1144,1210,841,1272,0,0,0,0,0,0,0,0,0,0,0,0,3,64,1008,64,-1,62,1006,62,88,1006,61,170,1106,0,73,3,65,21001,64,0,1,21002,66,1,2,21102,1,105,0,1105,1,436,1201,1,-1,64,1007,64,0,62,1005,62,73,7,64,67,62,1006,62,73,1002,64,2,132,1,132,68,132,1001,0,0,62,1001,132,1,140,8,0,65,63,2,63,62,62,1005,62,73,1002,64,2,161,1,161,68,161,1101,0,1,0,1001,161,1,169,102,1,65,0,1101,1,0,61,1102,0,1,63,7,63,67,62,1006,62,203,1002,63,2,194,1,68,194,194,1006,0,73,1001,63,1,63,1105,1,178,21101,0,210,0,106,0,69,2101,0,1,70,1102,1,0,63,7,63,71,62,1006,62,250,1002,63,2,234,1,72,234,234,4,0,101,1,234,240,4,0,4,70,1001,63,1,63,1106,0,218,1105,1,73,109,4,21102,1,0,-3,21101,0,0,-2,20207,-2,67,-1,1206,-1,293,1202,-2,2,283,101,1,283,283,1,68,283,283,22001,0,-3,-3,21201,-2,1,-2,1105,1,263,22102,1,-3,-3,109,-4,2105,1,0,109,4,21102,1,1,-3,21101,0,0,-2,20207,-2,67,-1,1206,-1,342,1202,-2,2,332,101,1,332,332,1,68,332,332,22002,0,-3,-3,21201,-2,1,-2,1106,0,312,21202,-3,1,-3,109,-4,2106,0,0,109,1,101,1,68,359,20101,0,0,1,101,3,68,366,21001,0,0,2,21102,376,1,0,1105,1,436,22102,1,1,0,109,-1,2106,0,0,1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536,131072,262144,524288,1048576,2097152,4194304,8388608,16777216,33554432,67108864,134217728,268435456,536870912,1073741824,2147483648,4294967296,8589934592,17179869184,34359738368,68719476736,137438953472,274877906944,549755813888,1099511627776,2199023255552,4398046511104,8796093022208,17592186044416,35184372088832,70368744177664,140737488355328,281474976710656,562949953421312,1125899906842624,109,8,21202,-6,10,-5,22207,-7,-5,-5,1205,-5,521,21101,0,0,-4,21101,0,0,-3,21102,51,1,-2,21201,-2,-1,-2,1201,-2,385,471,20102,1,0,-1,21202,-3,2,-3,22207,-7,-1,-5,1205,-5,496,21201,-3,1,-3,22102,-1,-1,-5,22201,-7,-5,-7,22207,-3,-6,-5,1205,-5,515,22102,-1,-6,-5,22201,-3,-5,-3,22201,-1,-4,-4,1205,-2,461,1106,0,547,21101,-1,0,-4,21202,-6,-1,-6,21207,-7,0,-5,1205,-5,547,22201,-7,-6,-7,21201,-4,1,-4,1105,1,529,21202,-4,1,-7,109,-8,2105,1,0,109,1,101,1,68,564,20102,1,0,0,109,-1,2105,1,0,1102,1873,1,66,1102,1,1,67,1102,598,1,68,1102,556,1,69,1101,0,1,71,1102,600,1,72,1105,1,73,1,2780339,45,3083,1101,43787,0,66,1102,1,1,67,1102,629,1,68,1102,1,556,69,1101,0,1,71,1101,0,631,72,1106,0,73,1,-24,25,57653,1102,37361,1,66,1101,0,4,67,1102,660,1,68,1101,302,0,69,1102,1,1,71,1102,668,1,72,1105,1,73,0,0,0,0,0,0,0,0,7,160526,1102,3083,1,66,1101,0,2,67,1101,697,0,68,1101,0,302,69,1102,1,1,71,1102,701,1,72,1105,1,73,0,0,0,0,32,113318,1102,1,61553,66,1102,1,1,67,1102,1,730,68,1101,556,0,69,1101,0,8,71,1101,732,0,72,1106,0,73,1,2,32,56659,28,3574,44,191974,3,75883,46,98354,16,111778,35,81463,35,244389,1102,23189,1,66,1101,0,1,67,1102,775,1,68,1101,0,556,69,1102,1,1,71,1102,777,1,72,1106,0,73,1,-28447,8,68577,1102,87881,1,66,1101,1,0,67,1102,1,806,68,1101,556,0,69,1101,0,0,71,1102,1,808,72,1106,0,73,1,1773,1102,1,1699,66,1102,1,1,67,1101,835,0,68,1102,1,556,69,1102,2,1,71,1102,1,837,72,1105,1,73,1,3,38,37361,38,74722,1101,24151,0,66,1101,0,3,67,1102,868,1,68,1102,302,1,69,1102,1,1,71,1102,1,874,72,1105,1,73,0,0,0,0,0,0,18,29476,1101,0,95071,66,1101,1,0,67,1101,903,0,68,1102,1,556,69,1102,1,1,71,1102,905,1,72,1105,1,73,1,11,16,167667,1102,22859,1,66,1102,3,1,67,1102,1,934,68,1101,253,0,69,1102,1,1,71,1102,1,940,72,1105,1,73,0,0,0,0,0,0,46,49177,1101,0,26161,66,1101,1,0,67,1102,1,969,68,1102,556,1,69,1101,0,6,71,1102,1,971,72,1106,0,73,1,18508,24,100153,42,41179,42,123537,48,24151,48,48302,48,72453,1101,0,18553,66,1101,0,1,67,1101,0,1010,68,1102,1,556,69,1102,1,0,71,1101,1012,0,72,1105,1,73,1,1971,1101,0,96667,66,1101,1,0,67,1102,1,1039,68,1102,1,556,69,1102,3,1,71,1102,1041,1,72,1106,0,73,1,5,1,34157,1,102471,35,325852,1102,1,100153,66,1101,0,2,67,1102,1,1074,68,1102,302,1,69,1101,0,1,71,1101,1078,0,72,1106,0,73,0,0,0,0,18,22107,1101,40693,0,66,1102,1,1,67,1101,0,1107,68,1102,556,1,69,1102,1,0,71,1102,1,1109,72,1106,0,73,1,1337,1102,75883,1,66,1102,1,3,67,1102,1136,1,68,1101,0,302,69,1102,1,1,71,1101,0,1142,72,1105,1,73,0,0,0,0,0,0,18,7369,1102,49177,1,66,1101,0,2,67,1101,1171,0,68,1101,0,302,69,1101,1,0,71,1101,0,1175,72,1106,0,73,0,0,0,0,7,80263,1101,82171,0,66,1101,0,1,67,1102,1,1204,68,1101,0,556,69,1102,2,1,71,1101,1206,0,72,1105,1,73,1,32,45,6166,4,50551,1101,35797,0,66,1102,1,1,67,1102,1,1237,68,1102,1,556,69,1102,0,1,71,1102,1,1239,72,1106,0,73,1,1204,1101,56659,0,66,1102,2,1,67,1102,1,1266,68,1102,1,302,69,1101,0,1,71,1101,0,1270,72,1106,0,73,0,0,0,0,28,1787,1102,4889,1,66,1102,1,1,67,1102,1,1299,68,1101,0,556,69,1102,1,1,71,1102,1301,1,72,1106,0,73,1,4003,27,94483,1101,0,8291,66,1102,1,1,67,1102,1330,1,68,1102,1,556,69,1101,0,2,71,1102,1332,1,72,1105,1,73,1,1,27,283449,25,172959,1101,12569,0,66,1101,1,0,67,1101,0,1363,68,1101,0,556,69,1102,0,1,71,1102,1,1365,72,1105,1,73,1,1771,1102,92179,1,66,1102,1,1,67,1101,0,1392,68,1102,556,1,69,1101,0,0,71,1101,0,1394,72,1106,0,73,1,1758,1101,0,29063,66,1102,1,1,67,1102,1421,1,68,1102,1,556,69,1101,0,1,71,1102,1423,1,72,1105,1,73,1,1041,38,149444,1102,64187,1,66,1102,1,1,67,1101,1452,0,68,1101,556,0,69,1102,0,1,71,1101,1454,0,72,1106,0,73,1,1103,1101,28289,0,66,1101,0,1,67,1102,1481,1,68,1101,556,0,69,1102,1,1,71,1101,0,1483,72,1105,1,73,1,149,38,112083,1101,100361,0,66,1102,1,1,67,1102,1512,1,68,1101,0,556,69,1102,2,1,71,1101,1514,0,72,1106,0,73,1,1381,3,227649,25,115306,1102,3593,1,66,1101,1,0,67,1102,1,1545,68,1101,556,0,69,1101,1,0,71,1102,1547,1,72,1106,0,73,1,160,35,162926,1101,6197,0,66,1102,1,1,67,1102,1576,1,68,1101,556,0,69,1101,0,1,71,1101,0,1578,72,1106,0,73,1,162381,8,22859,1101,94483,0,66,1102,3,1,67,1102,1607,1,68,1101,302,0,69,1102,1,1,71,1101,1613,0,72,1105,1,73,0,0,0,0,0,0,7,240789,1101,55889,0,66,1101,3,0,67,1101,1642,0,68,1102,1,302,69,1102,1,1,71,1102,1648,1,72,1105,1,73,0,0,0,0,0,0,24,200306,1102,1,57653,66,1101,0,3,67,1101,0,1677,68,1102,302,1,69,1102,1,1,71,1101,1683,0,72,1105,1,73,0,0,0,0,0,0,42,82358,1102,1,19471,66,1102,1,1,67,1101,1712,0,68,1102,556,1,69,1102,1,2,71,1102,1,1714,72,1105,1,73,1,10,1,136628,35,488778,1101,0,94099,66,1102,1,1,67,1101,1745,0,68,1101,0,556,69,1102,0,1,71,1102,1,1747,72,1105,1,73,1,1276,1102,1,81463,66,1101,6,0,67,1101,1774,0,68,1101,302,0,69,1102,1,1,71,1102,1786,1,72,1106,0,73,0,0,0,0,0,0,0,0,0,0,0,0,43,120514,1101,0,95987,66,1102,1,2,67,1101,0,1815,68,1102,302,1,69,1102,1,1,71,1101,1819,0,72,1106,0,73,0,0,0,0,3,151766,1102,1,3271,66,1101,0,1,67,1101,1848,0,68,1102,556,1,69,1101,0,1,71,1102,1850,1,72,1106,0,73,1,49395,8,45718,1101,60257,0,66,1101,0,2,67,1101,1879,0,68,1101,0,351,69,1102,1,1,71,1102,1883,1,72,1106,0,73,0,0,0,0,255,26161,1102,41179,1,66,1101,3,0,67,1101,1912,0,68,1102,1,302,69,1101,1,0,71,1101,1918,0,72,1105,1,73,0,0,0,0,0,0,18,14738,1102,1,7369,66,1102,1,4,67,1101,1947,0,68,1101,253,0,69,1101,1,0,71,1102,1,1955,72,1105,1,73,0,0,0,0,0,0,0,0,43,60257,1101,0,11197,66,1101,1,0,67,1101,0,1984,68,1101,0,556,69,1101,0,0,71,1101,1986,0,72,1105,1,73,1,1979,1102,1,1787,66,1102,1,2,67,1101,2013,0,68,1101,0,302,69,1101,1,0,71,1102,1,2017,72,1105,1,73,0,0,0,0,44,95987,1101,34157,0,66,1101,0,4,67,1102,2046,1,68,1102,1,302,69,1102,1,1,71,1101,0,2054,72,1105,1,73,0,0,0,0,0,0,0,0,35,407315,1102,1,86179,66,1102,1,1,67,1101,2083,0,68,1102,556,1,69,1101,1,0,71,1102,1,2085,72,1106,0,73,1,125,1,68314,1102,98573,1,66,1101,1,0,67,1102,1,2114,68,1102,556,1,69,1102,1,1,71,1102,2116,1,72,1105,1,73,1,-62,27,188966,1102,50551,1,66,1102,1,2,67,1102,1,2145,68,1101,0,302,69,1101,1,0,71,1101,0,2149,72,1105,1,73,0,0,0,0,16,55889,1101,0,80263,66,1102,1,3,67,1102,1,2178,68,1102,1,253,69,1101,0,1,71,1102,1,2184,72,1106,0,73,0,0,0,0,0,0,4,101102,1102,81353,1,66,1101,1,0,67,1101,2213,0,68,1101,0,556,69,1102,0,1,71,1101,2215,0,72,1105,1,73,1,1627";

	public static void main(String[] args){

		long stack[] = IntcodeComputer.processInput(PROGRAM);

		NetworkNode nn = new NetworkNode();
		IntcodeComputerThread[] ics = new IntcodeComputerThread[50];

		// Create all ICs and assign network addresses
		for(int i = 0; i < 50; i++){
			ics[i] = new IntcodeComputerThread(stack, 3);
			ics[i].setNetwork(i, nn);
			try {
				ics[i].getInputArray().put((long) i);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Create our custom catch-255 IC
		ArrayBlockingQueue<Long> out = new ArrayBlockingQueue<>(64);
		IntcodeComputer tmpIC = new IntcodeComputer(stack, 2);
		tmpIC.setInputArray(out);
		nn.registerIC(255, tmpIC);

		// Start our network
		nn.start();
		for(int i = 0; i < 50; i++){
			ics[i].start();
		}

		// Wait for packet on address 255
		long x = 0;
		long y = 0;
		try {
			x = out.take();
			y = out.take();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + y + "\n");


		// Stop all ICs
		for(int i = 0; i < ics.length; i++)
			ics[i].stopThread();
		// Stop the network node
		nn.stopThread();
	}
}
