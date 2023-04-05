package client.client_simple;

import client.Client;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSimple {

    private Client client;
    private String sessionName;

    private Scanner scanner = new Scanner(System.in);
    //TODO: A noter, le scanner est global au client.


    public static void main(String[] args) throws IOException {
        System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
        run();
    }

    public void run() {
        client = new Client();
        while (true) {
            try {
                handleSessionSelection();
                handleCoursDisplay();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        this.scanner.close();
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

        client.handleCourseRequest(this.sessionName);

    }


    private void handleCoursDisplay() {
        System.out.println("Les cours offerts pendant la session d'" + this.sessionName + " sont:");
        System.out.println(client.getListCourses());


        System.out.print("> Choix:\n1. Consulter les cours offerts pour une autre session\n2. Inscription à un cours.\n> Choix: ");

        try {
            Integer choix = this.scanner.nextInt();  //TODO: Integer au lieu de int?

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
        System.out.print("Veuillez saisir votre prénom: ");
        scanner.nextLine();  // pour clear les \n présents
        String prenom = this.scanner.nextLine();

        System.out.print("Veuillez saisir votre nom: ");
        String nom = this.scanner.nextLine();

        System.out.print("Veuillez saisir votre email: ");
        String email = this.scanner.nextLine();

        System.out.print("Veuillez saisir votre matricule: ");
        String matricule = this.scanner.nextLine();

        System.out.print("Veuillez saisir le code du cours: ");
        String codeCours = this.scanner.nextLine();

        client.validateRegistration(prenom, nom, email, matricule, codeCours);
    }
}
