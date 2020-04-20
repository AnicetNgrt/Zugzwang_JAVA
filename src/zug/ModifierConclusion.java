package zug;

class ModifierConclusion {
    Game before;
    ActionEndReason endReason;
    String desc;
    Game after;

    ModifierConclusion(ActionEndReason endReason, String desc, Game before, Game after) {
        this.endReason = endReason;
        this.desc = desc;
    }

    public String toString() {
        return desc;
    }
}
