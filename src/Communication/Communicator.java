package Communication;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Communicator {

    private final Socket sock;
    private volatile PrintWriter writer = null;
    private BufferedInputStream reader = null;

    public Communicator(Socket sock) {
        this.sock = sock;
    }

    protected Command read() throws IOException {
        String response;
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return Command.fromJson(response);
    }

    public void send(Command toSend) {
        synchronized (writer) {
            writer.write(toSend.toJson());
            writer.flush();
        }
    }

    protected void closeConnection() throws IOException {
        writer = null;
        reader = null;
        sock.close();
    }

    protected Socket getSock() {
        return sock;
    }

    protected void setReader(BufferedInputStream reader) {
        this.reader = reader;
    }

    protected void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

}
