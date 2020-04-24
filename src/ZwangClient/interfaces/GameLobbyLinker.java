package ZwangClient.interfaces;

public interface GameLobbyLinker {
    void onPlayerAdded(String pName, boolean isSpec);

    void onPlayerRemoved(String pName, boolean isSpec);

    void onLobbyDataReceived(String lobbyId, String lobbyName, boolean hasPassword, boolean inList, int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount);

    void onGameStarted();
}
