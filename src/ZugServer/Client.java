package ZugServer;

import Communication.CmdTypes;
import Communication.Command;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;

public class Client implements Runnable {

    private final Socket sock;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private InetSocketAddress remote;
    private final String id;
    private volatile String name = "anonymous";
    private volatile boolean inGame = false;
    private volatile String lobbyId = null;

    public Client(Socket pSock, String id) {
        sock = pSock;
        this.id = id;
    }

    public void run() {
        System.err.println("Lancement du traitement de la connexion cliente");

        while (!sock.isClosed()) {

            try {
                writer = new PrintWriter(sock.getOutputStream());
                reader = new BufferedInputStream(sock.getInputStream());

                Command cmd = read();
                remote = (InetSocketAddress) sock.getRemoteSocketAddress();

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

        switch (cmd.type) {
            case DISCONNECT:
                stop = true;
                break;

            case HOST:
                String lobbyId = Utils.getAlphaNumericString(6);
                String gameName = cmd.getStr("gameName");
                int mpc = cmd.getInt("maxPlayerCount");
                int spc = cmd.getInt("spectatorsAllowed");
                GameLobby gl = new GameLobby(lobbyId, this, gameName, mpc, spc);
                SharedData.lobbies.put(lobbyId, gl);
                inGame = true;
                this.lobbyId = lobbyId;
                break;

            case JOIN:
                lobbyId = cmd.getStr("gameId");
                final ConcurrentHashMap<String, GameLobby> lobbies = SharedData.lobbies;
                if (lobbies.containsKey(lobbyId)) {
                    boolean canJoin = lobbies.get(lobbyId).addPlayer(this);
                    if (canJoin) {
                        inGame = true;
                        this.lobbyId = lobbyId;
                    } else {
                        Command error = new Command(CmdTypes.ERROR);
                        error.set("reason", "can't join this game");
                        send(error);
                    }
                } else {
                    sendRetry("can't find game with such id");
                }
                break;

            case MYNAME:
                this.name = cmd.getStr("name");
                break;
        }

        return stop;
    }

    public String getName() {
        return name;
    }

    public String getNameInLobby() {
        return name + " #" + id;
    }

    private Command read() throws IOException {
        String response;
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return Command.fromJson(response);
    }

    public void sendRetry(String reason) {
        Command toSend = new Command(CmdTypes.RETRY);
        toSend.set("reason", reason);
        send(toSend);
    }

    public void send(Command toSend) {
        synchronized (writer) {
            writer.write(toSend.toJson());
            writer.flush();
        }
    }

    public void closeConnection() throws IOException {
        System.err.println("COMMANDE CLOSE DETECTEE ! ");
        writer = null;
        reader = null;
        sock.close();
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