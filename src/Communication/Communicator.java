package Communication;

import java.io.*;
import java.net.Socket;

public class Communicator {

    private final Socket sock;

    public Communicator(Socket sock) {
        this.sock = sock;
    }

    protected Command read() {
        try {
            InputStream in = sock.getInputStream();
            ObjectInputStream i = new ObjectInputStream(in);
            return Command.fromJson(i.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void send(Command toSend) {
        try {
            OutputStream out = sock.getOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(out);
            o.writeUTF(toSend.toJson());
            o.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void closeConnection() {
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Socket getSock() {
        return sock;
    }

}
