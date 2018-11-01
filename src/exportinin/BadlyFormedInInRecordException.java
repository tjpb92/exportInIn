package exportinin;

/**
 * Classe qui définit une exception lancée lorsqu'un enregistrement InIn est mal formaté
 * 
 * @author Thierry Baribaud
 * @version 0.02
 */
public class BadlyFormedInInRecordException extends Exception {

    /**
     * Creates a new instance of <code>BadlyFormedInInRecordException</code>
     * without detail message.
     */
    public BadlyFormedInInRecordException() {
    }

    /**
     * Constructs an instance of <code>BadlyFormedInInRecordException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public BadlyFormedInInRecordException(String msg) {
        super(msg);
    }
}
