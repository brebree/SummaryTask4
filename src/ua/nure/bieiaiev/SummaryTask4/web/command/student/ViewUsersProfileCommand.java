package ua.nure.bieiaiev.SummaryTask4.web.command.student;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.SubjectDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UsersTestDao;
import ua.nure.bieiaiev.SummaryTask4.entities.Subject;
import ua.nure.bieiaiev.SummaryTask4.entities.User;
import ua.nure.bieiaiev.SummaryTask4.entities.test.UsersTest;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command to view user's profile page
 * 
 * @author D.Bieliaiev
 *
 */
public class ViewUsersProfileCommand extends Command {

	private static final long serialVersionUID = -520273333036940731L;
	private static final Logger LOG = Logger.getLogger(ViewUsersProfileCommand.class);

	/**
	 * Executes View User's Profile Command
	 * 
	 * @return {@link String} address to go forward
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");

		try {
			DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
			UsersTestDao usersTestDao = dao.getUsersTestDao();
			SubjectDao sjDao = dao.getSubectDao();
			HttpSession session = request.getSession();

			User user = (User) session.getAttribute("user");
			LOG.trace("Get the session attribute: user -->" + user);

			List<UsersTest> usersTests = usersTestDao.getAllUsersTestsByUser(user);
			request.setAttribute("usersTests", usersTests);
			LOG.trace("Set the request attribute: usersTests -->" + usersTests);

			List<Subject> subjects = sjDao.getAll();
			request.setAttribute("subjects", subjects);
			LOG.trace("Set the request attribute: subjects --> " + subjects);

		} catch (DatabaseException e) {
			throw new ApplicationException("Can not show user's profile", e);
		}

		LOG.debug("Command finished");
		return Path.PAGE_VIEW_USERS_PROFILE;
	}

}