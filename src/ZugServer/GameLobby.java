package ZugServer;

import ZugCore.Classes.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class GameLobby {
    private ArrayList<Client> clients;
    private Game game;
    private String id;
    private String name;
    private int playerCount;
    private int spectatorsAllowed;
    private HashMap<String, ArrayList<String>> teams;

    GameLobby(String id, Client owner, String name, int playerCount, int spectatorsAllowed) {

    }
}
