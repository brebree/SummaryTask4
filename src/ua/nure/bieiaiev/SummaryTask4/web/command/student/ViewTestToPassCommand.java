package ua.nure.bieiaiev.SummaryTask4.web.command.student;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Command to view pass test page
 * 
 * @author D.Bieliaiev
 *
 */
public class ViewTestToPassCommand extends Command {

	private static final long serialVersionUID = 8419500104211527016L;

	private static final Logger LOG = Logger.getLogger(ViewTestToPassCommand.class);

	/**
	 * Executes View Test To Pass Command
	 * 
	 * @return {@link String} address to go forward
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");
		try {

			Integer id = Integer.parseInt(request.getParameter("id"));
			LOG.trace("Request parameter: id --> " + id);

			TestDao testDao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getTestDao();
			Test test = testDao.getById(id);
			LOG.trace("Found in DB: test --> " + test);
			request.setAttribute("test", test);
			LOG.trace("Set the request attribute: test --> " + test);

			SubjectDao sjDao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getSubectDao();
			List<Subject> subjects = sjDao.getAll();
			request.setAttribute("subjects", subjects);
			LOG.trace("Set the request attribute: subjects --> " + subjects);
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not view test to pass", e);
		}
		LOG.debug("Command finished");
		return Path.PAGE_VIEW_TEST_TO_PASS;
	}
}
