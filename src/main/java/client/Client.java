package client;
import server.Server;
import server.models.Course;
import server.models.RegistrationForm;

import javax.swing.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.net.Socket;
import java.io.*;


public class Client {
    private ArrayList<Course> cours = new ArrayList<Course>();

    //TODO: Peut-être ré-agencer le code pour être dans le format MVC pour les deux clients.

    public static void main(String[] args) {

    }

    public void handleCourseRequest(String sessionName) throws IOException{
        Socket socket = new Socket("127.0.0.1", 1337);

        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream dataOutputStream = new ObjectOutputStream(outputStream);

        dataOutputStream.writeObject("CHARGER " + sessionName);
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
    }

    public void handleCourseRegistration(RegistrationForm form) throws IOException {
        Socket socket = new Socket("127.0.0.1", 1337);

        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream dataOutputStream = new ObjectOutputStream(outputStream);

        // envoyer la requete de s'inscrire
        dataOutputStream.writeObject("INSCRIRE");
        dataOutputStream.flush(); // send the message
        //TODO: BIEN GERE LES ERREURS.
        try {
            InputStream inputStream = socket.getInputStream();
            System.out.println("input");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            System.out.println("object");

            // envoyer le form
            dataOutputStream.writeObject(form);
            dataOutputStream.flush(); // send the message

            String reussite = (String) objectInputStream.readObject();
            System.out.println(reussite);
            if (reussite.equals("Inscription réussie")) {
                System.out.println("Félicitations! Inscription réussie de " + form.getPrenom() + " au cours " + form.getCourse().getCode() + ".");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERREUR");
        }
        dataOutputStream.close();
    }

    public void validateRegistration(String prenom, String nom, String email, String matricule, String codeCours) {
        try {
            Course course = findCourse(codeCours);
            handleCourseRegistration(new RegistrationForm(prenom, nom, email, matricule, course));
        } catch (IOException e) {
            System.out.println("ERREUR");
        } catch (Exception e) {
            System.out.println("-> Le choix de cours que vous avez saisi n'est pas valide.");
            //TODO: côté client simple l'affichage, donc le catch d'erreur
        }
    }

    private Course findCourse(String codeCours) throws Exception {
        for (Course element : cours) {
            if (element.getCode().equals(codeCours)) {
                return element;
            }
        }
        throw new Exception();  // TODO: autre type d'exception moins générale?
    }

    public String getListCourses() {
        String listCourses = "";
        int id = 0;
        for (Course element : cours) {
            id += 1;
            listCourses = listCourses + id + ". " + element.getCode() + "\t" + element.getName() + "\n";
        }
        return listCourses;
    }
}
