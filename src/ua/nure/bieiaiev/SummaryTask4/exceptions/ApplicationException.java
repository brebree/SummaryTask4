package ua.nure.bieiaiev.SummaryTask4.exceptions;

/**
 * Exception to encapsulate the cause from user.
 * 
 * @author D.Bieliaiev
 *
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 8288779062647218916L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

}
