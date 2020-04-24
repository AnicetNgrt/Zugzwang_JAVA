package ZwangClient.classes;

import Communication.Command;
import Utils.TUI;
import ZwangClient.interfaces.UiLinker;

import java.util.concurrent.SynchronousQueue;

public class TextUiHandler implements UiLinker, Runnable {

    SynchronousQueue<Command> pending = null;
    int expectedLobbyCount = 0;
    int lobbyReceived = 0;
    boolean stop = false;

    public TextUiHandler() {
        pending = new SynchronousQueue<>();
        TUI.println("Attempting connection ...");
    }

    @Override
    public void run() {
        while (!stop) {
            Command cmd = null;
            while (cmd == null) {
                cmd = TUI.promptCmd(TUI.readline());
            }
            try {
                pending.put(cmd);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void mainMenu() {
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
    public void onDisconnected() {

    }

    @Override
    public void onNameConfirmed(String name) {

    }

    @Override
    public void onHostConfirmed(String gameId) {

    }

    @Override
    public void onConnectionConfirmed() {
        TUI.println("server: You just connected to the ZWANG server !");
    }

    @Override
    public void onRetry(String reason) {
        TUI.println("server: WARNING retry: " + reason);
    }

    @Override
    public void onError(String reason) {
        TUI.println("server: ERROR: " + reason);
    }

    @Override
    public void onJoinConfirmed(String gameId) {
        TUI.println("server: You just joined a lobby");
    }

    @Override
    public void onLobbyDataReceived(String lobbyId, String lobbyName, boolean hasPassword, boolean inList,
                                    int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount) {
        if (!inList) {
            TUI.println("server: Currently in lobby " + lobbyId + " | " + lobbyName);
            TUI.print("\t p: " + playerCount + "/" + maxPlayerCount);
            TUI.println(" | s: " + spectatorCount + "/" + spectatorsAllowed + "\n");
        } else {
            if (lobbyReceived == 0) TUI.println("server: LOBBY LIST LOADING ...");
            lobbyReceived++;
            TUI.print(lobbyId + " | " + lobbyName + " | ");
            TUI.print(hasPassword ? "password" : "public");
            TUI.print(" | p: " + playerCount + "/" + maxPlayerCount);
            TUI.print(" | s: " + spectatorCount + "/" + spectatorsAllowed + "\n");
            if (lobbyReceived == expectedLobbyCount) lobbyReceived = 0;
        }
    }

    @Override
    public void onGameStarted() {

    }

    @Override
    public void onPing(String message, int integer) {
        TUI.println("Poke received: " + message + " " + integer);
    }

    @Override
    public Command lastPending() {
        return pending.poll();
    }
}
