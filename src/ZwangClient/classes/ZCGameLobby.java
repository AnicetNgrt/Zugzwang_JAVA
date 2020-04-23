package ZwangClient.classes;

import ZwangCore.Classes.Game;
import ZwangCore.Classes.Rules;

import java.util.ArrayList;

public class ZCGameLobby {
    protected Rules rules = new Rules();
    protected ArrayList<String> pNames;
    protected boolean isGameStarted = false;
    protected Game game = null;

    ZCGameLobby() {
    }

    ArrayList<String> getNames() {
        return new ArrayList<>(pNames);
    }

    boolean isGameStarted() {
        return isGameStarted;
    }
}
