package Utils;

import ZwangClient.classes.ClientBridge;
import ZwangClient.interfaces.UiLinker;
import ZwangGameServer.ZGSConnectionHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class NetworkUtils {

    public static int startServer(String host, int port) {
        ZGSConnectionHandler ch = new ZGSConnectionHandler(host, port);
        ch.open();
        return ch.port();
    }

    public static void startClient(String host, int port, UiLinker... uils) {
        try {
            Thread clientThread = new Thread(new ClientBridge(host, port, uils));
            clientThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean intToBool(int i) {
        return i > 0;
    }

    public static String getAlphaNumericString(int n) {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString = new String(array, StandardCharsets.UTF_8);

        // Create a StringBuffer to store the result
        StringBuilder r = new StringBuilder();

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }
}
