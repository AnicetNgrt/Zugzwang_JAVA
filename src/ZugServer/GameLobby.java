package ZugServer;

import ZugCore.Classes.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class GameLobby {
    private ArrayList<Client> clients;
    private Game game;
    private String id;
    private String name;
    private int maxPlayerCount;
    private int spectatorsAllowed;
    private HashMap<String, ArrayList<String>> teams;

    GameLobby(String id, Client owner, String name, int maxPlayerCount, int spectatorsAllowed) {
        this.id = id;
        clients = new ArrayList<>();
        clients.add(owner);
        this.name = name;
        this.maxPlayerCount = maxPlayerCount;
        this.spectatorsAllowed = spectatorsAllowed;
        teams = new HashMap<>();
    }

    boolean addPlayer(Client c) {
        if (spectatorsAllowed + maxPlayerCount <= clients.size()) return false;
        clients.add(c);
        return true;
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

    void kickPlayer(Client c) {
        clients.remove(c);
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
}
