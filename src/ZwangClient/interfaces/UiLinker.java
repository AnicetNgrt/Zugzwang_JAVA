package ZwangClient.interfaces;

public interface UiLinker {

    void onDisconnect();

    void onNameConfirmed(String name);

    void onHostConfirm(String gameId);

    void onConnectionConfirm();

    void onRetry(String reason);

    void onError(String reason);

    void onJoinConfirmed(String gameId);

    void onReceiveLobbyData(String lobbyName, int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount);

    void onPing(String message, int integer);
}
