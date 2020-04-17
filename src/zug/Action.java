package zug;

import java.util.ArrayList;

public class Action {
	private Modifiers modifier;
	private int cost;
	private int maxGame;
	private int maxTurn;
	private int playedTurn;
	private int playedGame;
	
	Action(Modifiers modifier, int cost, int maxGame, int maxTurn) {
		this.modifier = modifier;
		this.cost = cost;
		this.maxGame = maxGame;
		this.maxTurn = maxTurn;
		this.playedTurn = 0;
		this.playedGame = 0;
	}

	Action(Action a) {
		this(a.modifier, a.cost, a.maxGame, a.maxTurn);
		playedTurn = a.playedTurn;
		playedGame = a.playedGame;
	}
	
	int cost() {
		return cost;
	}
	
	int maxGame() {
		return maxGame;
	}
	
	int maxTurn() {
		return maxTurn;
	}
	
	int playedTurn() {
		return playedTurn;
	}
	
	int playedGame() {
		return playedGame;
	}
	
	ActionEndReason play(Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) {
		ActionEndReason review = modifier.execute(g, p1, p2, senders, receivers, orientation);
		if(review == ActionEndReason.SUCCESS) {
			p1.pay(cost);
			g.addToClock(cost);
		}
		return review;
	}
	
	void turnReset() {
		playedTurn = 0;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" ");
		return sb.toString();
	}
}
