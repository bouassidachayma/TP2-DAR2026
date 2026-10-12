package serverPackage;

import java.io.*;
import java.net.*;

public class Server4 {
    public static final int PORT = 1234;
    // "0.0.0.0" means listen on all network interfaces of this machine
    public static final String SERVER_IP = "0.0.0.0";

    public static void main(String[] args) throws IOException {
        // 1. Use InetAddress and InetSocketAddress
        InetAddress ip = InetAddress.getByName(SERVER_IP);
        InetSocketAddress adresse = new InetSocketAddress(ip, PORT);

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(adresse);

        System.out.println("Serveur démarré sur " + adresse);
        System.out.println("En attente d'un client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connecté : " + socket.getInetAddress() + ":" + socket.getPort());

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // 2. Menu loop
        while (true) {
            int operation = dis.readInt();

            if (operation == 0) {
                System.out.println("Le client a demandé la fin.");
                break;
            }
            double x = dis.readDouble();
            double y = dis.readDouble();
            double resultat = 0;
            boolean ok = true;
            String erreur = "";

            switch (operation) {
                case 1: resultat = x + y; System.out.println("Addition: " + x + " + " + y + " = " + resultat); break;
                case 2: resultat = x - y; System.out.println("Soustraction: " + x + " - " + y + " = " + resultat); break;
                case 3: resultat = x * y; System.out.println("Multiplication: " + x + " * " + y + " = " + resultat); break;
                case 4:
                    if (y == 0) {
                        ok = false;
                        erreur = "Division par zéro impossible";
                        System.out.println("Erreur: Division par zéro");
                    } else {
                        resultat = x / y;
                        System.out.println("Division: " + x + " / " + y + " = " + resultat);
                    }
                    break;
                default:
                    ok = false;
                    erreur = "Opération inconnue";
                    System.out.println("Erreur: Opération inconnue");
            }
            // Send status and result/error
            dos.writeBoolean(ok);
            if (ok) {
                dos.writeDouble(resultat);
            } else {
                dos.writeUTF(erreur);
            }
            dos.flush();
        }
        dis.close();
        dos.close();
        socket.close();
        serverSocket.close();
        System.out.println("Serveur arrêté.");
    }
}