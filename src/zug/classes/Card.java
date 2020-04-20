package zug.classes;

import utils.JsonUtils;
import zug.enums.ActionEndReason;
import zug.enums.CardTypes;
import zug.enums.Cardinal;
import zug.jsonClasses.JsonCard;

import java.util.ArrayList;
import java.util.HashMap;

public class Card {
    private CardTypes type;
    private ArrayList<Action> actions;
    private Cardinal orientation;
    private Player owner;
    private boolean shown;
    private int playedTurn;
    private int playedGame;

    public Card(CardTypes type, Player owner, ArrayList<Action> actions, Cardinal orientation) {
        this.type = type;
        this.actions = actions;
        this.owner = owner;
        this.orientation = orientation;
        this.shown = type.shown();
        playedGame = 0;
        playedTurn = 0;
    }

    Card(Card c, Player ownerCopy) {
        this(c.type, ownerCopy, c.actions, c.orientation);
        playedGame = c.playedGame;
        playedTurn = c.playedTurn;
        shown = c.shown;
    }

    public static Card fromJson(String jsonPath, Player owner) {
        JsonUtils<JsonCard> jUtils = new JsonUtils<>(JsonCard.class);
        JsonCard cj = jUtils.readJson(jsonPath);

        Cardinal orientation = Cardinal.valueOf(cj.orientationName);
        CardTypes type = CardTypes.valueOf(cj.typeName);
        Card c = new Card(type, owner, new ArrayList<>(), orientation);
        for (String path : cj.actionsPaths)
            c.actions.add(Action.fromJson(path));

        c.shown = cj.shown;
        c.playedTurn = cj.playedTurn;
        c.playedGame = cj.playedGame;
        return c;
    }

    CardTypes type() {
        return type;
    }

    void rotate(Cardinal orientation) {
        this.orientation = orientation;
    }

    void reveal() {
        shown = true;
    }

    boolean shown() {
        return shown;
    }

    int playedTurn() {
        return playedTurn;
    }

    int playedGame() {
        return playedGame;
    }

    ArrayList<Action> actions() {
        return new ArrayList<>(actions);
    }


    ModifierConclusion play(int i, GameState g, ArrayList<Integer> targetsI, ArrayList<ArrayList<Integer>> pawnsI) {

        String cclStr = "Player " + owner.name() + " attempts action index " + i + " on card type " + type.name();
        if (i < 0 || i >= actions.size()) {
            ActionEndReason er = ActionEndReason.INDEX_OUT_OF_RANGE;
            return new ModifierConclusion(er, cclStr + " FAILS " + er.name(), g, g);
        }

        cclStr = "Player " + owner.name() + " attempts action " + actions.get(i).modifier().name() + " on card type " + type.name();
        Action action = actions.get(i);
        if (owner.ap() < action.cost()) {
            ActionEndReason er = ActionEndReason.TOO_EXPENSIVE;
            return new ModifierConclusion(er, cclStr + " FAILS " + er.name(), g, g);
        }
        if (playedTurn >= type.maxTurn()) {
            ActionEndReason er = ActionEndReason.MAX_TURN;
            return new ModifierConclusion(er, cclStr + " FAILS " + er.name(), g, g);
        }
        if (playedGame >= type.maxGame()) {
            ActionEndReason er = ActionEndReason.MAX_GAME;
            return new ModifierConclusion(er, cclStr + " FAILS " + er.name(), g, g);
        }

        ArrayList<Integer> playersI = new ArrayList<>();
        playersI.add(g.indexOfPlayer(owner));
        playersI.addAll(targetsI);

        ModifierConclusion mc = action.play(g, playersI, pawnsI, orientation);
        if (mc.endReason == ActionEndReason.SUCCESS) reveal();
        return mc;
    }

    void turnReset() {
        playedTurn = 0;
    }

    public String toJson(HashMap<String, String> pathes) {
        String path = pathes.get(this.getClass().getName()) + "card|" + type.name() + "|" + owner.name() + ".json";
        JsonUtils<JsonCard> jUtils = new JsonUtils<>(JsonCard.class);
        JsonCard jca = new JsonCard();
        jca.orientationName = orientation.name();
        jca.playedGame = playedGame;
        jca.playedTurn = playedTurn;
        jca.shown = shown;
        jca.typeName = type.name();
        jca.actionsPaths = new ArrayList<>();
        for (Action a : actions) {
            jca.actionsPaths.add(a.toJson(pathes));
        }
        jUtils.writeJson(path, jca);
        return path;
    }
}
