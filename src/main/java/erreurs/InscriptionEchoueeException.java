package erreurs;

public class InscriptionEchoueeException extends Exception {
    /**
     * Exception personnalisée permettant de capter lorsque l'inscription à un cours échoue.
     * @param errorMessage le message d'erreur pour l'utilisateur
     */
    public InscriptionEchoueeException(String errorMessage) {
        super(errorMessage);
    }
}
