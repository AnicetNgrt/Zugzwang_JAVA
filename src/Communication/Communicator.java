package Communication;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Communicator {

    private final Socket sock;

    public Communicator(Socket sock) {
        this.sock = sock;
    }

    protected LinkedList<Command> read() {
        LinkedList<Command> cmds = new LinkedList<>();
        try {
            InputStream in = sock.getInputStream();
            BufferedReader i = new BufferedReader(new InputStreamReader(in));
            String line;
            while (i.ready() && (line = i.readLine()) != null) {
                Command c = Command.fromJson(line);
                cmds.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cmds;
    }

    public void send(Command toSend) {
        try {
            OutputStream out = sock.getOutputStream();
            PrintWriter o = new PrintWriter(out, true);
            String s = toSend.toJson();
            o.write(s + "\n");
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
