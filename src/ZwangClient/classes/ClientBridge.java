package ZwangClient.classes;

import Communication.CmdTypes;
import Communication.Command;
import Communication.Communicator;
import Utils.NetworkUtils;
import ZwangClient.interfaces.UiLinker;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

public class ClientBridge extends Communicator implements Runnable {

    private String id;
    private String name = "anonymous";
    private boolean inGame = false;
    private String lobbyId = null;
    private GameLobbyHandler lobbyHandler;
    private UiLinker[] uiLinkers = {};

    public ClientBridge(String host, int port) throws IOException {
        super(new Socket(host, port));
    }

    public ClientBridge(String host, int port, UiLinker[] extensions) throws IOException {
        this(host, port);
        this.uiLinkers = extensions;
    }

    public void run() {
        boolean stop = false;
        while (!stop) {
            LinkedList<Command> cmds = read();
            while (!cmds.isEmpty()) {
                Command cmd = cmds.removeFirst();
                stop = handleCommand(cmd);
                if (stop) break;
            }
            for (UiLinker ui : uiLinkers) {
                Command cmd = ui.lastPending();
                if (cmd != null) send(cmd);
            }
        }
        closeConnection();
    }

    private boolean handleCommand(Command cmd) {
        if (cmd == null) return false;
        boolean stop = false;
        Command ca;
        switch (cmd.type) {
            case ADDPLAYER:
                if (!inGame) break;
                String name = cmd.getStr("nameInGame");
                boolean isSpec = NetworkUtils.intToBool(cmd.getInt("isSpectator"));
                for (UiLinker ui : uiLinkers) ui.onPlayerAdded(name, isSpec);
                lobbyHandler.onPlayerAdded(name, isSpec);
                break;

            case REMOVEPLAYER:
                if (!inGame) break;
                name = cmd.getStr("name");
                isSpec = NetworkUtils.intToBool(cmd.getInt("isSpectator"));
                for (UiLinker ui : uiLinkers) ui.onPlayerRemoved(name, isSpec);
                lobbyHandler.onPlayerRemoved(name, isSpec);
                break;

            case RECEIVELOBBYDATA:
                String gameId = cmd.getStr("gameId");
                String gameName = cmd.getStr("gameName");
                boolean hasPassword = NetworkUtils.intToBool(cmd.getInt("hasPassword"));
                boolean partOfList = NetworkUtils.intToBool(cmd.getInt("partOfList"));
                int maxPlayerCount = cmd.getInt("maxPlayerCount");
                int spectatorsAllowed = cmd.getInt("spectatorsAllowed");
                int playerCount = cmd.getInt("playerCount");
                int spectatorCount = cmd.getInt("spectatorCount");
                for (UiLinker ui : uiLinkers)
                    ui.onLobbyDataReceived(gameId, gameName, hasPassword, partOfList, maxPlayerCount, spectatorsAllowed, playerCount, spectatorCount);
                if (inGame && !partOfList) {
                    lobbyHandler.onLobbyDataReceived(gameId, gameName, hasPassword, partOfList, maxPlayerCount, spectatorsAllowed, playerCount, spectatorCount);
                }
                break;

            case STARTGAME:
                if (!inGame) break;
                for (UiLinker ui : uiLinkers) ui.onGameStarted();
                lobbyHandler.onGameStarted();
                break;

            case RECEIVEID:
                String id = cmd.getStr("id");
                this.id = id;
                for (UiLinker ui : uiLinkers) ui.onIdReceived(id);
                break;

            case DISCONNECT:
                stop = true;
                inGame = false;
                lobbyHandler = null;
                break;

            case JOIN:
                if (inGame) break;
                inGame = true;
                gameId = cmd.getStr("gameId");
                lobbyHandler = new GameLobbyHandler();
                for (UiLinker ui : uiLinkers) ui.onJoinConfirmed(gameId, lobbyHandler);
                ca = new Command(CmdTypes.SENDALLPLAYERS);
                send(ca);
                break;

            case PING:
                String message = cmd.getStr("message");
                int integer = cmd.getInt("integer");
                for (UiLinker ui : uiLinkers) ui.onPing(message, integer);
                break;

            case MYNAMEIS:
                this.name = cmd.getStr("name");
                for (UiLinker ui : uiLinkers) ui.onNameConfirmed(this.name);
                break;

            case CONNECTIONCONFIRM:
                for (UiLinker ui : uiLinkers) ui.onConnectionConfirmed();
                break;

            case ERROR:
                String reason = cmd.getStr("reason");
                for (UiLinker ui : uiLinkers) ui.onError(reason);
                break;

            case REQUESTLOBBYPASSWORD:
                for (UiLinker ui : uiLinkers) ui.onRequestLobbyPassword();
                break;

            case RETRY:
                reason = cmd.getStr("reason");
                for (UiLinker ui : uiLinkers) ui.onRetry(reason);
                break;
        }
        return stop;
    }
}
