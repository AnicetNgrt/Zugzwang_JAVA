package ZwangGUI;

import Communication.Command;
import ZwangClient.classes.ZCGameLobby;
import ZwangClient.interfaces.UiLinker;

import javax.swing.*;

public class WindowFrame extends JFrame implements UiLinker, Runnable {

    private ConnectPanel connectPanel;
    private ZwangGuiElement top;

    public WindowFrame() {
        super("Zwang Online - early build");
        start();
    }

    private void start() {
        setVisible(true);
        ZwangGuiElement top = new ZwangGuiElement();
        connectPanel = new ConnectPanel();
        addConnectPanel();
    }

    private void addConnectPanel() {
        this.add(top);
        pack();
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onNameConfirmed(String name) {

    }

    @Override
    public void onHostConfirmed(String gameId) {

    }

    @Override
    public void onConnectionConfirmed() {

    }

    @Override
    public void onRetry(String reason) {

    }

    @Override
    public void onError(String reason) {

    }

    @Override
    public void onJoinConfirmed(String gameId, ZCGameLobby gameLobby) {

    }

    @Override
    public void onPlayerAdded(String pName, boolean isSpec) {

    }

    @Override
    public void onPlayerRemoved(String pName, boolean isSpec) {

    }

    @Override
    public void onLobbyDataReceived(String lobbyId, String lobbyName, boolean hasPassword, boolean inList, int maxPlayerCount, int spectatorsAllowed, int playerCount, int spectatorCount) {

    }

    @Override
    public void onGameStarted() {

    }

    @Override
    public void onPing(String message, int integer) {

    }

    @Override
    public Command lastPending() {
        return null;
    }

    @Override
    public void onIdReceived(String id) {

    }

    @Override
    public void onRequestLobbyPassword() {

    }

    @Override
    public void run() {
    }
}
