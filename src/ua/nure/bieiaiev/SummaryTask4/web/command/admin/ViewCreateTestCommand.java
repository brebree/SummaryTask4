package ua.nure.bieiaiev.SummaryTask4.web.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.SubjectDao;
import ua.nure.bieiaiev.SummaryTask4.entities.Subject;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command to view a create test page
 * 
 * @author D.Bieliaiev
 *
 */
public class ViewCreateTestCommand extends Command {

	private static final long serialVersionUID = 4443920381907842916L;

	private static final Logger LOG = Logger.getLogger(ViewCreateTestCommand.class);

	/**
	 * Executes View Create Test Command
	 * 
	 * @return {@link String} address to go forward
	 * @throws ApplicationException
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");
		try {
			DaoFactory dao = DaoFactory.getDaoFactory("Derby");
			SubjectDao sjDao = dao.getSubectDao();

			List<Subject> subjects = sjDao.getAll();
			LOG.trace("Found in DB: subjects -->" + subjects);

			HttpSession session = request.getSession();

			session.setAttribute("subjects", subjects);
			LOG.trace("Set the session attribute: subjects -->" + subjects);
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not show create test page", e);
		}
		LOG.debug("Command finished");
		return Path.PAGE_VIEW_CREATE_TEST;
	}

}
