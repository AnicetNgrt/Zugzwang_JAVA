package ZugServer;

import ZugCore.Classes.Game;
import ZugCore.Classes.GameState;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameLobby {

    private final Object gameLock = new Object();
    private final String id;
    private final String name;
    private final int maxPlayerCount;
    private final int spectatorsAllowed;
    volatile private CopyOnWriteArrayList<Client> clients;
    volatile private Game game;
    volatile private boolean isGameStarted;

    GameLobby(String id, Client owner, String name, int maxPlayerCount, int spectatorsAllowed) {
        this.id = id;
        clients = new CopyOnWriteArrayList<>();
        clients.add(owner);
        this.name = name;
        this.maxPlayerCount = maxPlayerCount;
        this.spectatorsAllowed = spectatorsAllowed;
    }

    boolean addPlayer(Client c) {
        if (isGameStarted) return false;
        if (spectatorsAllowed + maxPlayerCount <= clients.size()) return false;
        clients.add(c);
        return true;
    }

    void kickPlayer(Client c) {
        clients.remove(c);
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

    boolean isIn(Client c) {
        return clients.contains(c);
    }

    boolean isSpectator(Client c) {
        return clients.indexOf(c) >= maxPlayerCount;
    }

    boolean isOwner(Client c) {
        return clients.indexOf(c) == 0;
    }

    boolean isGameStarted() {
        return isGameStarted;
    }
}
