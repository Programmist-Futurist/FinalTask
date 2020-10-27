package ua.nure.levchenko.SummaryTask.exception;

/**
 * An exception that provides
 * information on an application level errors.
 *
 * @author K.Levchenko
 */
public class AppException extends Exception {

    private static final long serialVersionUID = -4387451635231264854L;

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }

}
