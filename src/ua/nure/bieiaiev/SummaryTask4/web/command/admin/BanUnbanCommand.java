package ua.nure.bieiaiev.SummaryTask4.web.command.admin;

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
 * Command to ban or unban user
 * 
 * @author D.Bieliaiev
 *
 */
public class BanUnbanCommand extends Command {

	private static final long serialVersionUID = -2360336866466589331L;

	private static final Logger LOG = Logger.getLogger(BanUnbanCommand.class);

	/**
	 * Executes Ban/Unban Command
	 * 
	 * @return {@link String} address to redirect
	 * @throws ApplicationException
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");
		Integer userId = Integer.parseInt(request.getParameter("id"));
		LOG.trace("Request parameter: id --> " + userId);
		try {
			UserDao dao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getUserDao();
			User userToBan = dao.getById(userId);
			LOG.trace("Found in DB: user -->" + userToBan);
			// set user status to "banned" if it is unbanned or vice versa
			if (userToBan != null) {
				int statusId = userToBan.getStatusId();
				if (statusId == 1) {
					userToBan.setStatusId(2);
				} else {
					userToBan.setStatusId(1);
				}
				dao.update(userToBan);
				LOG.trace("Update in DB -->" + userToBan);
			}
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not change user's status", e);
		}
		LOG.debug("Command finished");

		return Path.COMMAND_VIEW_USERS;
	}

}
