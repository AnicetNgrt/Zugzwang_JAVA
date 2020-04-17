package zug;

import java.lang.Math; 

public class Coor2d {
	private int x;
	private int y;
	
	Coor2d(int x, int y) {
		this.x = x; this.y = y;
	}
	
	Coor2d(Coor2d c) {
		this(c.x, c.y);
	}
	
	int x() {
		return x;
	}
	
	int y() {
		return y;
	}
	
	Coor2d add(Coor2d c) {
		return new Coor2d(c.x + x, c.y + y);
	}
	
	boolean inShape(Coor2d blc, Coor2d urc) {
		return x >= blc.x && y >= blc.y && x <= urc.x && y <= urc.y;
	}
	
	int distanceTo(Coor2d c) {
		return Math.abs(x - c.x) + Math.abs(y - c.y); 
	}
}
