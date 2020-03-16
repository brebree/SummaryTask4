package ua.nure.bieiaiev.SummaryTask4.web.command.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command to logout from account
 * 
 * @author D.Bieliaiev
 *
 */
public class LogoutCommand extends Command {

	private static final long serialVersionUID = 8233807685482415150L;

	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

	/**
	 * Executes Logout Command
	 * 
	 * @return {@link String} address to redirect to login page
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		LOG.debug("Command finished");
		return Path.PAGE_LOGOUT;
	}

}
