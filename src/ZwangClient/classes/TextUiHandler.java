package ZwangClient.classes;

import Utils.TUI;
import ZwangClient.interfaces.GameLobbyLinker;
import ZwangClient.interfaces.UiLinker;

public class TextUiHandler implements UiLinker, GameLobbyLinker {

    ZCGameLobby gl;
    Bridge bridge = null;

    public TextUiHandler() {
        TUI.println("Attempting connection ...");
    }

    void mainMenu() {
        if (bridge == null) return;

        String out = "-----MENU-----\n";
        out += "1 - host\n";
        out += "2 - join\n";
        TUI.choiceList(out, "1", "2");
    }

    @Override
    public void onPlayerAdded(String pName, boolean isSpec) {

    }

    @Override
    public void onPlayerRemoved(String pName, boolean isSpec) {

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
    public void onConnectionConfirm(Bridge bridge) {
        TUI.println("You just connected to the ZWANG server !");
        this.bridge = bridge;
    }

    @Override
    public void onRetry(String reason) {
        TUI.println("WARNING server asks to retry: " + reason);
    }

    @Override
    public void onError(String reason) {
        TUI.println("ERROR from server: " + reason);
    }

    @Override
    public void onJoinConfirmed(String gameId) {
        TUI.println("You just joined a lobby");
    }

    @Override
    public void onReceiveLobbyData(String lobbyName, int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount) {
        TUI.print("Currently in lobby " + lobbyName + "\n");
        TUI.print("p: " + playerCount + "/" + maxPlayerCount + "\n");
        TUI.println("s: " + spectatorCount + "/" + spectatorsAllowed);
    }

    @Override
    public void onStartGame() {

    }

    @Override
    public void onPing(String message, int integer) {
        TUI.println("Poke received: " + message + " " + integer);
    }
}
