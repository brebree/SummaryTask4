package ua.nure.bieiaiev.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UserDao;
import ua.nure.bieiaiev.SummaryTask4.entities.User;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.utils.MailUtil;

/**
 * Command to register a new user
 * 
 * @author D.Bieliaiev
 *
 */
public class SubmitRegisterCommand extends Command {

	private static final long serialVersionUID = -2608142576930432667L;

	private static final Logger LOG = Logger.getLogger(SubmitRegisterCommand.class);

	/**
	 * Executes Submit Register Command
	 * 
	 * @return {@link String} address to redirect
	 * @throws ApplicationException
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");
		// get parameters from the request and build User entity
		User user = new User();
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);
		user.setLogin(login);
		user.setPassword(request.getParameter("password"));
		String firstName = request.getParameter("firstName");
		LOG.trace("Requst parameter: firstName --> " + firstName);
		user.setFirstName(firstName);
		String lastName = request.getParameter("lastName");
		LOG.trace("Request parameter: lastName --> " + lastName);
		user.setLastName(lastName);
		String email = request.getParameter("email");
		LOG.trace("Request parameter: email --> " + email);
		user.setEmail(email);
		//if email exists - send a greetings letter
		if (email != null && !email.isEmpty()) {
			MailUtil.sendMail(email, login, firstName, lastName);
		}
		user.setRoleId(1);
		user.setStatusId(2);
		user.setTestsResult(0);
		String forward = Path.PAGE_LOGIN;
		try {
			UserDao dao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getUserDao();
			// if success redirect to login page
			if (dao.getUserByLogin(login) != null) {
				/*
				 * if user is already exists - > set the warn attribute to
				 * session scope - > redirect to register page
				 */
				HttpSession session = request.getSession();
				session.setAttribute("incorrectLogin", login);
				LOG.warn("User with such login already exists");
				LOG.trace("Set the session attribute: incorrectLogin -->" + login);
				forward = Path.COMMAND_VIEW_REGISTER;
			} else {
				// else add new user to db
				dao.create(user);
				LOG.trace("Insert to DB: user --> " + user);
			}
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not register user", e);
		}
		LOG.debug("Command finished");
		return forward;
	}

}
