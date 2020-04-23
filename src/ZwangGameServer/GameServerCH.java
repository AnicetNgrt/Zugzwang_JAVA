package ZwangGameServer;

import Communication.ConnectionHandler;

import java.net.Socket;

public class GameServerCH extends ConnectionHandler {

    public GameServerCH() {
        super();
    }

    public GameServerCH(String pHost, int pPort) {
        super(pHost, pPort);
    }

    @Override
    public void handle(Socket sock) {
        System.out.println("Connexion cliente reçue.");
        String clientId = Utils.getAlphaNumericString(6);
        ClientHandler client = new ClientHandler(sock, clientId);
        SharedData.users.put(clientId, client);
        Thread t1 = new Thread(client);
        t1.start();
    }
}
