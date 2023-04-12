package client.client_simple;

import client.ClientModel;
import erreurs.EmailException;
import erreurs.InscriptionEchoueeException;
import erreurs.MauvaisChoixException;
import server.models.Course;

import java.io.*;
import java.util.Scanner;

public class ClientSimple {

    private ClientModel client;
    private String sessionName;

    private Scanner scanner = new Scanner(System.in);

    //TODO: A noter, le scanner est global au client.


    public ClientSimple(ClientModel modele) throws IOException {
        System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
        client = modele;
    }

    public void run() {
        // le controleur
        while (true) {
            handleSessionSelection();
        }
    }

    public void close() {
        this.scanner.close();
    }

    private void handleSessionSelection() {
        System.out.println("Veuillez choisir la session pour laquelle vous souhaitez consulter la liste de cours:");
        System.out.print("1. Automne\n2. Hiver\n3. Été\n> Choix: ");
        try {
            int session = this.scanner.nextInt();

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
                    System.out.println("-> Le choix que vous avez effectué n'existe pas...");
                    handleSessionSelection();
            }
        } catch (Exception e) {
            System.out.println("-> Le choix que vous avez effectué n'existe pas...");
            handleSessionSelection();  // TODO: ou return vu que boucle?
        }

        try {
            client.handleCourseRequest(this.sessionName);
            handleCoursDisplay();
        } catch (IOException e) {
            System.out.println("-> Erreur dans la connection au serveur. Veuiller vous assurez qu'il est actif.");
            // TODO: arrêter le programme?
        } catch (ClassNotFoundException e) {
            System.out.println("-> La classe envoyée par le serveur n'existe pas dans le programme.");
        }

    }


    private void handleCoursDisplay() {
        System.out.println("Les cours offerts pendant la session d'" + this.sessionName + " sont:");
        System.out.println(getListCourses());

        System.out.print("> Choix:\n1. Consulter les cours offerts pour une autre session\n2. Inscription à un cours.\n> Choix: ");

        try {
            int choix = this.scanner.nextInt();

            switch (choix) {
                case 1:
                    handleSessionSelection();
                    break;
                case 2:
                    handleCourseSelection();
                    break;
                default:
                    System.out.println("-> Le choix que vous avez effectué n'existe pas...");
                    handleCoursDisplay();
                    break;
            }
        } catch (Exception e) {
            System.out.println("-> Le choix que vous avez effectué n'existe pas...");
            handleCoursDisplay();
        }
    }

    private void handleCourseSelection() {
        scanner.nextLine();  // pour clear les \n présents

        System.out.print("Veuillez saisir votre prénom: ");
        String prenom = this.scanner.nextLine();

        System.out.print("Veuillez saisir votre nom: ");
        String nom = this.scanner.nextLine();

        System.out.print("Veuillez saisir votre email: ");
        String email = getGoodEmail(this.scanner.nextLine());

        System.out.print("Veuillez saisir votre matricule: ");
        String matricule = this.scanner.nextLine();

        System.out.print("Veuillez saisir le code du cours: ");
        String codeCours = this.scanner.nextLine();

        // demande au modèle de s'inscrire au cours
        try {
            client.validateRegistration(prenom, nom, email, matricule, codeCours);
            System.out.println("Félicitations! Inscription réussie de " + prenom + " au cours " + codeCours + ".");
        } catch (IOException e) {
            System.out.println("-> Erreur dans la connection au serveur. Veuiller vous assurez qu'il est actif.");
        } catch (MauvaisChoixException e) {
            System.out.println("-> Le choix \"" + e.getMessage() + "\" que vous avez effectué n'existe pas.");
        } catch (InscriptionEchoueeException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("-> La classe envoyée par le serveur n'existe pas dans le programme.");
        }
    }

    public String getListCourses() {
        String listCourses = "";
        int id = 0;
        for (Course element : client.getCours()) {
            id += 1;
            listCourses = listCourses + id + ". " + element.getCode() + "\t" + element.getName() + "\n";  // TODO: \n de trop
        }
        return listCourses;
    }

    private String getGoodEmail(String email) {
        try {
            client.validateEmail(email);
            return email;
        } catch (EmailException e) {
            System.out.print("Erreur. Veuillez saisir un bon email: ");
            return getGoodEmail(this.scanner.nextLine());
        }
    }
}
