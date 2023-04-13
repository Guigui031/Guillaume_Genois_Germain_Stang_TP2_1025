package client.client_simple;

import client.ClientModel;
import erreurs.EmailException;
import erreurs.InscriptionEchoueeException;
import erreurs.MatriculeException;
import erreurs.MauvaisChoixException;
import server.models.Course;

import java.io.*;
import java.util.Scanner;

public class ClientSimple {
    private ClientModel client;
    private String sessionName;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructeur du ClientSimple annonçant son bon départ.
     * @param modele initialise globalement le modèle que le client utilisera pour ses calculs
     */
    public ClientSimple(ClientModel modele) {
        client = modele;
        System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
    }

    /**
     * Boucle principal permettant de répéter l'inscription aux cours.
     */
    public void run() {
        while (true) {
            handleSessionSelection();
        }
    }

    /**
     * Ferme le scanner à la fermeture du client.
     */
    public void close() {
        this.scanner.close();
    }

    /**
     * Imprime pour l'utilisateur le choix de sessions possibles
     * puis gère la réponse donnée par l'utilisateur.
     */
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
                    return;
            }
        } catch (Exception e) {
            System.out.println("-> Le choix que vous avez effectué n'existe pas...");
            return;
        }
        // TODO: séparer en différentes fonctions?
        try {
            client.handleCourseRequest(this.sessionName);
            handleCoursesDisplay();
        } catch (IOException e) {
            System.out.println("-> Erreur dans la connection au serveur. Veuillez vous assurez qu'il est actif.");
        } catch (ClassNotFoundException e) {
            System.out.println("-> La classe envoyée par le serveur n'existe pas dans le programme.");
        }

    }

    /**
     * Imprime les cours disponibles de la session sélectionnée par l'utilisateur et lui donne le choix
     * de s'inscrire à un cours ou de sélectionner une autre session.
     * On gère la réponse de l'utilisateur ensuite.
     */
    private void handleCoursesDisplay() {
        // Imprime les cours offerts pendant la session sélectionnée
        System.out.println("Les cours offerts pendant la session d'" + this.sessionName + " sont:");
        System.out.print(getListCoursesToString());

        // Imprime les choix d'actions à faire disponibles
        System.out.print("> Choix:\n1. Consulter les cours offerts pour une autre session\n2. Inscription à un cours.\n> Choix: ");

        // Gérer le choix de l'utilisateur
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
                    handleCoursesDisplay();
                    break;
            }
        } catch (Exception e) {
            System.out.println("-> Le choix que vous avez effectué n'existe pas...");
            handleCoursesDisplay();
        }
    }

    /**
     * Demande à l'utilisateur les informations nécessaires à son inscription à un cours.
     * On vérifie aussi si les informations sont bonnes si nécessaire.
     * Puis on l'inscrit au cours.
     */
    private void handleCourseSelection() {
        scanner.nextLine();  // pour clear les \n déjà présents

        System.out.print("Veuillez saisir votre prénom: ");
        String prenom = this.scanner.nextLine();

        System.out.print("Veuillez saisir votre nom: ");
        String nom = this.scanner.nextLine();

        System.out.print("Veuillez saisir votre email: ");
        String email = getGoodEmail(this.scanner.nextLine());  // vérifie format email

        System.out.print("Veuillez saisir votre matricule: ");
        String matricule = getGoodMatricule(this.scanner.nextLine());  // vérifie format matricule

        System.out.print("Veuillez saisir le code du cours: ");
        String codeCours = this.scanner.nextLine();

        // demande au modèle de s'inscrire au cours
        try {
            client.validateRegistration(prenom, nom, email, matricule, codeCours);
            System.out.println("Félicitations! Inscription réussie de " + prenom + " au cours " + codeCours + ".");
        } catch (IOException e) {
            System.out.println("-> Erreur dans la connection au serveur. Veuillez vous assurez qu'il est actif.");
        } catch (MauvaisChoixException e) {
            System.out.println("-> Le choix \"" + e.getMessage() + "\" que vous avez effectué n'existe pas.");
        } catch (InscriptionEchoueeException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("-> La classe envoyée par le serveur n'existe pas dans le programme.");
        }
    }

    /**
     * Formatte les cours disponibles pour l'affichage à l'utilisateur.
     * @return le String à afficher
     */
    public String getListCoursesToString() {
        // TODO: si liste vide, imprime "Aucun cours disponible"?
        String listCourses = "";
        int id = 0;
        for (Course element : client.getCours()) {
            id += 1;
            listCourses = listCourses + id + ". " + element.getCode() + "\t" + element.getName() + "\n";
        }
        return listCourses;
    }

    /**
     * Vérifie que le email est un email avec un bon format
     * S'il ne l'est pas, en redemande un nouveau à l'utilisateur.
     * @param email le email donné par l'utilisateur à vérifier le format
     * @return un email avec un bon format
     */
    private String getGoodEmail(String email) {
        try {
            client.validateEmail(email);
            return email;
        } catch (EmailException e) {
            System.out.print("Erreur. Veuillez saisir un bon email: ");
            return getGoodEmail(this.scanner.nextLine());
        }
    }

    /**
     * Vérifie que le matricule est un matricule avec un bon format
     * S'il ne l'est pas, en redemande un nouveau à l'utilisateur.
     * @param matricule le matricule donné par l'utilisateur à vérifier le format
     * @return un matricule avec un bon format
     */
    private String getGoodMatricule(String matricule) {
        try {
            client.validateMatricule(matricule);
            return matricule;
        } catch (MatriculeException e) {
            System.out.print("Erreur. Veuillez saisir un bon matricule: ");
            return getGoodMatricule(this.scanner.nextLine());
        }
    }
}
