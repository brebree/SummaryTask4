package ua.nure.bieiaiev.SummaryTask4.web.command.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command to define that current command doesn't exist
 * 
 * @author D.Bieliaiev
 *
 */
public class IncorrectCommand extends Command {

	private static final long serialVersionUID = 1204915289274936068L;

	private static final Logger LOG = Logger.getLogger(IncorrectCommand.class);

	/**
	 * Executes Incorrect Command
	 * 
	 * @return {@link String} address to go forward to error page
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		String errorMessage = "No such command";
		request.setAttribute("errorMessage", errorMessage);
		LOG.error("Set the request attribute: errorMessage --> " + errorMessage);

		LOG.debug("Command finished");
		return Path.PAGE_ERROR_PAGE;
	}

}
