package ZwangGameServer;

import java.util.concurrent.ConcurrentHashMap;

public class SharedData {
    public static volatile ConcurrentHashMap<String, ZGSGameLobby> lobbies = new ConcurrentHashMap<>();
    public static volatile ConcurrentHashMap<String, ClientHandler> users = new ConcurrentHashMap<>();
}
