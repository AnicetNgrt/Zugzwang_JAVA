package ZwangClient.classes;

import Communication.CmdTypes;
import Communication.Command;
import Utils.TUI;
import ZwangClient.interfaces.UiLinker;

import java.util.concurrent.SynchronousQueue;

public class TextUiHandler implements UiLinker, Runnable {

    private volatile SynchronousQueue<Command> pending;
    private volatile int expectedLobbyCount = 0;
    private volatile int lobbyReceived = 0;
    private volatile ZCGameLobby gameLobby = null;
    private volatile boolean stop = false;

    public TextUiHandler() {
        pending = new SynchronousQueue<>();
        TUI.println("Attempting connection ...");
    }

    @Override
    public void run() {
        while (!stop) {
            Command cmd = null;
            while (cmd == null) {
                String line = TUI.readline();
                boolean isOfflineCmd = handleOfflineCommands(line);
                if (!isOfflineCmd) {
                    cmd = TUI.promptCmd(line);
                    handleOnlineCommands(cmd);
                }
            }
            try {
                pending.put(cmd);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleOnlineCommands(Command cmd) {
        if (cmd == null) return;
        switch (cmd.type) {
            case REQUESTLOBBIES:
                expectedLobbyCount = cmd.getInt("count");
        }
    }

    boolean handleOfflineCommands(String line) {
        line = line.trim();
        if (line.equals("help")) {
            StringBuilder hs = new StringBuilder("\n----HELP---\n");
            hs.append("\nClient commands: \n");
            for (CmdTypes t : CmdTypes.clientCmds)
                hs.append(" - ").append(t.toString()).append("\n");
            hs.append("\nLobby commands: \n");
            for (CmdTypes t : CmdTypes.lobbyCmds)
                hs.append(" - ").append(t.toString()).append("\n");
            hs.append("\nIn-game commands: \n");
            for (CmdTypes t : CmdTypes.inGameCmds)
                hs.append(" - ").append(t.toString()).append("\n");
            TUI.println(hs.toString());
            return true;
        }
        if (line.equals("rfr") && gameLobby != null) {
            if (gameLobby.isGameStarted) {
                showGame();
            } else {
                showLobby();
            }
            return true;
        }
        return false;
    }

    void showLobby() {
        StringBuilder out = new StringBuilder("-----LOBBY-----\nPlayers: \n");
        for (String name : gameLobby.pNames) {
            out.append(" - ").append(name).append("\n");
        }
        out.append("Spectators: \n");
        for (String name : gameLobby.specNames) {
            out.append(" - ").append(name).append("\n");
        }
        out.append("Rules: \n");
        out.append(gameLobby.rules.toString());
        TUI.println(out.toString());
    }

    void showGame() {
        TUI.println("nothing to show");
    }

    @Override
    public void onPlayerAdded(String pName, boolean isSpec) {

    }

    @Override
    public void onPlayerRemoved(String pName, boolean isSpec) {

    }

    @Override
    public void onDisconnected() {
        stop = true;
    }

    @Override
    public void onNameConfirmed(String name) {
        TUI.println("server: Your name is " + name);
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
    public void onJoinConfirmed(String gameId, ZCGameLobby gameLobby) {
        TUI.println("server: You just joined a lobby (id: " + gameId + ")");
        this.gameLobby = gameLobby;
    }

    @Override
    synchronized public void onLobbyDataReceived(String lobbyId, String lobbyName, boolean hasPassword, boolean inList,
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
        TUI.print("server: Online game has been started by host");
    }

    @Override
    public void onPing(String message, int integer) {
        TUI.println("server: Poke received: " + message + " " + integer);
    }

    @Override
    public Command lastPending() {
        try {
            return pending.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onIdReceived(String id) {
        TUI.println("server: Your id is: " + id);
    }
}
