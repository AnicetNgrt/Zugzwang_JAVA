package zug.classes;

import utils.JsonUtils;
import zug.enums.ActionEndReason;
import zug.enums.Cardinal;
import zug.enums.Modifiers;
import zug.jsonClasses.JsonAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
		JsonUtils<JsonAction> jUtils = new JsonUtils<>(JsonAction.class);
		JsonAction aj = jUtils.readJson(jsonPath);

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
		int id = new Random().nextInt(99);
		String path = pathes.get(this.getClass().getName()) + "action|" + modifier.name() + "|" + id + ".json";
		JsonUtils<JsonAction> jUtils = new JsonUtils<>(JsonAction.class);
		JsonAction ja = new JsonAction();
		ja.modifierName = modifier.name();
		ja.cost = cost;
		jUtils.writeJson(path, ja);
		return path;
	}
}
