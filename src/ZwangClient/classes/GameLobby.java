package ZwangClient.classes;

import ZwangClient.interfaces.GameLinker;
import ZwangCore.Classes.Rules;

public class GameLobby implements GameLinker {

    Rules rules = new Rules();

    public GameLobby() {

    }

    @Override
    public void onPlayerAdded(String pName) {

    }

    @Override
    public void onPlayerRemoved(String pName) {

    }

    @Override
    public void onReceiveLobbyData(String lobbyName, int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount) {
        rules.playerCount = playerCount;
    }
}
