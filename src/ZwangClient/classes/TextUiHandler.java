package ZwangClient.classes;

import ZwangClient.interfaces.GameLinker;
import ZwangClient.interfaces.GeneralLinker;

public class TextUiHandler implements GeneralLinker, GameLinker {

    @Override
    public void onPlayerAdded(String pName) {

    }

    @Override
    public void onPlayerRemoved(String pName) {

    }

    @Override
    public void onDisconnect() {

    }

    @Override
    public void onNameConfirmed(String name) {

    }

    @Override
    public void onHostConfirm(String gameId) {

    }

    @Override
    public void onConnectionConfirm() {

    }

    @Override
    public void onRetry(String reason) {

    }

    @Override
    public void onError(String reason) {

    }

    @Override
    public void onJoinConfirmed(String gameId) {
        System.out.println("You just joined a lobby");
    }

    @Override
    public void onReceiveLobbyData(String lobbyName, int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount) {
        System.out.print("Currently in lobby " + lobbyName + "\n");
        System.out.print("p: " + playerCount + "/" + maxPlayerCount + "\n");
        System.out.println("s: " + spectatorCount + "/" + spectatorsAllowed);
    }

    @Override
    public void onPing(String message, int integer) {
        System.out.println("Poke received: " + message + " " + integer);
    }
}
