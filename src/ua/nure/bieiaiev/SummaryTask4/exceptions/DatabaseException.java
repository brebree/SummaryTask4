package ua.nure.bieiaiev.SummaryTask4.exceptions;

/**
 * Exception to group SQL exceptions and encapsulate the cause from user.
 * 
 * @author D.Bieliaiev
 *
 */
public class DatabaseException extends ApplicationException {

	private static final long serialVersionUID = -3550446897536410392L;

	public DatabaseException() {
		super();
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
