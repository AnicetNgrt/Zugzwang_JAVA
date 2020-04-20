package zug.classes;

import utils.JsonUtils;
import zug.enums.Cardinal;
import zug.enums.Modifiers;
import zug.jsonClasses.JsonModifierEmbed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ModifierEmbed {
	private Modifiers modifier;
	private ArrayList<Integer> playersI;
	private ArrayList<ArrayList<Integer>> pawnsI;
	private Cardinal orientation;

	public ModifierEmbed(Modifiers m, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI, Cardinal o) {
		modifier = m;
		this.playersI = playersI;
		this.pawnsI = pawnsI;
		orientation = o;
	}

	public static ModifierEmbed fromJson(String jsonPath) {
		JsonUtils<JsonModifierEmbed> jUtils = new JsonUtils<>(JsonModifierEmbed.class);
		JsonModifierEmbed mtkj = jUtils.readJson(jsonPath);

		Modifiers mod = Modifiers.valueOf(mtkj.modifierName);
		Cardinal or = Cardinal.valueOf(mtkj.orientationName);
		return new ModifierEmbed(mod, mtkj.playersI, mtkj.pawnsI, or);
	}

	ModifierConclusion execute(GameState gameState) {
		return modifier.execute(gameState, playersI, pawnsI, orientation);
	}

	public String toJson(HashMap<String, String> pathes) {
		int id = new Random().nextInt(99);
		String path = pathes.get(this.getClass().getName()) + "me|" + modifier.name() + "|" + id + ".json";
		JsonUtils<JsonModifierEmbed> jUtils = new JsonUtils<>(JsonModifierEmbed.class);
		JsonModifierEmbed jme = new JsonModifierEmbed();
		jme.modifierName = modifier.name();
		jme.orientationName = orientation.name();
		jme.playersI = playersI;
		jme.pawnsI = pawnsI;
		jUtils.writeJson(path, jme);
		return path;
	}
}
