package clientPackage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) throws IOException {
        System.out.println("Je suis un client pas encore connecté...");

        // 2. Se connecter au serveur
        Socket socket = new Socket("localhost", 1234);
        System.out.println("je suis un client connecté");

        // 3. Demander un entier à l'utilisateur
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrer un entier x : ");
        int x = scanner.nextInt();

        // 4. Envoyer l'entier
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(x);
        System.out.println("Nombre envoyé au serveur : " + x);

        // 5. Recevoir le résultat
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        int xx = dis.readInt();
        System.out.println("Résultat reçu du serveur : " + xx);

        // 6. Fermer
        dis.close();
        dos.close();
        socket.close();
        scanner.close();
    }
}