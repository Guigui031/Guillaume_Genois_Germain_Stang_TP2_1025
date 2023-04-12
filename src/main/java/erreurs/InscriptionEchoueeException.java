package erreurs;

public class InscriptionEchoueeException extends Exception {
    public InscriptionEchoueeException(String errorMessage) {
        super(errorMessage);
    }
}
