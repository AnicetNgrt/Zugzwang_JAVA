package ZwangGameServer;

import Communication.CmdTypes;
import Communication.Command;
import Communication.Communicator;
import Utils.NetworkUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler extends Communicator implements Runnable {

    private static final String DEFAULT_NAME = "anon";

    private InetSocketAddress remote;
    private final String id;
    private volatile String name = DEFAULT_NAME;
    private volatile boolean inGame = false;
    private volatile String lobbyId = null;

    public ClientHandler(Socket pSock, String id) {
        super(pSock);
        this.id = id;
    }

    public void run() {
        System.err.println("Lancement du traitement de la connexion cliente");

        while (!getSock().isClosed()) {

            try {
                setWriter(new PrintWriter(getSock().getOutputStream()));
                setReader(new BufferedInputStream(getSock().getInputStream()));

                Command cmd = read();
                remote = (InetSocketAddress) getSock().getRemoteSocketAddress();

                System.err.println("\n" + debugStr(cmd));
                boolean stop = handleCommand(cmd);

                if (stop) {
                    closeConnection();
                    break;
                }
            } catch (SocketException e) {
                System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
                break;
            } catch (IOException e) {
                e.printStackTrace();
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
        } else if (cmd.isMissing()) {
            sendRetry("missing argument");
        }

        Command ca;
        ZGSGameLobby gl;

        switch (cmd.type) {
            case DISCONNECT:
                System.err.println("arrêt volontaire de la connexion");
                stop = true;
                break;

            case HOST:
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
                lobbyId = cmd.getStr("gameId");
                final ConcurrentHashMap<String, ZGSGameLobby> lobbies = SharedData.lobbies;
                if (lobbies.containsKey(lobbyId)) {
                    boolean canJoin = lobbies.get(lobbyId).addPlayer(this);
                    if (canJoin) {
                        setGameAsJoined(lobbyId);
                    } else {
                        sendError("can't join this game");
                    }
                } else {
                    sendRetry("can't find game with such id");
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
                ca = new Command(CmdTypes.GIVEID);
                ca.set("id", this.getId());
                send(ca);
                break;

            case PING:
                ca = new Command(CmdTypes.PING);
                ca.set("message", cmd.getStr("message"));
                ca.set("integer", cmd.getStr("integer"));
                send(ca);

            case STARTGAME:
                if (!isInGame(true)) break;
                if (!isLobbyOwner(true)) break;
                gl = SharedData.lobbies.get(this.lobbyId);
                gl.startGame();
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