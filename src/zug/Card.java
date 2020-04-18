package zug;

import java.util.ArrayList;

public class Card {
	private CardTypes type;
	private ArrayList<Action> actions;
	private Player owner;
	private Cardinal orientation;
	private boolean shown;
	private int playedTurn;
	private int playedGame;

	Card(CardTypes type, ArrayList<Action> actions, Cardinal orientation) {
		this.type = type;
		this.actions = actions;
		this.orientation = orientation;
		this.shown = type.shown();
		playedGame = 0;
		playedTurn = 0;
	}

	CardTypes type() {
		return type;
	}

	void rotate(Cardinal orientation) {
		this.orientation = orientation;
	}

	void setOwner(Player p) {
		owner = p;
	}

	void reveal() {
		shown = true;
	}

	boolean shown() {
		return shown;
	}

	int playedTurn() {
		return playedTurn;
	}

	int playedGame() {
		return playedGame;
	}

	ArrayList<Action> actions() {
		return new ArrayList<>(actions);
	}

	ActionEndReason play(int i, Game g, Player target, ArrayList<Pawn> senders, ArrayList<Pawn> receivers) {
		if (i < 0 || i >= actions.size()) return ActionEndReason.INDEX_OUT_OF_RANGE;
		Action action = actions.get(i);
		if (owner.ap() < action.cost()) return ActionEndReason.TOO_EXPENSIVE;
		if (playedTurn >= type.maxTurn()) return ActionEndReason.MAX_TURN;
		if (playedGame >= type.maxGame()) return ActionEndReason.MAX_GAME;
		ActionEndReason aer = action.play(g, owner, target, senders, receivers, orientation);
		if (aer == ActionEndReason.SUCCESS) shown = true;
		return aer;
	}
	
	void turnReset() {
		playedTurn = 0;
	}
}
