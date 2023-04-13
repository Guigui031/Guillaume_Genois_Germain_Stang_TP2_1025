package erreurs;

public class MauvaisChoixException extends Exception {
    /**
     * Exception personnalisée permettant de capter lorsque l'utilisateur rentre
     * un choix qui n'est pas valide.
     * @param errorMessage le message d'erreur pour l'utilisateur spécifiant quel a été le mauvais choix
     */
    public MauvaisChoixException(String errorMessage) {
        super(errorMessage);
    }
}