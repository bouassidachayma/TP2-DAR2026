package clientPackage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client3 {
    public static void main(String[] args) throws IOException {
        System.out.println("Je suis un client pas encore connecté...");

        Socket socket = new Socket("localhost", 1234);
        System.out.println("je suis un client connecté");

        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        Scanner scanner = new Scanner(System.in);

        // 5a. Boucle de saisie
        while (true) {
            System.out.print("Entrer un entier x (0 pour quitter) : ");
            int x = scanner.nextInt();

            // Envoyer l'entier
            dos.writeInt(x);
            dos.flush();
            System.out.println("Nombre envoyé au serveur : " + x);

            // 5c & 5d. Si 0, on quitte
            if (x == 0) {
                System.out.println("Fin de la communication.");
                break;
            }

            // Recevoir le résultat
            int xx = dis.readInt();
            System.out.println("Résultat reçu du serveur : " + xx);
        }

        dis.close();
        dos.close();
        socket.close();
        scanner.close();
        System.out.println("Client arrêté.");
    }
}