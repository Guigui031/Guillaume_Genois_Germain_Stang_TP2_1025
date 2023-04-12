package client;
import erreurs.EmailException;
import erreurs.InscriptionEchoueeException;
import erreurs.InscriptionEchoueeException;
import erreurs.MauvaisChoixException;
import server.models.Course;
import server.models.RegistrationForm;

import java.util.ArrayList;
import java.net.Socket;
import java.io.*;


public class ClientModel {
    private ArrayList<Course> cours = new ArrayList<Course>();

    //TODO: Peut-être ré-agencer le code pour être dans le format MVC pour les deux clients.

    public void handleCourseRequest(String sessionName) throws IOException, ClassNotFoundException {
        // se connecte au server pour envoyer
        Socket socket = new Socket("127.0.0.1", 1337);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream dataOutputStream = new ObjectOutputStream(outputStream);

        // envoie la requête de charger
        dataOutputStream.writeObject("CHARGER " + sessionName);
        dataOutputStream.flush();

        // récupère la réponse du serveur
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        this.cours = (ArrayList<Course>) objectInputStream.readObject();  // ClassNotFoundException si pas capable de lire l'objet

        dataOutputStream.close();
    }

    private void handleCourseRegistration(RegistrationForm form) throws IOException, ClassNotFoundException, InscriptionEchoueeException {
        // se connecte au server pour envoyer
        Socket socket = new Socket("127.0.0.1", 1337);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream dataOutputStream = new ObjectOutputStream(outputStream);

        // envoie la requête de s'inscrire
        dataOutputStream.writeObject("INSCRIRE");
        dataOutputStream.flush();

        // envoie le form
        dataOutputStream.writeObject(form);
        dataOutputStream.flush();

        // récupère la réponse du serveur
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        String reussite = (String) objectInputStream.readObject();  // ClassNotFoundException si pas capable de lire le String

        // analyse la réponse
        if (!reussite.equals("Inscription réussie")) {
            throw new InscriptionEchoueeException(reussite);
        }
        dataOutputStream.close();
    }

    public void validateRegistration(String prenom, String nom, String email, String matricule, String codeCours) throws MauvaisChoixException, IOException, InscriptionEchoueeException, ClassNotFoundException {
        Course course = findCourse(codeCours);  // envoie erreur de choix si cours n'existe pas
        handleCourseRegistration(new RegistrationForm(prenom, nom, email, matricule, course));  // envoie erreur de connection au serveur
    }

    private Course findCourse(String codeCours) throws MauvaisChoixException {
        for (Course element : cours) {
            if (element.getCode().equals(codeCours)) {
                return element;
            }
        }
        throw new MauvaisChoixException(codeCours);
    }

    public void validateEmail(String email) throws EmailException {
        if (!email.matches("(.*)@(.*).(.*)")) {
            throw new EmailException("Mauvais email");
        }
    }

    public ArrayList<Course> getCours() {
        return cours;
    }
}
