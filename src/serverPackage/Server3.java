package serverPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server3 {
    public static void main(String[] args) throws IOException {
        ServerSocket socketServer = new ServerSocket(1234);
        System.out.println("Je suis un serveur en attente la connexion d'un client");

        Socket socket = socketServer.accept();
        System.out.println("un client est connecté");

        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        // 5a. Boucle tant que le client envoie autre chose que 0
        while (true) {
            int nb = dis.readInt();
            System.out.println("Le serveur a reçu le nombre : " + nb);

            // 5d. Si 0, on arrête proprement
            if (nb == 0) {
                System.out.println("Le client a demandé la fin de la communication.");
                break;
            }

            // 5b. Calculer et renvoyer
            int x = nb * 5;
            System.out.println("Calcul du serveur : " + nb + " * 5 = " + x);

            dos.writeInt(x);
            dos.flush();
            System.out.println("Le résultat a été envoyé au client : " + x);
        }

        dis.close();
        dos.close();
        socket.close();
        socketServer.close();
        System.out.println("Serveur arrêté.");
    }
}