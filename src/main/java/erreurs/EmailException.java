package erreurs;

public class EmailException extends Exception {
    /**
     * Exception personnalisée permettant de capter lorsque l'utilisateur rentre
     * un email qui n'est pas dans le bon format.
     * @param errorMessage le message d'erreur pour l'utilisateur
     */
    public EmailException(String errorMessage) {
        super(errorMessage);
    }
}
