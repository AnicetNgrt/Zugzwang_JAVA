package ZwangCore.Enums;

import ZwangCore.Classes.Action;
import ZwangCore.Classes.Card;
import ZwangCore.Classes.Player;

import java.util.ArrayList;

public enum CardTypes {
    SMALL_RIVERS("Small rivers",
            0,
            true,
            true,
            999,
            999,
            new Action(Modifiers.M1, 1)),
    KNIGHT("Knight",
            8,
            false,
            true,
            999,
            2,
            new Action(Modifiers.MCavLeft, 1),
            new Action(Modifiers.MCavRight, 1)),
    CLOCK_MAKER("Clock maker",
            7,
            false,
            true,
            999,
            2,
            new Action(Modifiers.MClockLeft, 1),
            new Action(Modifiers.MClockRight, 1)),
    CONTACT("Contact",
            0,
            false,
            true,
            999,
            999,
            new Action(Modifiers.Contact, 1)),
    ARCHER("Archer",
            3,
            false,
            false,
            999,
            999,
            new Action(Modifiers.Bow, 1),
            new Action(Modifiers.Bandage, 2));

    private String name;
    private Action[] actions;
    private int weight;
    private boolean oriented;
    private boolean shown;
    private int maxGame;
    private int maxTurn;

    CardTypes(String name, int weight, boolean oriented, boolean shown, int maxGame, int maxTurn, Action... actions) {
        this.name = name;
        this.weight = weight;
        this.oriented = oriented;
        this.shown = shown;
        this.actions = actions;
    }

    public int weight() {
        return weight;
    }

    boolean oriented() {
        return oriented;
    }

    String trueName() {
        return name;
    }

    public int maxTurn() {
        return maxTurn;
    }

    public int maxGame() {
        return maxGame;
    }

    public boolean shown() {
        return shown;
    }

    public Card getOne(Cardinal orientation, Player owner) {
        ArrayList<Action> cardActions = new ArrayList<>();
        for (Action a : actions) {
            cardActions.add(new Action(a));
        }
        return new Card(this, owner, cardActions, orientation);
    }
}
