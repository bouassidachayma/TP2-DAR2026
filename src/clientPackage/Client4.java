package clientPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client4 {
    public static final int PORT = 1234;
    public static final String SERVER_IP = "192.168.1.2";

    public static void main(String[] args) throws IOException {
        // 1. Use InetAddress and InetSocketAddress
        InetAddress ip = InetAddress.getByName(SERVER_IP);
        InetSocketAddress adresse = new InetSocketAddress(ip, PORT);

        Socket socket = new Socket();
        socket.connect(adresse, 5000); // 5 seconds timeout
        System.out.println("Connecté au serveur " + adresse);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        Scanner sc = new Scanner(System.in);

        // 2. Menu loop
        while (true) {
            System.out.println("\n--- Menu calculatrice ---");
            System.out.println("1. Addition");
            System.out.println("2. Soustraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            int operation = sc.nextInt();
            dos.writeInt(operation);

            if (operation == 0) {
                dos.flush();
                break;
            }

            System.out.print("Donner x : ");
            double x = sc.nextDouble();
            System.out.print("Donner y : ");
            double y = sc.nextDouble();

            dos.writeDouble(x);
            dos.writeDouble(y);
            dos.flush();

            boolean ok = dis.readBoolean();

            if (ok) {
                double resultat = dis.readDouble();
                System.out.println("Résultat = " + resultat);
            } else {
                String erreur = dis.readUTF();
                System.out.println("Erreur : " + erreur);
            }
        }

        dis.close();
        dos.close();
        socket.close();
        sc.close();
        System.out.println("Client arrêté.");
    }
}