package ua.nure.bieiaiev.SummaryTask4.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;
import ua.nure.bieiaiev.SummaryTask4.web.command.CommandContainer;

/**
 * Application controller
 * 
 * @author D.Bieliaiev
 *
 */
public class Controller extends HttpServlet {

	private static final long serialVersionUID = -7106827138587957535L;

	private static final Logger LOG = Logger.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * Processes request. Invokes commands contained in request.
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @throws IOException
	 * @throws ServletException
	 */
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		LOG.debug("Controller starts");

		String commandName = request.getParameter("command");
		LOG.trace("Request parameter: command --> " + commandName);

		Command command = CommandContainer.get(commandName);
		LOG.trace("Obtained command --> " + command);

		String forward = Path.PAGE_ERROR_PAGE;
		// PRG
		boolean redirect = false;
		if (request.getMethod().equals("POST")) {
			redirect = true;
		}
		try {
			forward = command.execute(request, response);
		} catch (ApplicationException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			redirect = false;
		}

		if (request.getMethod().equals("POST") && redirect) {
			LOG.trace("Redirect address --> " + forward);

			LOG.debug("Controller finished, now redirect to address --> " + forward);
			response.sendRedirect(forward);
		} else if (commandName.equals("logout")) {
			response.sendRedirect(forward);
		} else {
			LOG.trace("Forward address --> " + forward);

			LOG.debug("Controller finished, now go to forward address --> " + forward);

			request.getRequestDispatcher(forward).forward(request, response);
		}
	}
}
