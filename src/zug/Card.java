package zug;

import java.util.ArrayList;
import java.util.HashSet;

public class Card {
	private Types type;
	private ArrayList<Action> actions;
	private Player owner;
	private Cardinal orientation;
	private boolean shown;
	
	Card(Types type, ArrayList<Action> actions, Cardinal orientation, boolean shown) {
		this.type = type; this.actions = actions;
		this.orientation = orientation;
		this.shown = shown;
	}
	
	Types type() {
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
	
	ArrayList<Action> actions() {
		return new ArrayList<Action>(actions);
	}
	
	ActionEndReason play(int i, Game g, Player target, ArrayList<Pawn> senders, ArrayList<Pawn> receivers) {
		if(i < 0 || i >= actions.size()) return ActionEndReason.INDEX_OUT_OF_RANGE;
		Action action = actions.get(i);
		if(owner.ap() < action.cost()) return ActionEndReason.TOO_EXPENSIVE;
		if(action.playedTurn() >= action.maxTurn()) return ActionEndReason.MAX_TURN;
		if(action.playedGame() >= action.maxGame()) return ActionEndReason.MAX_GAME;
		return action.play(g, owner, target, senders, receivers, orientation);
	}
	
	void turnReset() {
		for(Action a:actions)
			a.turnReset();
	}
	
	enum Types {
		PETITS_RUISSEAUX("Petits Ruisseaux",
				0,
				true,
				true,
				new Action(Modifiers.M1, 1, 999, 999)),
		CAVALIER("Cavalier",
				8,
				false,
				true,
				new Action(Modifiers.MCavLeft, 1, 999, 999),
				new Action(Modifiers.MCavRight, 1, 999, 999));
		
		private String name;
		private Action[] actions;
		private int weight;
		private boolean oriented;
		private boolean shown;
		
		Types(String name, int weight, boolean oriented, boolean shown, Action... actions) {
			this.name = name;
			this.weight = weight;
			this.oriented = oriented;
			this.shown = shown;
			this.actions = actions;
		}
		
		int weight() {
			return weight;
		}
		
		boolean oriented() {
			return oriented;
		}
		
		String trueName() {
			return name;
		}
		
		Card getOne(Cardinal orientation) {
			ArrayList<Action> cardActions = new ArrayList<Action>();
			for(Action a:actions) {
				cardActions.add(new Action(a));
			}
			return new Card(this, cardActions, orientation, shown);
		}
	}
}
