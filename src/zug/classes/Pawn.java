package zug.classes;

import utils.JsonUtils;
import zug.enums.ActionEndReason;
import zug.jsonClasses.JsonPawn;

public class Pawn {
	private Coor2d coor;
	private Player owner;
	private boolean exiled;
	private boolean alive;

	Pawn(Coor2d c, Player owner) {
		this.coor = c;
		this.setOwner(owner);
		this.setExiled(false);
		alive = true;
	}

	Pawn(Pawn pa, Player ownerCopy) {
		this(pa.coor, ownerCopy);
		exiled = pa.exiled;
		alive = pa.alive;
	}

	public static Pawn fromJson(String jsonPath, Player owner) {
		JsonUtils<JsonPawn> jUtils = new JsonUtils<>(JsonPawn.class);
		JsonPawn paj = jUtils.readJson(jsonPath);

		Pawn pa = new Pawn(new Coor2d(paj.x, paj.y), owner);
		pa.exiled = paj.exiled;
		pa.alive = paj.alive;
		return pa;
	}

	void moveTo(Coor2d c) {
		coor = c;
	}

	Coor2d coor() {
		return coor;
	}

	Player getOwner() {
		return owner;
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
		if(!alive) return ActionEndReason.CANT_DIE_TWICE;
		alive = false;
		b.setObstructed(coor, false);
		return ActionEndReason.SUCCESS;
	}
}
