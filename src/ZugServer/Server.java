package ZugServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port = 2345;
    private String host = "127.0.0.1";
    private ServerSocket server = null;
    private boolean isRunning = true;

    public Server() {
        try {
            server = new ServerSocket(port, 100, InetAddress.getByName(host));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(String pHost, int pPort) {
        host = pHost;
        port = pPort;
        try {
            server = new ServerSocket(port, 100, InetAddress.getByName(host));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void open() {

        Thread t = new Thread(() -> {
            while (isRunning) {

                try {
                    Socket sock = server.accept();

                    System.out.println("Connexion cliente reçue.");
                    String clientId = Utils.getAlphaNumericString(6);
                    Client client = new Client(sock, clientId);
                    SharedData.users.put(clientId, client);
                    Thread t1 = new Thread(client);
                    t1.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
                server = null;
            }
        });

        t.start();
    }

    public void close() {
        isRunning = false;
    }
}
