package ua.nure.bieiaiev.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;

/**
 * Command to view register page
 * 
 * @author D.Bieliaiev
 *
 */
public class ViewRegisterCommand extends Command {

	private static final long serialVersionUID = 8563158248714719207L;

	private static final Logger LOG = Logger.getLogger(ViewRegisterCommand.class);

	/**
	 * Executes View Register Command
	 * 
	 * @return {@link String} address to go forward
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");
		LOG.debug("Command finished");
		return Path.PAGE_VIEW_REGISTER;
	}

}
