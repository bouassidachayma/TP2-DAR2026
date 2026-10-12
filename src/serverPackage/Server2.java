package serverPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
    public static void main(String[] args) throws IOException {
        // 1. Créer la socket d'écoute sur le port 1234
        ServerSocket socketServer = new ServerSocket(1234);
        System.out.println("Je suis un serveur en attente la connexion d'un client");

        // 2. Attendre et accepter la connexion d'un client
        Socket socket = socketServer.accept();
        System.out.println("un client est connecté");

        // 3. Recevoir le nombre
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        int nb = dis.readInt();
        System.out.println("Le serveur a reçu le nombre : " + nb);

        // 4. Calculer x * 5
        int x = nb * 5;
        System.out.println("Calcul du serveur : " + nb + " * 5 = " + x);

        // 5. Envoyer le résultat
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(x);
        System.out.println("Le résultat a été envoyé au client : " + x);

        // 6. Fermer
        dis.close();
        dos.close();
        socket.close();
        socketServer.close();
    }
}