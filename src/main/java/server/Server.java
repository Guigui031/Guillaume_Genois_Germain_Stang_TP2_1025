package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;


    /**
     Construit un objet Serveur qui écoute sur le port spécifié.
     @param port le port sur lequel le Serveur doit tourner.
     @throws IOException si une erreur d'entrée/sortie survient lors de la création de la ServerSocket.
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     Ajoute un gestionnaire d'événements à la liste des gestionnaires d'événements pour ce Serveur.
     @param h le gestionnaire d'événements à ajouter.
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     Alertes tous les gestionnaires d'événements enregistrés pour ce Serveur avec la commande et l'argument spécifiés.
     @param cmd la commande à transmettre aux gestionnaires d'événements
     @param arg l'argument à transmettre aux gestionnaires d'événements
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     Écoute en permanence pour les connexions entrantes et gère chaque connexion individuellement.
     @throws Exception si une erreur se produit lors de la création des flux d'entrée et de sortie, ou lors de la fermeture de la connexion
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     Écoute les messages entrants du client et les traite en appelant la méthode {@link #processCommandLine(String)}
     pour extraire les commandes et les arguments, puis en appelant la méthode {@link #alertHandlers(String, String)}
     pour alerter les gestionnaires d'événements enregistrés.
     @throws IOException si une erreur se produit lors de la lecture des messages du client ou de la communication avec les gestionnaires d'événements
     @throws ClassNotFoundException si la classe d'un objet reçu via le flux d'entrée d'objets n'a pas été trouvée
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            System.out.println(cmd);
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     Traite une ligne de commande envoyée par le client en la divisant en deux parties : la commande et les arguments.
     @param line la ligne de commande envoyée par le client
     @return un objet {@link Pair} contenant la commande (première partie de la ligne) et les arguments (le reste de la ligne)
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     Ferme les flux d'entrée et de sortie et la connexion avec le client.
     @throws IOException si une erreur se produit lors de la fermeture des flux d'entrée et de sortie ou de la connexion avec le client
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     Gère les événements envoyés par les clients en appelant les méthodes appropriées.
     Si la commande est {@value #REGISTER_COMMAND}, la méthode {@link #handleRegistration()} est appelée.
     Si la commande est {@value #LOAD_COMMAND}, la méthode {@link #handleLoadCourses(String)} est appelée.
     @param cmd la commande envoyée par le client
     @param arg les arguments associés à la commande (le cas échéant)
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transformer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     @throws Exception si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux
     */
    public void handleLoadCourses(String arg) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            FileInputStream fileStream = new FileInputStream("src/main/java/server/data/cours.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {

                String[] fields = line.split("\t");

                if (fields[2].equals(arg)) {
                    Course course = new Course((String) fields[1], (String) fields[0], (String) fields[2]);
                    courses.add(course);
                }
            }

            bufferedReader.close();
            inputStreamReader.close();
            fileStream.close();

            objectOutputStream.writeObject(courses);

        } catch (IOException e) {
            e.printStackTrace();
        } // TODO: catch de l'exception lors du flux de sortie?

    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     @throws Exception si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            //processCommandLine(objectInputStream.readObject().toString());
            // stream vs non stream?
            System.out.println("lol");
            RegistrationForm form = (RegistrationForm) objectInputStream.readObject();
            System.out.println(form);
            FileOutputStream fileStream = new FileOutputStream("src/main/java/server/data/inscription.txt");
            System.out.println("file");
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(fileStream));
            System.out.println("writer");
            String msg = form.getCourse() + "\t" + form.getClass() + "\t" + form.getPrenom() + "\t" + form.getNom() + "\t" + form.getEmail();
            writer.append(msg);  // TODO: s'assurer que ajoute au fichier et ne reset pas tout
            writer.close();
            System.out.println("inscription");
            objectOutputStream.writeObject("Inscription réussie");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("La classe lue n'existe pas dans le programme");
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Erreur à la lecture du fichier");
        } // TODO: catch de l'exception lors du flux de sortie?

    }
}

