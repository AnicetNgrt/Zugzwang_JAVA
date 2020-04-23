package ZwangGameServer;

import Communication.ConnectionHandler;
import Utils.NetworkUtils;

import java.net.Socket;

public class ZGSConnectionHandler extends ConnectionHandler {

    public ZGSConnectionHandler() {
        super();
    }

    public ZGSConnectionHandler(String pHost, int pPort) {
        super(pHost, pPort);
    }

    @Override
    public void handle(Socket sock) {
        System.err.println("Connexion cliente reçue");
        String clientId = NetworkUtils.getAlphaNumericString(6);
        ClientHandler client = new ClientHandler(sock, clientId);
        SharedData.users.put(clientId, client);
        Thread t1 = new Thread(client);
        t1.start();
    }
}
