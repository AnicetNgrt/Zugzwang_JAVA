package zug.jsonClasses;

import java.util.ArrayList;

public class JsonGameState {
    public int maxClock;
    public String rulesPath;
    public ArrayList<String> playersPaths;
    public ArrayList<String>[] plannedPaths;
    public String boardPath;
    public int clock;
}
