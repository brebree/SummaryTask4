package ua.nure.bieiaiev.SummaryTask4.web.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Initializes application
 * 
 * @author D.Bieliaiev
 *
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
		initCommandContainer();
		initFileLocation(servletContext);

		log("Servlet context initialization finished");
	}

	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			ex.printStackTrace();
		}
		log("Log4J initialization finished");
	}

	private void initCommandContainer() {

		try {
			Class.forName("ua.nure.bieiaiev.SummaryTask4.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Cannot initialize Command Container");
		}
	}

	private void initFileLocation(ServletContext ctx) {
		String rootPath = System.getProperty("catalina.home");

		String relativePath = ctx.getInitParameter("tempfile.dir");

		File file = new File(rootPath + File.separator + relativePath);

		if (!file.exists()) {
			file.mkdirs();
		}
		System.out.println("File Directory created to be used for storing files");

		ctx.setAttribute("FILES_DIR_FILE", file);

		ctx.setAttribute("FILES_DIR", rootPath + File.separator + relativePath);

	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}

	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");

		log("Servlet context destruction finished");
	}
}