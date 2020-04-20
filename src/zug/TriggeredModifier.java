package zug;

import java.util.ArrayList;

public abstract class TriggeredModifier extends ModifierToolKit {

	TriggeredModifier(Modifiers m, Game g, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI, Cardinal o) {
		super(m, g, playersI, pawnsI, o);
	}

	abstract boolean isTriggered();

	abstract ModifierConclusion execute();
}

class MimicTriggerModifier extends TriggeredModifier {

	MimicTriggerModifier(Modifiers m, Game g, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI, Cardinal o) {
		super(m, g, playersI, pawnsI, o);
	}

	boolean isTriggered() {
		return false;
	}

	@Override
	ModifierConclusion execute() {
		return null;
	}
	
}