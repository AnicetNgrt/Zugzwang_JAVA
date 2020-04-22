package ZwangIdentificationServer;

import NetworkingClasses.ConnectionHandler;

import java.io.IOException;
import java.net.Socket;

public class Server extends ConnectionHandler {

    public Server() {
        super();
    }

    public Server(String pHost, int pPort) {
        super(pHost, pPort);
    }

    @Override
    public void handle(Socket sock) {
        System.out.println("Connexion cliente reçue, mais rien faire");
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
