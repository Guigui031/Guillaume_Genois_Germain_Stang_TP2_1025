package erreurs;

public class MatriculeException extends Exception {
    /**
     * Exception personnalisée permettant de capter lorsque l'utilisateur rentre
     * un matricule qui n'est pas dans le bon format.
     * @param errorMessage le message d'erreur pour l'utilisateur
     */
    public MatriculeException(String errorMessage) {
        super(errorMessage);
    }


}
