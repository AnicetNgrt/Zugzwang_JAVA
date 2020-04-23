package Communication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {
    private int port = 2808;
    private String host = "127.0.0.1";
    private ServerSocket server = null;
    private boolean isRunning = true;

    public ConnectionHandler() {
        try {
            server = new ServerSocket(port, 100, InetAddress.getByName(host));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConnectionHandler(String pHost, int pPort) {
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
                    handle(sock);

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

    public void handle(Socket sock) {
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        isRunning = false;
    }
}
