package zug.classes;

import utils.JsonUtils;
import zug.enums.Cardinal;
import zug.enums.Modifiers;
import zug.jsonClasses.JsonModifierToolKit;

import java.util.ArrayList;

public class ModifierToolKit {
	private Modifiers modifier;
	private ArrayList<Integer> playersI;
	private ArrayList<ArrayList<Integer>> pawnsI;
	private Cardinal orientation;

	public ModifierToolKit(Modifiers m, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI, Cardinal o) {
		modifier = m;
		this.playersI = playersI;
		this.pawnsI = pawnsI;
		orientation = o;
	}

	public static ModifierToolKit fromJson(String jsonPath) {
		JsonUtils<JsonModifierToolKit> jUtils = new JsonUtils<>(JsonModifierToolKit.class);
		JsonModifierToolKit mtkj = jUtils.readJson(jsonPath);

		Modifiers mod = Modifiers.valueOf(mtkj.modifierName);
		Cardinal or = Cardinal.valueOf(mtkj.orientationName);
		return new ModifierToolKit(mod, mtkj.playersI, mtkj.pawnsI, or);
	}

	ModifierConclusion execute(GameState gameState) {
		return modifier.execute(gameState, playersI, pawnsI, orientation);
	}
}
