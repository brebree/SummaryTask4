package ua.nure.bieiaiev.SummaryTask4.web.command.admin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UserDao;
import ua.nure.bieiaiev.SummaryTask4.entities.User;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command to view all registered users
 * 
 * @author D.Bieliaiev
 *
 */
public class ViewAllUsersCommand extends Command {

	private static final long serialVersionUID = 3550784812192480522L;

	private static final Logger LOG = Logger.getLogger(ViewAllUsersCommand.class);

	/**
	 * Executes View All Users Command
	 * 
	 * @return {@link String} address to go forward
	 * @throws ApplicationException
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		LOG.debug("Command starts");
		try {
			UserDao userDao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getUserDao();
			List<User> users = userDao.getAll();
			LOG.trace("Found in DB: usersList --> " + users);
			Collections.sort(users, new Comparator<User>() {
				@Override
				public int compare(User o1, User o2) {
					return o1.getId() - o2.getId();
				}
			});

			request.setAttribute("users", users);
			LOG.trace("Set the request attribute: users --> " + users);
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not show all view users page", e);
		}
		LOG.debug("Command finished");
		return Path.PAGE_VIEW_USERS;
	}

}
