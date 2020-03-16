package ua.nure.bieiaiev.SummaryTask4.web.command.admin;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.SubjectDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.TestDao;
import ua.nure.bieiaiev.SummaryTask4.entities.Subject;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Answer;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Question;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Test;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command creates new Test in DB
 * 
 * @author D.Bieliaiev
 *
 */
public class SubmitCreateTestCommand extends Command {

	private static final long serialVersionUID = 7264540474202932637L;

	private static final Logger LOG = Logger.getLogger(SubmitCreateTestCommand.class);

	/**
	 * Executes Submit Create Test Command
	 * 
	 * @return {@link String} address to redirect
	 * @throws ApplicationException
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		try {
			DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
			Test test = extractTest(request, session, dao);

			TestDao testDao = dao.getTestDao();
			testDao.create(test);
			LOG.trace("Insert to DB: test -->" + test);
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not create test", e);
		}
		LOG.debug("Command finished");
		return Path.COMMAND_VIEW_TESTS;
	}

	/**
	 * Extracts Test from the request. Gets test's subject from session if
	 * exists, or creates new subject if not
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param session
	 *            {@link HttpSession}
	 * @param dao
	 *            {@link DaoFactory}
	 * @return {@link Test} to create
	 * @throws DatabaseException
	 */
	private Test extractTest(HttpServletRequest request, HttpSession session, DaoFactory dao) throws DatabaseException {
		Test test = new Test();
		String testName = request.getParameter("testName");
		LOG.trace("Request parameter: testName --> " + testName);
		Integer testComplexity = Integer.parseInt(request.getParameter("complexity"));
		LOG.trace("Request parameter: complexity --> " + testComplexity);
		SubjectDao sjDao = dao.getSubectDao();
		String sjName = request.getParameter("subject");
		LOG.trace("Request parameter: subject -->" + sjName);
		@SuppressWarnings("unchecked")
		List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");
		LOG.trace("Get the session attribute: subjects -->" + subjects);
		Integer sjId = null;
		if (subjects != null && subjects.size() != 0) {
			for (Subject subject : subjects) {
				if (subject.getName().equals(sjName)) {
					sjId = subject.getId();
				}
			}
		}
		if (sjId == null) {
			Subject sj = new Subject();
			sj.setName(sjName);
			sjDao.create(sj);
			LOG.trace("Insert to DB: subject --> " + sj);
			sjId = sjDao.getSubjectByName(sjName).getId();
		}
		Long testTime = Long.parseLong(request.getParameter("time"));
		LOG.trace("Request parameter: time --> " + testTime);
		test.setTestName(testName);
		test.setComplexity(testComplexity);
		test.setTestSubjectId(sjId);
		test.setTestTime(testTime);
		Enumeration<String> params = request.getParameterNames();
		LOG.trace("Request parameter names: -->" + params);
		List<Question> questionsList = extractQuestionList(request, params);
		test.setQuestionList(questionsList);
		return test;
	}

	/**
	 * Extracts question list from the request
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param params
	 *            {@link Enumeration} of request parameters
	 * @return {@link List} of {@link Question}
	 */
	private List<Question> extractQuestionList(HttpServletRequest request, Enumeration<String> params) {
		List<Question> questionsList = new ArrayList<>();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String[] paramNameArr = param.split("_");
			if (paramNameArr[0].equals("question")) {
				Question question = new Question();
				question.setId(Integer.parseInt(paramNameArr[1]));
				question.setQuestionText(request.getParameter(param));
				questionsList.add(question);
			}
		}
		for (Question question : questionsList) {
			params = request.getParameterNames();
			List<Answer> answersList = extractAnswerList(request, params, question);
			question.setAnswerList(answersList);
		}
		return questionsList;
	}

	/**
	 * Extracts answer list of a question from the request
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param params
	 *            {@link Enumeration} of request parameters
	 * @param question
	 *            {@link Question}
	 * @return {@link List} of {@link Answer}
	 */
	private List<Answer> extractAnswerList(HttpServletRequest request, Enumeration<String> params, Question question) {
		List<Answer> answersList = new ArrayList<>();
		Integer questionId = question.getId();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String[] paramNameArr = param.split("_");
			if (paramNameArr[0].equals("answer") && paramNameArr[1].equals(String.valueOf(questionId))) {
				Answer answer = new Answer();
				answer.setId(Integer.parseInt(paramNameArr[2]));
				answer.setAnswerText(request.getParameter(param));
				String checkboxName = "box_" + paramNameArr[1] + "_" + paramNameArr[2];
				String correct = request.getParameter(checkboxName);
				if ("correct".equals(correct)) {
					answer.setCorrect(true);
				} else {
					answer.setCorrect(false);
				}
				answersList.add(answer);
			}
		}
		return answersList;
	}
}
