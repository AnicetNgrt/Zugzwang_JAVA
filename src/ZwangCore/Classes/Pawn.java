package ZwangCore.Classes;

import Utils.JsonUtils;
import ZwangCore.Enums.ActionEndReason;
import ZwangCore.JsonClasses.JsonPawn;

import java.util.HashMap;

public class Pawn {
    private Coor2d coor;
    private Player owner;
    private boolean exiled;
    private boolean alive;
    private boolean immortal;

    Pawn(Coor2d c, Player owner) {
        this.coor = c;
        this.setOwner(owner);
        this.setExiled(false);
        alive = true;
        immortal = false;
    }

    Pawn(Pawn pa, Player ownerCopy) {
		this(pa.coor, ownerCopy);
		exiled = pa.exiled;
		alive = pa.alive;
	}

    public static Pawn fromJson(String jsonPath, Player owner) {
        JsonPawn paj = (JsonPawn) JsonUtils.readJson(jsonPath, JsonPawn.class);

        Pawn pa = new Pawn(new Coor2d(paj.x, paj.y), owner);
        pa.exiled = paj.exiled;
        pa.alive = paj.alive;
        return pa;
    }

    void moveTo(Coor2d c, Board b) {
        c = b.pacmanCoor(c);
        coor = c;
    }

    boolean correctPos(Board b) {
        if (b.isObstructed(coor)) return false;
        if (b.isRestricted(coor)) coor = b.shift(coor);
        return true;
    }

    Coor2d coor() {
        return coor;
    }

    Player getOwner() {
        return owner;
    }

    boolean isImmortal() {
        return immortal;
    }

    void setImmortal(boolean b) {
        immortal = b;
    }

    void setOwner(Player owner) {
        this.owner = owner;
    }

    boolean isExiled() {
        return exiled;
    }

    void setExiled(boolean exiled) {
		this.exiled = exiled;
	}

	boolean isAlive() {
		return alive;
	}

	ActionEndReason kill(Board b) {
		if (!alive) return ActionEndReason.CANT_DIE_TWICE;
		alive = false;
		b.setObstructed(coor, false);
		return ActionEndReason.SUCCESS;
	}

	public String toJson(HashMap<String, String> pathes, int i) {
        int id = System.identityHashCode(this);
        String path = pathes.get("Pawn") + "pawn_" + i + "_" + owner.name() + "_" + id + ".json";
        JsonPawn jpa = new JsonPawn();
        jpa.alive = alive;
        jpa.exiled = exiled;
        jpa.x = coor.x();
        jpa.y = coor.y();
        jpa.immortal = immortal;
        JsonUtils.writeJson(path, jpa);
        return path;
    }
}
