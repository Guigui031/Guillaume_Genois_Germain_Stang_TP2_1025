package client;
import server.models.Course;

import javax.swing.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.net.Socket;
import java.io.*;


public class Client {
    private String sessionName;
    private ArrayList<Course> cours = new ArrayList<Course>();
    private Scanner scanner = new Scanner(System.in);

    //TODO: A noter, le scanner est global au client.
    //TODO: Peut-être ré-agencer le code pour être dans le format MVC pour les deux clients.

    public Client (int type) throws IOException {
        if (type == 0) {
            System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
            handleSessionSelection();
            this.scanner.close();
        } else {
            //TODO: CLIENT FX
        }
    }

    private void handleSessionSelection() throws IOException {
            System.out.println("Veuillez choisir la session pour laquelle vous souhaitez consulter la liste de cours:");
            System.out.print("1. Automne\n2. Hiver\n3. Ete\n> Choix: ");

            try {
                Integer session = this.scanner.nextInt();

                switch (session) {
                    case 1:
                        this.sessionName = "Automne";
                        break;
                    case 2:
                        this.sessionName = "Hiver";
                        break;
                    case 3:
                        this.sessionName = "Ete";
                        break;
                    default:
                        System.out.println("\n-> Le choix que vous avez effectué n'existe pas...");
                        handleSessionSelection();
                }
            } catch (Exception e) {
                System.out.println("\n-> Le choix que vous avez effectué n'existe pas...");
                handleSessionSelection();
            }

        handleCourseRequest();

    }

    private void handleCourseRequest() throws IOException{
        Socket socket = new Socket("127.0.0.1", 1337);

        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream dataOutputStream = new ObjectOutputStream(outputStream);

        dataOutputStream.writeObject("CHARGER " + this.sessionName);
        dataOutputStream.flush(); // send the message
        //TODO: BIEN GERE LES ERREURS.
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            this.cours = (ArrayList<Course>) objectInputStream.readObject();
        } catch (Exception e) {
            System.out.println("ERREUR");
        }
        dataOutputStream.close();
        handleCoursDisplay();
    }

    private void handleCoursDisplay() {
        System.out.println("Les cours offerts pendant la session d'" + this.sessionName + " sont:");
        int id = 0;

        for (Course element : cours) {
            id += 1;
            System.out.println(id + ". " + element.getCode() + "     " + element.getName());
        }

        System.out.print("> Choix:\n1. Consulter les cours offerts pour une autre session\n2.Inscription à un cours.\n> Choix: ");

        try {
            Integer choix = this.scanner.nextInt();

            switch (choix) {
                case 1:
                    handleSessionSelection();
                    break;
                case 2:
                    handleCourseSelection();
                    break;
                default:
                    System.out.println("\n-> Le choix que vous avez effectué n'existe pas...");
                    handleCoursDisplay();
                    break;
            }
        } catch (Exception e) {
            System.out.println("\n-> Le choix que vous avez effectué n'existe pas...");
            handleCoursDisplay();
        }

    }

    private void handleCourseSelection() {
        //TODO
    }

}
