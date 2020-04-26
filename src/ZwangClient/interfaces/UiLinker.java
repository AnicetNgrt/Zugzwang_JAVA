package ZwangClient.interfaces;

import Communication.Command;
import ZwangClient.classes.ZCGameLobby;

public interface UiLinker extends GameLobbyLinker {

    void onDisconnected();

    void onNameConfirmed(String name);

    void onHostConfirmed(String gameId);

    void onConnectionConfirmed();

    void onRetry(String reason);

    void onError(String reason);

    void onJoinConfirmed(String gameId, ZCGameLobby gameLobby);

    void onLobbyDataReceived(String lobbyId, String lobbyName, boolean hasPassword, boolean inList, int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount);

    void onPing(String message, int integer);

    Command lastPending();

    void onIdReceived(String id);

    void onRequestLobbyPassword();
}
