package ZwangClient.classes;

import ZwangCore.Classes.Game;
import ZwangCore.Classes.Rules;

import java.util.concurrent.CopyOnWriteArrayList;

public class ZCGameLobby {
    protected volatile Rules rules = new Rules();
    protected volatile CopyOnWriteArrayList<String> pNames = new CopyOnWriteArrayList<>();
    protected volatile CopyOnWriteArrayList<String> specNames = new CopyOnWriteArrayList<>();
    protected volatile boolean isGameStarted = false;
    protected volatile Game game = null;

    ZCGameLobby() {
    }
}
