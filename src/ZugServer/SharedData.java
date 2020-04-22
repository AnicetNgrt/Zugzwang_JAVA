package ZugServer;

import java.util.concurrent.ConcurrentHashMap;

public class SharedData {
    public static volatile ConcurrentHashMap<String, GameLobby> lobbies = new ConcurrentHashMap<>();
    public static volatile ConcurrentHashMap<String, Client> users = new ConcurrentHashMap<>();
}
