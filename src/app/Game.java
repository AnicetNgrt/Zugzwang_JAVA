package app;

import utils.JsonUtils;
import zug.classes.GameState;
import zug.classes.Rules;

import java.util.ArrayList;
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
}
