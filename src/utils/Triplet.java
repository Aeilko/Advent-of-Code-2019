package utils;

public class Triplet implements Comparable, Cloneable {

	private int x;
	private int y;
	private int z;

	public Triplet(){
		this(0,0,0);
	}

	public Triplet(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void incX(){
		this.x++;
	}

	public void decX(){
		this.x--;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void incY(){
		this.y++;
	}

	public void decY(){
		this.y--;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public void incZ(){
		this.z++;
	}

	public void decZ(){
		this.z--;
	}

	public Triplet copy(){
		return new Triplet(this.x, this.y, this.z);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	@Override
	public boolean equals(Object obj) {
		Triplet t = (Triplet) obj;
		return (this.x == t.getX() && this.y == t.getY() && this.z == t.getZ());
	}

	@Override
	public int compareTo(Object o) {
		int result;
		Triplet t = (Triplet) o;

		if(this.x < t.getX())
			result = -1;
		else if(this.x > t.getX())
			result = 1;
		else if(this.y < t.getY())
			result = -1;
		else if(this.y > t.getY())
			result = 1;
		else if(this.z < t.getZ())
			result = -1;
		else if(this.z > t.getZ())
			result = 1;
		else
			result = 0;

		return result;
	}
}
