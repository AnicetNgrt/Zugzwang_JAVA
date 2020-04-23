package ZwangClient.interfaces;

public interface GameLobbyLinker {
    void onPlayerAdded(String pName, boolean isSpec);

    void onPlayerRemoved(String pName, boolean isSpec);

    void onReceiveLobbyData(String lobbyName, int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount);

    void onStartGame();
}
