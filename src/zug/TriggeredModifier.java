package zug;

import java.util.ArrayList;

public abstract class TriggeredModifier extends ModifierToolKit{

	TriggeredModifier(Modifiers m, Game g, Player p1, Player p2, ArrayList<Pawn> s, ArrayList<Pawn> r, Cardinal o) {
		super(m, g, p1, p2, s, r, o);
	}
	
	abstract boolean isTriggered();
	abstract ActionEndReason execute();
}

class MimicTriggerModifier extends TriggeredModifier {

	MimicTriggerModifier(Modifiers m, Game g, Player p1, Player p2, ArrayList<Pawn> s, ArrayList<Pawn> r, Cardinal o) {
		super(m, g, p1, p2, s, r, o);
	}

	boolean isTriggered() {
		return false;
	}

	@Override
	ActionEndReason execute() {
		return null;
	}
	
}