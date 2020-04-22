package ZwangGameServer;

import NetworkingClasses.CmdTypes;
import NetworkingClasses.Command;
import ZwangCore.Classes.Game;
import ZwangCore.Classes.GameState;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameLobby {

    private final Object gameLock = new Object();
    private final String id;
    private final String name;
    private final String password;
    private final int maxPlayerCount;
    private final int spectatorsAllowed;
    volatile private CopyOnWriteArrayList<ClientHandler> clients;
    volatile private Game game;
    volatile private boolean isGameStarted;

    GameLobby(String id, ClientHandler owner, String name, int maxPlayerCount, int spectatorsAllowed, String password) {
        this.password = password;
        this.id = id;
        clients = new CopyOnWriteArrayList<>();
        clients.add(owner);
        this.name = name;
        this.maxPlayerCount = Math.max(2, maxPlayerCount);
        this.spectatorsAllowed = Math.max(0, spectatorsAllowed);
    }

    boolean addPlayer(ClientHandler newc) {
        if (isGameStarted) return false;
        if (spectatorsAllowed + maxPlayerCount <= clients.size()) return false;

        Command ca1 = new Command(CmdTypes.ADDPLAYER);
        ca1.set("playerName", newc.getName());
        ca1.set("playerId", newc.getId());
        for (ClientHandler ch : clients) {
            ch.send(ca1);

            Command ca2 = new Command(CmdTypes.ADDPLAYER);
            ca2.set("playerName", ch.getName());
            ca2.set("playerId", ch.getId());
            newc.send(ca2);
        }
        clients.add(newc);
        return true;
    }

    void kickPlayer(ClientHandler c) {
        clients.remove(c);
        Command ca = new Command(CmdTypes.KICKFROMGAME);
        c.send(ca);

        ca = new Command(CmdTypes.REMOVEPLAYER);
        ca.set("playerName", c.getName());
        ca.set("playerId", c.getId());

        for (ClientHandler ch : clients) {
            ch.send(ca);
        }

        if (isGameStarted) {
            synchronized (gameLock) {
                GameState cgs = game.getCurrent();
            }
        }
    }

    void startGame() {
        synchronized (gameLock) {
            isGameStarted = true;
            game = new Game();
        }
    }

    boolean requiresPassword() {
        return !password.equals("");
    }

    String getPassword() {
        return password;
    }

    int playerCount() {
        return Math.min(clients.size(), maxPlayerCount);
    }

    int spectatorCount() {
        return Math.max(0, clients.size() - maxPlayerCount);
    }

    int maxPlayerCount() {
        return maxPlayerCount;
    }

    int maxSpectatorCount() {
        return spectatorsAllowed;
    }

    boolean isIn(ClientHandler c) {
        return clients.contains(c);
    }

    boolean isSpectator(ClientHandler c) {
        return clients.indexOf(c) >= maxPlayerCount;
    }

    boolean isOwner(ClientHandler c) {
        return clients.indexOf(c) == 0;
    }

    boolean isGameStarted() {
        return isGameStarted;
    }
}
