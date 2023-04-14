package client;
import erreurs.*;
import erreurs.InscriptionEchoueeException;
import server.models.Course;
import server.models.RegistrationForm;

import java.util.ArrayList;
import java.net.Socket;
import java.io.*;

/**
 * Dans cette classe, nous définissons le modèle pour les clients. Celui-ci est complètement indépendant
 * des interfaces graphiques. Il gère seul les appels au serveur et l'interprétation des données.
 */
public class ClientModel {
    private ArrayList<Course> courses = new ArrayList<>();

    /**
     * Envoie la requête au serveur de charger les cours disponibles à la session donnée
     * et les récupère pour les stocker globalement.
     * @param sessionName la session dont nous voulons récupérer les cours disponibles
     * @throws IOException si ne se connecte pas au serveur
     * @throws ClassNotFoundException si n'est pas capable de lire l'objet reçue en réponse du serveur
     */
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
        this.courses = (ArrayList<Course>) objectInputStream.readObject();

        dataOutputStream.close();
    }

    /**
     * Envoie la requête au serveur d'inscrire le formulaire d'inscription donnée.
     * @param form le formulaire d'inscription à envoyer au serveur
     * @throws IOException si ne se connecte pas au serveur
     * @throws ClassNotFoundException si n'est pas capable de lire l'objet reçue en réponse du serveur
     * @throws InscriptionEchoueeException si ne reçoit pas la confirmation d'inscription réussite du serveur
     */
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
        String reussite = (String) objectInputStream.readObject();

        // analyse la réponse
        if (!reussite.equals("Inscription réussie")) {
            throw new InscriptionEchoueeException(reussite);
        }
        dataOutputStream.close();
    }

    /**
     * Valide si l'inscription à un cours par l'utilisateur est un succès.
     * @param prenom le prénom de l'étudiant à inscrire
     * @param nom le nom de l'étudiant à inscrire
     * @param email le email bien formatté de l'étudiant à inscrire
     * @param matricule le matricule bien formatté de l'étudiant à inscrire
     * @param codeCours le code du cours où l'étudiant essaye de s'inscrire
     * @throws MauvaisChoixException si le choix de cours n'existe pas
     * @throws IOException si ne se connecte pas au serveur
     * @throws ClassNotFoundException si n'est pas capable de lire l'objet reçue en réponse du serveur
     * @throws InscriptionEchoueeException si ne reçoit pas la confirmation d'inscription réussite du serveur
     */
    public void validateRegistration(String prenom, String nom, String email, String matricule, String codeCours) throws MauvaisChoixException, IOException, InscriptionEchoueeException, ClassNotFoundException {
        Course course = findCourse(codeCours);
        handleCourseRegistration(new RegistrationForm(prenom, nom, email, matricule, course));
    }

    /**
     * Cherche pour un objet Course dont le code du cours correspond au code de cours donné.
     * @param codeCours le code du cours à trouver
     * @return le cours Course s'il est trouvé
     * @throws MauvaisChoixException si le code de cours ne correspond à aucun cours
     */
    private Course findCourse(String codeCours) throws MauvaisChoixException {
        for (Course element : courses) {
            if (element.getCode().equals(codeCours)) {
                return element;
            }
        }
        throw new MauvaisChoixException(codeCours);
    }

    /**
     * Vérifie qu'un email donné soit dans un format valide,
     * c'est-à-dire avec au moins un caractère avant @ et au moins des caractères après dont un . parmi eux
     * @param email le email à valider
     * @throws EmailException si le email n'est pas valide
     */
    public void validateEmail(String email) throws EmailException {
        if (!email.matches("^(.+)@(.+)\\.(.+)$")) {
            throw new EmailException("Mauvais email");
        }
    }

    /**
     * Vérifie qu'un matricule donné soit dans un format valide,
     * c'est-à-dire avec exactement 8 chiffres.
     * @param matricule le matricule à valider
     * @throws MatriculeException si le matricule n'est pas valide
     */
    public void validateMatricule(String matricule) throws MatriculeException {
        if (!matricule.matches("^\\d{8}$")) {
            throw new MatriculeException("Mauvais matricule");
        }
    }

    /**
     * Retourne la liste de cours disponibles.
     * @return ArrayList des cours
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }
}
