package zug;

import java.util.ArrayList;

public class ModifierToolKit {
	private Modifiers modifier;
	Game game;
	Player p1;
	Player p2;
	ArrayList<Pawn> senders;
	ArrayList<Pawn> receivers;
	Cardinal orientation;
	
	ModifierToolKit(Modifiers m, Game g, Player p1, Player p2, ArrayList<Pawn> s, ArrayList<Pawn> r, Cardinal o) {
		modifier = m;
		game = g;
		this.p1 = p1;
		this.p2 = p2;
		senders = s;
		receivers = r;
		orientation = o;
	}
	
	ActionEndReason execute() {
		return modifier.execute(game, p1, p2, senders, receivers, orientation);
	}
}
