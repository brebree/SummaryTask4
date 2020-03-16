package ua.nure.bieiaiev.SummaryTask4.web.command.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.SubjectDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.TestDao;
import ua.nure.bieiaiev.SummaryTask4.entities.Subject;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Test;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command to view edit test page
 * 
 * @author D.Bieliaiev
 *
 */
public class ViewEditTestCommand extends Command {

	private static final long serialVersionUID = 2050208305942799214L;

	private static final Logger LOG = Logger.getLogger(ViewEditTestCommand.class);

	/**
	 * Executes View Edit Test Command
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
			TestDao testDao = dao.getTestDao();
			Integer testId = Integer.parseInt(request.getParameter("id"));
			LOG.trace("Request parameter: id --> " + testId);

			List<Subject> subjects = sjDao.getAll();
			LOG.trace("Found in DB: subjects --> " + subjects);

			HttpSession session = request.getSession();

			session.setAttribute("subjects", subjects);
			LOG.trace("Set the session attribute: subjects -->" + subjects);

			request.setAttribute("id", testId);
			LOG.trace("Set the request attribute: id --> " + testId);

			Test test = testDao.getById(testId);
			LOG.trace("Found in DB: test --> " + test);
			session.setAttribute("test", test);
			LOG.trace("Set the request attribute: test --> " + test);
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not show test to edit", e);
		}
		LOG.debug("Command finished");
		return Path.PAGE_VIEW_EDIT_TEST;
	}

}
