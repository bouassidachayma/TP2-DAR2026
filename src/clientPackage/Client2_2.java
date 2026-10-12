package clientPackage;

import commonPackage.Operation;
import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class Client2_2 {

    public static void main(String[] args) {
        try {
            System.out.println("Je suis un client pas encore connecté…");
            Socket socket = new Socket("localhost", 1234);
            System.out.println("je suis un client connecté");

            // Même ordre que le serveur : sortie d'abord, entrée ensuite
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            Scanner sc = new Scanner(System.in);
            sc.useLocale(Locale.US); // le point décimal est accepté (3.5)
            String encore;
            do {
                System.out.print("Premier opérande : ");
                double x = sc.nextDouble();

                String op;
                do {
                    System.out.print("Opérateur (+ - * /) : ");
                    op = sc.next();
                } while (!op.matches("[+\\-*/]"));

                System.out.print("Deuxième opérande : ");
                double y = sc.nextDouble();

                // b) Le client envoie un NOUVEL objet à chaque opération
                Operation operation = new Operation(x, op.charAt(0), y);
                oos.writeObject(operation);

                // g) Le client lit et affiche le résultat
                String reponse = (String) ois.readObject();
                System.out.println("Résultat : " + reponse);

                System.out.print("Autre calcul ? (o/n) : ");
                encore = sc.next();
            } while (encore.equalsIgnoreCase("o"));

            oos.writeObject("FIN"); // prévenir le serveur
            System.out.println("Fin de la communication");
            sc.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur côté client : " + e.getMessage());
        }
    }
}