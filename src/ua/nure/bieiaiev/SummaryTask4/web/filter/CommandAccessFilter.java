package ua.nure.bieiaiev.SummaryTask4.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.entities.RoleEnum;

/**
 * Filter controls access to commands
 * 
 * @author D.Bieliaiev
 *
 */
public class CommandAccessFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

	private Map<RoleEnum, List<String>> resricted = new HashMap<RoleEnum, List<String>>();
	private List<String> common = new ArrayList<String>();
	private List<String> outOfControl = new ArrayList<String>();

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization starts");

		resricted.put(RoleEnum.ADMIN, asList(fConfig.getInitParameter("admin")));
		resricted.put(RoleEnum.STUDENT, asList(fConfig.getInitParameter("student")));
		LOG.trace("Restricted access map --> " + resricted);

		common = asList(fConfig.getInitParameter("common"));
		LOG.trace("Common commands --> " + common);

		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		LOG.trace("Out of control commands --> " + outOfControl);

		LOG.debug("Filter initialization finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("Filter starts");

		if (accessAllowed(request)) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";

			request.setAttribute("errorMessage", errorMessasge);
			LOG.trace("Set the request attribute: errorMessage --> " + errorMessasge);

			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
		}
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		if (commandName == null || commandName.isEmpty()) {
			return false;
		}

		if (outOfControl.contains(commandName)) {
			return true;
		}

		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			return false;
		}
		RoleEnum userRole = (RoleEnum) session.getAttribute("userRole");
		if (userRole == null) {
			return false;
		}

		return resricted.get(userRole).contains(commandName) || common.contains(commandName);
	}

	public void destroy() {
		LOG.debug("Filter destruction starts");

		LOG.debug("Filter destruction finished");
	}

	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}

}