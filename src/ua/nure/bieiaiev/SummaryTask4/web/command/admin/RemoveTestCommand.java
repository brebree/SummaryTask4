package ua.nure.bieiaiev.SummaryTask4.web.command.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.TestDao;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Test;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command deletes chosen Test
 * 
 * @author D.Bieliaiev
 *
 */
public class RemoveTestCommand extends Command {

	private static final long serialVersionUID = 4247185744930650859L;

	private static final Logger LOG = Logger.getLogger(RemoveTestCommand.class);

	/**
	 * Executes Remove Test Command
	 * 
	 * @return {@link String} address to redirect
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");
		try {
			DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
			TestDao testDao = dao.getTestDao();
			Test test = new Test();
			Integer id = Integer.parseInt(request.getParameter("id"));
			test.setId(id);
			LOG.trace("Request parameter: id -->" + id);
			testDao.delete(test);
			LOG.trace("Deleted from DB: test -->" + test);
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not remove test", e);
		}

		LOG.debug("Command finished");
		return Path.COMMAND_VIEW_TESTS;
	}

}
