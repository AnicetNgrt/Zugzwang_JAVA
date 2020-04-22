package ZugCore.Classes;

import Utils.JsonUtils;
import ZugCore.JsonClasses.JsonGame;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Game {
    private LinkedList<GameState> states;
    private ArrayList<String> pNames;
    private Rules rules;
    private int mainIndex;

    public Game(ArrayList<String> pNames, Rules rules) {
        this.rules = rules;
        int lastI = Math.min(rules.playerCount, pNames.size());
        this.pNames = (ArrayList<String>) pNames.subList(0, lastI);
        this.states = new LinkedList<>();

        ArrayList<Player> players = new ArrayList<>();
        for (String name : pNames) {
            players.add(new Player(name, rules));
        }
        Board.Sizes size = Board.Sizes.valueOf(rules.boardSize);
        GameState ini = new GameState(players, rules, new Board(size));

        states.add(ini);
        mainIndex = 0;
    }

    public Game() {
    }

    public static Game fromJson(String jsonPath) {
        JsonGame gj = (JsonGame) JsonUtils.readJson(jsonPath, JsonGame.class);
        Game g = new Game();
        g.states = new LinkedList<>();
        g.pNames = new ArrayList<>();
        assert gj != null;
        g.rules = Rules.fromJson(gj.rulesPath);
        g.mainIndex = gj.mainIndex;
        Collections.addAll(g.pNames, gj.pNames);
        for (String path : gj.statesPaths)
            g.states.add(GameState.fromJson(path));

        return g;
    }

    public void disqualify(String pName) {
        GameState gs = getCurrent();
        Player p = gs.getPlayer(pName);
        for (int i = 0; i < rules.maxPawn; i++) {
            Pawn pa = p.getPawn(i);
            pa.kill(gs.board());
        }
    }

    public boolean updateState(GameState gs) {
        int i = commitState(gs);
        return pushState(i);
    }

    public int commitState(GameState gs) {
        states.add(gs);
        return states.indexOf(gs);
    }

    public boolean pushState(int index) {
        if (index < 0 || index >= states.size()) return false;
        if (states.get(index) == null) return false;
        mainIndex = index;
        return true;
    }

    public GameState getCurrent() {
        return states.get(mainIndex);
    }

    public String toJson(String name, String assetPath) {
        if (!assetPath.endsWith("\\")) assetPath += "\\";
        if (name.endsWith("\\")) name = name.substring(0, name.length() - 2);

        HashMap<String, String> pathes = new HashMap<>();

        String gamePath = assetPath + name + "\\";
        System.out.println(assetPath + name + "\\");
        File gameFolder = new File(gamePath);
        boolean bool = true;
        if (!Files.exists(Path.of(gamePath))) {
            bool = gameFolder.mkdir();
        }

        pathes.put("GameState", gamePath + "gameStates\\");
        pathes.put("Action", gamePath + "actions\\");
        pathes.put("Card", gamePath + "cards\\");
        pathes.put("Board", gamePath + "boards\\");
        pathes.put("Player", gamePath + "players\\");
        pathes.put("Pawn", gamePath + "pawns\\");
        pathes.put("ModifierEmbed", gamePath + "modifierEmbeds\\");

        for (String key : pathes.keySet()) {
            File folder = new File(pathes.get(key));
            if (!Files.exists(Path.of(pathes.get(key)))) {
                bool = folder.mkdir() && bool;
            }
        }

        System.out.println(bool);
        pathes.put("Rules", gamePath + "rules_" + rules.name + ".json");

        if (bool) {
            JsonGame jg = new JsonGame();
            jg.pNames = new String[pNames.size()];
            int i = 0;
            for (String s : pNames)
                jg.pNames[i++] = s;
            jg.rulesPath = rules.toJson(pathes);
            jg.statesPaths = new String[states.size()];
            jg.mainIndex = mainIndex;
            i = 0;
            for (GameState gs : states)
                jg.statesPaths[i++] = gs.toJson(pathes);

            JsonUtils.writeJson(gamePath + name + ".json", jg);
            return gamePath;
        }

        return "Failure";
    }
}
