package ua.nure.levchenko.SummaryTask.exception;

/**
 * An exception that provides
 * information on a DAO level errors.
 *
 * @author K.Levchenko
 */
public class DBException extends ServiceException {

    private static final long serialVersionUID = 7724449906837780062L;

    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

}
