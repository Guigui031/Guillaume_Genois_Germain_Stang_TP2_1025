package erreurs;

public class EmailException extends Exception {
    /**
     *
     * @param errorMessage
     */
    public EmailException(String errorMessage) {
        super(errorMessage);
    }
}
