package ZwangClient.classes;

import Communication.Command;
import Communication.Communicator;
import ZwangClient.interfaces.GeneralLinker;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Bridge extends Communicator implements Runnable {

    private String id;
    private String name = "anonymous";
    private boolean inGame = false;
    private String lobbyId = null;
    private GameLobby lobbyHandler;
    private GeneralLinker[] extensions = {};

    public Bridge(String host, int port) throws IOException {
        super(new Socket(host, port));
    }

    public Bridge(String host, int port, GeneralLinker[] extensions) throws IOException {
        this(host, port);
        this.extensions = extensions;
    }

    public void run() {
        boolean running = true;
        while (running) {
            try {
                setWriter(new PrintWriter(getSock().getOutputStream(), true));
                setReader(new BufferedInputStream(getSock().getInputStream()));
                Command cmd = read();
                System.out.println("\t * Réponse reçue " + cmd);

                running = handleCommand(cmd);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean handleCommand(Command cmd) {
        boolean stop = false;
        switch (cmd.type) {
            case DISCONNECT:
                stop = true;
                inGame = false;
                lobbyHandler = null;
                break;

            case JOIN:
                if (inGame) break;
                inGame = true;
                lobbyHandler = new GameLobby();
                break;

            case PING:
                String message = cmd.getStr("message");
                int integer = cmd.getInt("integer");
                for (GeneralLinker gl : extensions) gl.onPing(message, integer);
                break;
        }
        return stop;
    }
}
