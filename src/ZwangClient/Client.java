package ZwangClient;

import NetworkingClasses.Command;
import NetworkingClasses.Communicator;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Communicator implements Runnable {

    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private String id;
    private String name = "anonymous";
    private boolean inGame = false;
    private String lobbyId = null;

    public Client(String host, int port) throws IOException {
        super(new Socket(host, port));
    }


    public void run() {

        //nous n'allons faire que 10 demandes par thread...
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {


                writer = new PrintWriter(getSock().getOutputStream(), true);
                reader = new BufferedInputStream(getSock().getInputStream());
                //On envoie la commande au serveur

                /*Command commande;
                writer.write(commande);*/
                //TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR

                //On attend la réponse
                Command response = read();
                System.out.println("\t * Réponse reçue " + response);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        writer.write("CLOSE");
        writer.flush();
        writer.close();
    }
}
