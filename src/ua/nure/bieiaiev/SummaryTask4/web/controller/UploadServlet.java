package ua.nure.bieiaiev.SummaryTask4.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
import ua.nure.bieiaiev.SummaryTask4.exceptions.Messages;
import ua.nure.bieiaiev.SummaryTask4.utils.ReportUtil;

/**
 * Servlet for downloading user's reports
 * 
 * @author D.Bieliaiev
 *
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 8719079355364046803L;

	private static final Logger LOG = Logger.getLogger(UploadServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.debug("Upload servlet starts");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		LOG.trace("Get the session attribute: user -->" + user);

		List<UsersTest> usersTests = null;
		List<Subject> sjList = null;
		String format = request.getParameter("format");
		LOG.trace("Get the request attribute: format -->" + format);
		try {
			usersTests = getUsersTests(user);
			sjList = getSubjects();
			if (!isValid(format)) {
				throw new ApplicationException(Messages.ERR_CANNOT_DOWNLOAD_FILE);
			}
		} catch (ApplicationException e) {
			LOG.error(Messages.ERR_CANNOT_DOWNLOAD_FILE, e);
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
		}

		File file = null;
		String fileName = user.getLogin();

		try {
			if ("PDF".equals(format)) {
				fileName = fileName + ".pdf";
				file = new File(request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileName);
				ReportUtil util = new ReportUtil();
				util.writePDF(file, user, usersTests, sjList);
			} else if ("XML".equals(format)) {
				fileName = fileName + ".xml";
				file = new File(request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileName);
				ReportUtil util = new ReportUtil();
				util.writeXML(file, usersTests);
			} else if ("DOC".equals(format)) {
				fileName = fileName + ".docx";
				file = new File(request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileName);
				ReportUtil util = new ReportUtil();
				util.writeDOC(user, file, usersTests, sjList);
			}
		} catch (ApplicationException e) {
			LOG.error(Messages.ERR_CANNOT_DOWNLOAD_FILE, e);
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
		}

		LOG.trace("File location on server::" + file.getAbsolutePath());
		ServletContext ctx = getServletContext();
		InputStream fis = new FileInputStream(file);
		String mimeType = ctx.getMimeType(file.getAbsolutePath());
		response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		ServletOutputStream os = response.getOutputStream();
		byte[] bufferData = new byte[1024];
		int read = 0;
		while ((read = fis.read(bufferData)) != -1) {
			os.write(bufferData, 0, read);
		}
		os.flush();
		os.close();
		fis.close();
		file.delete();
		LOG.trace("File downloaded at client successfully");

		LOG.debug("Upload servlet finished");
	}

	private List<UsersTest> getUsersTests(User user) throws DatabaseException {
		UsersTestDao dao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getUsersTestDao();
		List<UsersTest> list = dao.getAllUsersTestsByUser(user);
		LOG.trace("Found in DB: usersList -->" + list);
		return list;
	}

	private List<Subject> getSubjects() throws DatabaseException {
		SubjectDao dao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getSubectDao();
		List<Subject> list = dao.getAll();
		LOG.trace("Found in DB: usersList -->" + list);
		return list;
	}

	private boolean isValid(String format) {
		if ("PDF".equals(format) || "XML".equals(format) || "DOC".equals(format)) {
			return true;
		} else {
			return false;
		}
	}
}
