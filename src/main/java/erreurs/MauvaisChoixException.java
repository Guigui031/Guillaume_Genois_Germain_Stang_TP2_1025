package erreurs;

public class MauvaisChoixException extends Exception {
    public MauvaisChoixException(String errorMessage) {
        super(errorMessage);
    }
}