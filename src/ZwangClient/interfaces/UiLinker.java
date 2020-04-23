package ZwangClient.interfaces;

import ZwangClient.classes.Bridge;

public interface UiLinker {

    void onDisconnect();

    void onNameConfirmed(String name);

    void onHostConfirm(String gameId);

    void onConnectionConfirm(Bridge bridge);

    void onRetry(String reason);

    void onError(String reason);

    void onJoinConfirmed(String gameId);

    void onReceiveLobbyData(String lobbyName, int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount);

    void onPing(String message, int integer);
}
