package ua.nure.bieiaiev.SummaryTask4.web.command.commons;

import java.util.Collections;
import java.util.Comparator;
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
import ua.nure.bieiaiev.SummaryTask4.utils.ComparatorContainer;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command to view all tests by chosen subject
 * 
 * @author D.Bieliaiev
 *
 */
public class ViewTestsBySubjectCommand extends Command {

	private static final long serialVersionUID = -2722150984446753372L;

	private static final Logger LOG = Logger.getLogger(ViewTestsBySubjectCommand.class);

	/**
	 * Executes View Tests By Subject Command
	 * 
	 * @return {@link String} address to go forward
	 * @throws ApplicationException
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");

		Integer subjectId = null;
		subjectId = Integer.parseInt(request.getParameter("subject"));

		LOG.trace("Request parameter: subjectId --> " + subjectId);
		if (subjectId == null || subjectId == 0) {
			return new IncorrectCommand().execute(request, response);
		}
		try {
			DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
			SubjectDao sjDao = dao.getSubectDao();
			Subject sj = sjDao.getById(subjectId);
			LOG.trace("Found in DB: subject --> " + sj);
			if (sj == null) {
				return new IncorrectCommand().execute(request, response);
			}
			List<Subject> subjects = sjDao.getAll();
			TestDao testDao = dao.getTestDao();
			List<Test> tests = testDao.getAllBySubject(sj);
			LOG.trace("Found in DB: testsList --> " + tests);

			String comparatorName = request.getParameter("comparator");
			LOG.trace("Request parameter: comparator --> " + comparatorName);
			// sort by chosen comparator
			Comparator<Test> comparator = ComparatorContainer.get(comparatorName);
			if (comparator != null) {
				Collections.sort(tests, comparator);
			}
			HttpSession session = request.getSession();
			session.setAttribute("tests", tests);
			LOG.trace("Set the request attribute: tests --> " + tests);

			request.setAttribute("subjects", subjects);
			LOG.trace("Set the request attribute: subjects --> " + subjects);
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not view tests by subject", e);
		}
		LOG.debug("Command finished");
		return Path.PAGE_VIEW_TESTS;
	}

}
