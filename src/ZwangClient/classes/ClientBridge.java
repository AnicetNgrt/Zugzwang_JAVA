package ZwangClient.classes;

import Communication.Command;
import Communication.Communicator;
import Utils.NetworkUtils;
import ZwangClient.interfaces.UiLinker;

import java.io.IOException;
import java.net.Socket;

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
            Command cmd = read();
            stop = handleCommand(cmd);
            for (UiLinker ui : uiLinkers) {
                cmd = ui.lastPending();
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
            case DISCONNECT:
                stop = true;
                inGame = false;
                String name = cmd.getStr("name");
                boolean isSpec = NetworkUtils.intToBool(cmd.getInt("isSpectator"));
                for (UiLinker ui : uiLinkers) ui.onPlayerAdded(name, isSpec);
                lobbyHandler = null;
                break;

            case JOIN:
                if (inGame) break;
                inGame = true;
                lobbyHandler = new GameLobbyHandler();
                break;

            case PING:
                String message = cmd.getStr("message");
                int integer = cmd.getInt("integer");
                for (UiLinker ui : uiLinkers) ui.onPing(message, integer);
                break;

            case MYNAMEIS:
                for (UiLinker ui : uiLinkers) ui.onNameConfirmed(cmd.getStr("name"));

            case CONNECTIONCONFIRM:
                for (UiLinker ui : uiLinkers) ui.onConnectionConfirmed();
        }
        return stop;
    }
}
