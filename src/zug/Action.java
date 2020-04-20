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

	ModifierConclusion simulate(Game g, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI, Cardinal orientation) {
		return modifier.execute(g, playersI, pawnsI, orientation);
	}

	ModifierConclusion play(Game g, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI, Cardinal orientation) {
		ModifierConclusion ccl = simulate(g, playersI, pawnsI, orientation);
		if (ccl.endReason == ActionEndReason.SUCCESS) {
			ccl.after.getPlayer(playersI.get(0)).pay(cost);
			ccl.after.addToClock(cost);
		}
		return ccl;
	}

	Modifiers modifier() {
		return modifier;
	}

	public String toString() {
		return "";
	}
}
