package ua.nure.bieiaiev.SummaryTask4.web.command.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command to view test result
 * 
 * @author D.Bieliaiev
 *
 */
public class ViewTestResultCommand extends Command {

	private static final long serialVersionUID = -6762238987721340827L;

	private static final Logger LOG = Logger.getLogger(ViewTestResultCommand.class);

	/**
	 * Executes View Test Result Command
	 * 
	 * @return {@link String} address to go forward
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");
		LOG.debug("Command finished");
		return Path.PAGE_VIEW_TEST_RESULT;
	}

}
