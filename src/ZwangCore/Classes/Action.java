package ZwangCore.Classes;

import Utils.JsonUtils;
import ZwangCore.Enums.ActionEndReason;
import ZwangCore.Enums.Cardinal;
import ZwangCore.Enums.Modifiers;
import ZwangCore.JsonClasses.JsonAction;

import java.util.ArrayList;
import java.util.HashMap;

public class Action {
	private Modifiers modifier;
	private int cost;

	public Action(Modifiers modifier, int cost) {
		this.modifier = modifier;
		this.cost = cost;
	}

	public Action(Action a) {
		this(a.modifier, a.cost);
	}

	public static Action fromJson(String jsonPath) {
        JsonAction aj = (JsonAction) JsonUtils.readJson(jsonPath, JsonAction.class);

        Modifiers modifier = Modifiers.valueOf(aj.modifierName);
        return new Action(modifier, aj.cost);
    }

	int cost() {
		return cost;
	}

	ModifierConclusion simulate(GameState g, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI, Cardinal orientation) {
		return modifier.execute(g, playersI, pawnsI, orientation);
	}

	ModifierConclusion play(GameState g, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI, Cardinal orientation) {
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

	public String toJson(HashMap<String, String> pathes) {
        int id = System.identityHashCode(this);
        String path = pathes.get("Action") + "action_" + modifier.name() + "_" + id + ".json";
        JsonAction ja = new JsonAction();
        ja.modifierName = modifier.name();
        ja.cost = cost;
        JsonUtils.writeJson(path, ja);
        return path;
    }
}
