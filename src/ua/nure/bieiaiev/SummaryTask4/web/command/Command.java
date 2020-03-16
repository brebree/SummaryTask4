package ua.nure.bieiaiev.SummaryTask4.web.command;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;

/**
 * Command model.
 * 
 * @author D.Bieliaiev
 *
 */
public abstract class Command implements Serializable {

	private static final long serialVersionUID = 6028505247690537837L;

	/**
	 * Executes the command
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @return {@link String} address to redirect/forward
	 * @throws IOException
	 * @throws ServletException
	 * @throws ApplicationException
	 */
	public abstract String execute(HttpServletRequest request, HttpServletResponse response)
			throws  ApplicationException;

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
