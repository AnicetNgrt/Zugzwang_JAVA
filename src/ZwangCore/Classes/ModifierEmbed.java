package ZwangCore.Classes;

import Utils.JsonUtils;
import ZwangCore.Enums.Cardinal;
import ZwangCore.Enums.Modifiers;
import ZwangCore.JsonClasses.JsonModifierEmbed;

import java.util.ArrayList;
import java.util.HashMap;

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
        JsonModifierEmbed mtkj = (JsonModifierEmbed) JsonUtils.readJson(jsonPath, JsonModifierEmbed.class);

        Modifiers mod = Modifiers.valueOf(mtkj.modifierName);
        Cardinal or = Cardinal.valueOf(mtkj.orientationName);
        ArrayList<Integer> playersI = new ArrayList<>();
        for (int i : mtkj.playersI) playersI.add(i);
        ArrayList<ArrayList<Integer>> pawnsI = new ArrayList<>();
        int j = 0;
        for (int[] li : mtkj.pawnsI) {
            pawnsI.add(new ArrayList<>());
            for (int i : li) {
                pawnsI.get(j).add(i);
            }
            j++;
        }
        return new ModifierEmbed(mod, playersI, pawnsI, or);
    }

	ModifierConclusion execute(GameState gameState) {
		return modifier.execute(gameState, playersI, pawnsI, orientation);
	}

	public String toJson(HashMap<String, String> pathes) {
        int id = System.identityHashCode(this);
        String path = pathes.get("ModifierEmbed") + "membd_" + modifier.name() + "_" + id + ".json";
        JsonModifierEmbed jme = new JsonModifierEmbed();
        jme.modifierName = modifier.name();
        jme.orientationName = orientation.name();
        jme.playersI = new int[playersI.size()];
        int i = 0;
        for (int pi : playersI) jme.playersI[i++] = pi;
        jme.pawnsI = new int[pawnsI.size()][];
        i = 0;
        for (ArrayList<Integer> li : pawnsI) {
            int j = 0;
            jme.pawnsI[i] = new int[li.size()];
            for (int pi : li) {
                jme.pawnsI[i][j] = pi;
            }
            i++;
        }
        JsonUtils.writeJson(path, jme);
        return path;
    }
}
