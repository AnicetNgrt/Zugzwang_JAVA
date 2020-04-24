package ZwangGameServer;

import Communication.CmdTypes;
import Communication.Command;
import Communication.Communicator;
import Utils.NetworkUtils;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler extends Communicator implements Runnable {

    private static final String DEFAULT_NAME = "anon";

    private InetSocketAddress remote;
    private final String id;
    private volatile String name = DEFAULT_NAME;
    private volatile boolean inGame = false;
    private volatile String lobbyId = null;
    private ZGSGameLobby lobbyWaitingForPswd = null;

    public ClientHandler(Socket pSock, String id) {
        super(pSock);
        this.id = id;
    }

    public void run() {
        System.err.println("Lancement du traitement de la connexion cliente");

        Command ca = new Command(CmdTypes.CONNECTIONCONFIRM);
        send(ca);

        while (!getSock().isClosed()) {
            Command cmd = read();
            if (cmd == null) break;

            remote = (InetSocketAddress) getSock().getRemoteSocketAddress();

            System.err.println("\n" + debugStr(cmd));
            boolean stop = handleCommand(cmd);

            if (stop) {
                closeConnection();
                break;
            }
        }

        inGame = false;
        lobbyId = null;
        SharedData.users.remove(id);
    }

    private boolean handleCommand(Command cmd) {
        boolean stop = false;
        if (cmd.isNull()) {
            sendRetry("wrong format");
            return false;
        } else if (cmd.isMissing()) {
            sendRetry("missing argument");
            return false;
        }

        Command ca;
        ZGSGameLobby gl;

        switch (cmd.type) {
            case DISCONNECT:
                if (inGame) {
                    gl = SharedData.lobbies.get(this.lobbyId);
                    gl.kickPlayer(this.getNameInLobby());
                }
                send(cmd);
                stop = true;
                break;

            case HOST:
                if (isInGame(false)) sendError("can't host a new game while in a game");
                String lobbyId = NetworkUtils.getAlphaNumericString(6);
                String gameName = cmd.getStr("gameName");
                int mpc = cmd.getInt("maxPlayerCount");
                int spc = cmd.getInt("spectatorsAllowed");
                String psw = cmd.getStr("password");
                gl = new ZGSGameLobby(lobbyId, this, gameName, mpc, spc, psw);
                SharedData.lobbies.put(lobbyId, gl);
                setGameAsJoined(lobbyId);
                break;

            case JOIN:
                if (isInGame(false)) sendError("can't join a game while in a game");
                lobbyId = cmd.getStr("gameId");
                final ConcurrentHashMap<String, ZGSGameLobby> lobbies = SharedData.lobbies;
                if (lobbies.containsKey(lobbyId)) {
                    ZGSGameLobby lobby = lobbies.get(lobbyId);
                    if (lobby.getPassword().length() > 0) {
                        ca = new Command(CmdTypes.REQUESTLOBBYPASSWORD);
                        send(ca);
                        lobbyWaitingForPswd = lobby;
                    } else {
                        boolean canJoin = lobby.addPlayer(this);
                        if (canJoin) {
                            setGameAsJoined(lobbyId);
                        } else {
                            sendError("can't join this game");
                        }
                    }
                } else {
                    sendRetry("can't find game with such id");
                }
                break;

            case GIVELOBBYPASSWORD:
                if (isInGame(false)) sendError("can't join a game while in a game");
                if (lobbyWaitingForPswd == null) break;
                String password = cmd.getStr("password");
                if (password.equals(lobbyWaitingForPswd.getPassword())) {
                    boolean canJoin = lobbyWaitingForPswd.addPlayer(this);
                    if (canJoin) {
                        setGameAsJoined(lobbyWaitingForPswd.getId());
                        lobbyWaitingForPswd = null;
                    } else {
                        sendError("can't join this game");
                    }
                } else {
                    sendRetry("wrong password for lobby id: " + lobbyWaitingForPswd.getId());
                }
                break;

            case MYNAMEIS:
                if (name.equals(DEFAULT_NAME))
                    name = cmd.getStr("name");
                ca = new Command(CmdTypes.MYNAMEIS);
                ca.set("name", this.getName());
                send(ca);
                break;

            case GIVEID:
                ca = new Command(CmdTypes.RECEIVEID);
                ca.set("id", this.getId());
                send(ca);
                break;

            case PING:
                send(cmd);
                break;

            case STARTGAME:
                if (!isInGame(true)) break;
                if (!isLobbyOwner(true)) break;
                gl = SharedData.lobbies.get(this.lobbyId);
                gl.startGame();
                break;

            case REQUESTLOBBIES:
                int count = cmd.getInt("count");
                int offset = cmd.getInt("offset");
                ArrayList<String> keys = new ArrayList<>(SharedData.lobbies.keySet());
                for (int i = offset; i < offset + count; i++) {
                    String key = keys.get(i % keys.size());
                    gl = SharedData.lobbies.get(key);
                    gl.sendData(this, true);
                }
                break;

            case GIVEMYLOBBYDATA:
                if (!isInGame(true)) break;
                gl = SharedData.lobbies.get(this.lobbyId);
                gl.sendData(this, false);
                break;

            case KICKFROMLOBBY:
                if (!isInGame(true)) break;
                if (!isLobbyOwner(true)) break;
                gl = SharedData.lobbies.get(this.lobbyId);
                gl.kickPlayer(cmd.getStr("nameInGame"));
                break;
        }

        return stop;
    }

    private boolean isInGame(boolean warn) {
        if (!inGame || this.lobbyId == null) {
            if (warn) sendError("You are not in a game lobby");
            return false;
        }
        return true;
    }

    private boolean isLobbyOwner(boolean warn) {
        ZGSGameLobby gl = SharedData.lobbies.get(this.lobbyId);
        if (!gl.isOwner(this)) {
            if (warn) sendError("You are not the owner of your current lobby");
            return false;
        }
        return true;
    }

    private void setGameAsJoined(String lobbyId) {
        inGame = true;
        this.lobbyId = lobbyId;
        Command ca = new Command(CmdTypes.JOIN);
        ca.set("gameId", lobbyId);
        send(ca);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getNameInLobby() {
        return name + " #" + id;
    }

    public void sendRetry(String reason) {
        Command toSend = new Command(CmdTypes.RETRY);
        toSend.set("reason", reason);
        send(toSend);
    }

    public void sendError(String reason) {
        Command toSend = new Command(CmdTypes.ERROR);
        toSend.set("reason", reason);
        send(toSend);
    }

    private String debugStr(Command cmd) {
        String debug;
        debug = "Thread : " + Thread.currentThread().getName() + ". ";
        debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() + ".";
        debug += " Sur le port : " + remote.getPort() + ".\n";
        debug += "\t -> Commande reçue : " + cmd.toString() + "\n";
        return debug;
    }
}