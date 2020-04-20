package zug;

import java.util.ArrayList;

public class ModifierToolKit {
	private Modifiers modifier;
	private Game game;
	private ArrayList<Integer> playersI;
	private ArrayList<ArrayList<Integer>> pawnsI;
	private Cardinal orientation;

	ModifierToolKit(Modifiers m, Game g, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI, Cardinal o) {
		modifier = m;
		game = g;
		this.playersI = playersI;
		this.pawnsI = pawnsI;
		orientation = o;
	}

	ModifierConclusion execute() {
		return modifier.execute(game, playersI, pawnsI, orientation);
	}
}
