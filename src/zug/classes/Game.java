package zug.classes;

import utils.JsonUtils;
import zug.jsonClasses.JsonGame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Game {
    private LinkedList<GameState> states;
    private ArrayList<String> pNames;
    private Rules rules;

    Game(ArrayList<String> pNames, Rules rules) {
        this.rules = rules;
        this.pNames = pNames;
        this.states = new LinkedList<>();
    }

    static Game fromJson(String jsonPath) {
        JsonUtils<JsonGame> jUtils = new JsonUtils<>(JsonGame.class);
        JsonGame gj = jUtils.readJson(jsonPath);
        Game g = new Game(gj.pNames, Rules.fromJson(gj.rulesPath));
        for (String path : gj.statesPaths)
            g.states.add(GameState.fromJson(path));
        return g;
    }

    void toJson(String name, String assetPath) {
        if (!assetPath.endsWith("\\")) assetPath += "\\";
        if (name.endsWith("\\")) name = name.substring(0, name.length() - 2);

        HashMap<String, String> pathes = new HashMap<>();

        String gamePath = assetPath + name + "\\";
        File gameFolder = new File(gamePath);
        boolean bool = gameFolder.mkdir();

        pathes.put("GameState", gamePath + "gameStates\\");
        pathes.put("Action", gamePath + "actions\\");
        pathes.put("Rules", gamePath + "rules.json");
        pathes.put("Card", gamePath + "cards\\");
        pathes.put("Player", gamePath + "players\\");
        pathes.put("Pawn", gamePath + "pawns\\");
        pathes.put("ModifierToolKit", gamePath + "modifierToolKits\\");

        for (String key : pathes.keySet()) {
            File folder = new File(pathes.get(key));
            bool = folder.mkdir() && bool;
        }

        if (bool) {
            JsonUtils<JsonGame> jUtils = new JsonUtils<>(JsonGame.class);
            JsonGame jg = new JsonGame();
            jg.pNames = pNames;
            jg.rulesPath = rules.toJson(pathes);
            jg.statesPaths = new ArrayList<>();
            for (GameState gs : states)
                jg.statesPaths.add(gs.toJson(pathes));

            jUtils.writeJson(gamePath + name + ".json", jg);
        }
    }
}
