package zug.enums;

import zug.classes.Coor2d;

public enum Cardinal {
    NORTH(0, new Coor2d(0, 1)), EAST(1, new Coor2d(1, 0)), SOUTH(2, new Coor2d(0, -1)), WEST(3, new Coor2d(-1, 0)),
    NONE(4, new Coor2d(0, 0));

    private int i;
    private Coor2d vector;

    Cardinal(int i, Coor2d vector) {
        this.i = i;
        this.vector = vector;
    }

    public Coor2d vector() {
		return vector;
	}

	public Cardinal[] rotate(Cardinal... from) {
		Cardinal[] res = new Cardinal[from.length];
		if (from.length == 0) {
			return from;
		} else if (from.length > 1) {
			int i = 0;
			for (Cardinal c : from) {
				res[i++] = rotate(this, c)[0];
			}
		} else {
            res[0] = Cardinal.values()[(from[0].i + this.i) % 4];
        }
		return res;
	}
}
