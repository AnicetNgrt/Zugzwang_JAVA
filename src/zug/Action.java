package zug;

import java.util.ArrayList;

public class Action {
	private Modifiers modifier;
	private int cost;

	Action(Modifiers modifier, int cost) {
		this.modifier = modifier;
		this.cost = cost;
	}

	Action(Action a) {
		this(a.modifier, a.cost);
	}

	int cost() {
		return cost;
	}

	ActionEndReason play(Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) {
		ActionEndReason review = modifier.execute(g, p1, p2, senders, receivers, orientation);
		if (review == ActionEndReason.SUCCESS) {
			p1.pay(cost);
			g.addToClock(cost);
		}
		return review;
	}

	public String toString() {
		@SuppressWarnings("StringBufferReplaceableByString") StringBuilder sb = new StringBuilder();
		sb.append(" ");
		return sb.toString();
	}
}
