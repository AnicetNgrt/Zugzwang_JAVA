package zug.classes;

import zug.enums.ActionEndReason;

public class ModifierConclusion {
    GameState before;
    ActionEndReason endReason;
    String desc;
    GameState after;

    public ModifierConclusion(ActionEndReason endReason, String desc, GameState before, GameState after) {
        this.endReason = endReason;
        this.desc = desc;
        this.before = before;
        this.after = after;
    }

    public String toString() {
        return desc;
    }
}
