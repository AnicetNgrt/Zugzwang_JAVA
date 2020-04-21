package zug.classes;

import utils.JsonUtils;
import zug.jsonClasses.JsonBoard;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private final Coor2d[][] shiftMap;
    private final ArrayList<Coor2d> middles;
    private boolean[][] restrictedMap;
    private boolean[][] obstructedMap;
    private final Sizes size;

    Board(Sizes size) {
        this.size = size;

        ArrayList<Integer> mys = new ArrayList<>();
        ArrayList<Integer> mxs = new ArrayList<>();

        if ((maxCoor().y() + 1) % 2 == 0) {
            mys.add((maxCoor().y() + 1) / 2);
            mys.add(((maxCoor().y() + 1) / 2) - 1);
        } else {
            mys.add((maxCoor().y()) / 2);
        }
        if ((maxCoor().x() + 1) % 2 == 0) {
            mxs.add((maxCoor().x() + 1) / 2);
            mxs.add(((maxCoor().x() + 1) / 2) - 1);
        } else {
            mxs.add((maxCoor().x()) / 2);
        }

        middles = new ArrayList<>();

        mxs.sort(null);
        mys.sort(null);

        // [higher left, higher right,
        //  lower left,  lower right]

        for (int y : mys) {// haut en bas
            for (int x : mxs) {// gauche à droite
                middles.add(new Coor2d(x, y));
            }
        }

        shiftMap = new Coor2d[maxCoor().y() + 1][maxCoor().x() + 1];
        restrictedMap = new boolean[maxCoor().y() + 1][maxCoor().x() + 1];
        obstructedMap = new boolean[maxCoor().y() + 1][maxCoor().x() + 1];

        for (int y = 0; y <= maxCoor().y(); y++) {
            for (int x = 0; x <= maxCoor().x(); x++) {
                Coor2d vec1, vec2;
                if (y > middles.get(0).y()) {
                    vec1 = new Coor2d(0, 1);
                } else if (y < middles.get(middles.size() - 1).y()) {
                    vec1 = new Coor2d(0, -1);
                } else {
                    vec1 = new Coor2d(0, 0);
                }

                if (x > middles.get(middles.size() - 1).x()) {
					vec2 = new Coor2d(-1, 0);
				} else if (x < middles.get(0).x()) {
					vec2 = new Coor2d(1, 0);
				} else {
                    vec2 = new Coor2d(0, 0);
                }

                shiftMap[y][x] = vec1.add(vec2);
                restrictedMap[y][x] = false;
                obstructedMap[y][x] = false;
            }
        }
    }

    Board(Board b) {
        this(b.size);
        for (int i = 0; i < maxCoor().y(); i++) {
            for (int j = 0; j < maxCoor().x(); j++) {
                restrictedMap[i][j] = b.restrictedMap[i][j];
                obstructedMap[i][j] = b.obstructedMap[i][j];
            }
        }
    }

    public static Board fromJson(String jsonPath) {
        JsonBoard jb = (JsonBoard) JsonUtils.readJson(jsonPath, JsonBoard.class);

        Board b = new Board(Sizes.valueOf(jb.sizeName));
        b.obstructedMap = jb.obstructedMap;
        b.restrictedMap = jb.restrictedMap;
        return b;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isIn(Coor2d c) {
        return c.inShape(new Coor2d(0, 0), maxCoor());
    }

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
        return new Coor2d(c.x() % maxCoor().x(), c.y() % maxCoor().y());
    }

    boolean isCorner(Coor2d c) {
        return isBorder(new Coor2d(c.x(), maxCoor().y() / 2)) && isBorder(new Coor2d(maxCoor().x() / 2, c.y()));
    }

    boolean isBorder(Coor2d c) {
        return c.x() == 0 || c.y() == 0 || c.x() == maxCoor().x() || c.y() == maxCoor().y();
    }

    boolean isMiddle(Coor2d c) {
        return middles.contains(c);
    }

    Coor2d maxCoor() {
        return this.size.maxCoor();
    }

    public String toJson(HashMap<String, String> pathes) {
        int id = System.identityHashCode(this);
        String path = pathes.get("Board") + "board_" + id + ".json";
        JsonBoard jb = new JsonBoard();
        jb.obstructedMap = obstructedMap;
        jb.restrictedMap = restrictedMap;
        jb.sizeName = size.name();
        JsonUtils.writeJson(path, jb);
        return path;
    }

    public enum Sizes {
        SMALL(new Coor2d(7, 10)),
        STANDARD(new Coor2d(9, 14)),
        EXTENDED(new Coor2d(19, 29));

        private Coor2d maxCoor;

        Sizes(Coor2d maxCoor) {
            this.maxCoor = maxCoor;
        }

		Coor2d maxCoor() {
			return maxCoor;
		}
	}
}
