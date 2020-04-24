package ZwangCore.Classes;

import Utils.JsonUtils;

import java.util.HashMap;

public class Rules {

    public String name;
    public int maxWeight = 10;
    public int maxAp = 4;
    public int maxClock = 21;
    public int maxPawn = 4;
    public int startPawn = 3;
    public String boardSize = "STANDARD";
    public boolean canDuplicateCard = false;
    public int playerCount = 2;

    public Rules(String name) {
        this.name = name;
    }

    public Rules() {
        this("noName");
    }

    public static Rules fromJson(String jsonPath) {
        return (Rules) JsonUtils.readJson(jsonPath, Rules.class);
    }

    public String toJson(HashMap<String, String> pathes) {
        String path = pathes.get("Rules");

        JsonUtils.writeJson(path, this);
        return path;
    }

    public String toString() {
        String ret = "name: " + name;
        ret += "\nboard size: " + boardSize;
        ret += "\nplayer count: " + playerCount;
        ret += "\nstart pawn count: " + startPawn;
        ret += "\nmax pawn count: " + maxPawn;
        ret += "\naction point: " + maxAp + " per turn";
        ret += "\nmax hand weight: " + maxWeight;
        ret += "\nhaving the same card twice: " + (canDuplicateCard ? "permitted" : "prohibited");
        ret += "\ngame max duration: " + (maxClock / maxAp) + " turns\n";
        return ret;
    }

}
