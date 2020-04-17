package zug;

public class Board {
	private Coor2d maxCoor;
	private Coor2d[][] shiftMap;
	private boolean[][] restrictedMap;
	private boolean[][] obstructedMap;
	
	boolean isRestricted(Coor2d c) {
		return restrictedMap[c.y()][c.x()];
	}
	
	void setRestricted(Coor2d c, boolean r) {
		restrictedMap[c.y()][c.x()] = r;
	}
	
	boolean isObstructed(Coor2d c) {
		return obstructedMap[c.y()][c.x()];
	}
	
	void setObstructed(Coor2d c, boolean o) {
		obstructedMap[c.y()][c.x()] = o;
	}
	
	Coor2d shift(Coor2d c) {
		return c.add(shiftMap[c.y()][c.x()]);
	}
	
	Coor2d pacmanCoor(Coor2d c) {
		return new Coor2d(c.x() % maxCoor.x(), c.y() % maxCoor.y());
	}
	
	boolean isIn(Coor2d c) {
		return c.inShape(new Coor2d(0,0), maxCoor);
	}
	
	boolean isBorder(Coor2d c) {
		return c.x() == 0 || c.y() == 0 || c.x() == maxCoor.x() || c.y() == maxCoor.y();
	}
	
	boolean isCorner(Coor2d c) {
		return isBorder(new Coor2d(c.x(), maxCoor.y()/2)) && isBorder(new Coor2d(maxCoor.x()/2, c.y()));
	}
}
