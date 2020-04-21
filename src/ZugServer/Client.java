package ZugServer;

import Communication.CmdTypes;
import Communication.Command;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client implements Runnable {

    private Socket sock;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private InetSocketAddress remote;
    private String name = "anonymous";
    private boolean inGame = false;
    private String lobbyId = null;
    private String id;

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
                break;
            case JOIN:
                break;
            case MYNAME:
                name = cmd.getStr("name");
                break;
        }

        return stop;
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
        writer.write(toSend.toJson());
        writer.flush();
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