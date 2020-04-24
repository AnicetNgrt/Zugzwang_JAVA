package ZwangClient.classes;

import ZwangClient.interfaces.GameLobbyLinker;
import ZwangCore.Classes.Game;

public class GameLobbyHandler extends ZCGameLobby implements GameLobbyLinker {

    public GameLobbyHandler() {
        super();
    }

    @Override
    public void onPlayerAdded(String pName, boolean isSpec) {
        if (!isSpec) pNames.add(pName);
    }

    @Override
    public void onPlayerRemoved(String pName, boolean isSpec) {
        if (!isSpec) {
            pNames.remove(pName);
            if (isGameStarted) {
                game.disqualify(pName);
            }
        }
    }

    @Override
    public void onLobbyDataReceived(String lobbyId, String lobbyName, boolean hasPassword, boolean inList,
                                    int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount) {
        rules.playerCount = playerCount;
    }

    @Override
    public void onGameStarted() {
        isGameStarted = true;
        game = new Game(pNames, rules);
    }

}