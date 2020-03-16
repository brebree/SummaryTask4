package ua.nure.bieiaiev.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UserDao;
import ua.nure.bieiaiev.SummaryTask4.entities.RoleEnum;
import ua.nure.bieiaiev.SummaryTask4.entities.User;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;

public class LoginCommand extends Command {

	private static final long serialVersionUID = -6230206037185231519L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	/**
	 * Executes login command
	 * 
	 * @return {@link String} address to redirect
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		// get parameters from the request
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);

		String password = request.getParameter("password");
		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			throw new ApplicationException("Login/password cannot be empty");
		}
		// compare parameters with entities exists in DB
		UserDao userDao = dao.getUserDao();
		User user = userDao.getUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);
		// throw exception if no such user in DB
		if (user == null || !password.equals(user.getPassword())) {
			throw new ApplicationException("Cannot find user with such login/password");
		}
		// throw exception if user's status is "banned"
		if (user.getStatusId() == 1) {
			throw new ApplicationException("You are banned");
		}
		RoleEnum userRole = RoleEnum.getRole(user);
		LOG.trace("userRole --> " + userRole);
		// set user and user's roles into session scope
		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		LOG.debug("Command finished");
		return Path.COMMAND_VIEW_TESTS;
	}

}
