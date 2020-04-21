package ZugServer;

import java.util.HashMap;

public class SharedData {
    static volatile HashMap<String, GameLobby> lobbies = new HashMap<>();
    static volatile HashMap<String, Client> users = new HashMap<>();
}
