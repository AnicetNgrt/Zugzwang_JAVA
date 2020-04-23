package ZwangClient.interfaces;

public interface GameLinker {
    void onPlayerAdded(String pName);

    void onPlayerRemoved(String pName);

    void onReceiveLobbyData(String lobbyName, int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount);
}
