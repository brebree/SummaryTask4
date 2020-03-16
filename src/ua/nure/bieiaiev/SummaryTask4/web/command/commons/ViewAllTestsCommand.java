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
 * Command to view all existing tests
 * 
 * @author D.Bieliaiev
 *
 */
public class ViewAllTestsCommand extends Command {

	private static final long serialVersionUID = -5473023992050280305L;

	private static final Logger LOG = Logger.getLogger(ViewAllTestsCommand.class);

	/**
	 * Executes View All Tests Command
	 * 
	 * @return {@link String} address to go forward
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");
		try {
			HttpSession session = request.getSession();
			List<Test> tests = null;
			if (!"s".equals(request.getParameter("prefix"))) {
				TestDao testDao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getTestDao();
				tests = testDao.getAll();
				LOG.trace("Found in DB: testsList --> " + tests);
			} else {
				tests = (List<Test>) session.getAttribute("tests");
			}
			String comparatorName = request.getParameter("comparator");
			// if there is a sorter chosen on a page - sort , by default
			// sort by
			// subject ID
			Comparator<Test> comparator = ComparatorContainer.get(comparatorName);
			if (comparator == null) {
				Collections.sort(tests, new Comparator<Test>() {
					public int compare(Test o1, Test o2) {
						return o1.getTestSubjectId() - o2.getTestSubjectId();
					}
				});
			} else {
				Collections.sort(tests, comparator);
			}
			if (request.getParameter("condition") != null) {
				request.setAttribute("condition", "over");
			}
			session.setAttribute("tests", tests);
			LOG.trace("Set the request attribute: tests --> " + tests);

			SubjectDao sjDao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getSubectDao();
			List<Subject> subjects = sjDao.getAll();
			request.setAttribute("subjects", subjects);
			LOG.trace("Set the request attribute: subjects --> " + subjects);
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not view all tests", e);
		}
		LOG.debug("Command finished");
		return Path.PAGE_VIEW_TESTS;
	}

}
