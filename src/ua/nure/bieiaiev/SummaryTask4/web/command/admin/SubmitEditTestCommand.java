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
 * Command to edit Test in DB
 * 
 * @author D.Bieliaiev
 *
 */
public class SubmitEditTestCommand extends Command {

	private static final long serialVersionUID = -5878265343877323058L;

	private static final Logger LOG = Logger.getLogger(SubmitEditTestCommand.class);

	/**
	 * Executes Submit Edit Test Command
	 * 
	 * @return {@link String} address to redirect
	 * @throws ApplicationException
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");
		try {
			HttpSession session = request.getSession();

			DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
			TestDao testDao = dao.getTestDao();

			Test oldTest = (Test) session.getAttribute("test");
			LOG.trace("Request parameter: test --> " + oldTest);
			Test test = extractTest(request, session, dao, oldTest);

			testDao.update(test);
			LOG.trace("Update in DB: test --> " + test);
		} catch (DatabaseException e) {
			throw new ApplicationException("Can not edit test", e);
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
	 * @param oldTest
	 *            {@link Test} with old fields except deleted
	 * @return {@link Test} to create
	 * @throws ApplicationException
	 */
	private Test extractTest(HttpServletRequest request, HttpSession session, DaoFactory dao, Test oldTest)
			throws ApplicationException {
		Test test = new Test();
		SubjectDao sjDao = dao.getSubectDao();
		test.setId(oldTest.getId());
		String testName = null;
		String sjName = null;
		Integer sjId = null;
		Integer textComplexity = null;
		Long testTime = null;
		String testNamePar = request.getParameter("testName");
		LOG.trace("Request parameter: testName --> " + testNamePar);
		// if there is no new test name - set old name
		if (testNamePar != null && testNamePar != "") {
			testName = request.getParameter("testName");
		} else {
			testName = oldTest.getTestName();
		}
		String sjNamePar = request.getParameter("subject");
		LOG.trace("Request parameter: subject --> " + sjNamePar);
		// if there is no new subject name - set old subject, if new subject
		// doesn't exists in DB -> insert it to DB
		if (sjNamePar != null && sjNamePar != "") {
			sjName = sjNamePar;
			@SuppressWarnings("unchecked")
			List<Subject> subjects = (List<Subject>) session.getAttribute("subjects");
			LOG.trace("Get the session attribute: subjects -->" + subjects);
			if (subjects != null && subjects.size() != 0) {
				sjId = sjDao.getSubjectByName(sjName).getId();
			} else {
				Subject sj = new Subject();
				sj.setName(sjName);
				sjDao.create(sj);
				LOG.trace("Insert to DB: subject --> " + sj);
				sjId = sjDao.getSubjectByName(sjName).getId();
			}
		} else {
			sjId = oldTest.getTestSubjectId();
		}
		// if there is no new test complexity - set old one
		if (request.getParameter("complexity") != null && request.getParameter("complexity") != "") {
			textComplexity = Integer.parseInt(request.getParameter("complexity"));
		} else {
			textComplexity = oldTest.getComplexity();
		}
		// if there is no new test time - set old one
		if (request.getParameter("time") != null && request.getParameter("time") != "") {
			testTime = Long.parseLong(request.getParameter("time"));
		} else {
			testTime = oldTest.getTestTime();
		}
		test.setTestName(testName);
		test.setComplexity(textComplexity);
		test.setTestSubjectId(sjId);
		test.setTestTime(testTime);
		Enumeration<String> params = request.getParameterNames();
		LOG.trace("Request parameter names: -->" + params);
		List<Question> questionsList = extractQuestionList(request, params, oldTest);
		test.setQuestionList(questionsList);
		return test;
	}

	/**
	 * Extracts questions list from the request
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param params
	 *            {@link Enumeration} of parameter names
	 * @param oldTest
	 *            {@link Test} with old fields except deleted
	 * @return {@link List} of {@link Question}
	 */
	private List<Question> extractQuestionList(HttpServletRequest request, Enumeration<String> params, Test oldTest) {
		List<Question> questionsList = new ArrayList<>();
		List<Question> oldQuestions = oldTest.getQuestionList();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String[] paramNameArr = param.split("_");
			if (paramNameArr[0].equals("question")) {
				Question question = new Question();
				Integer questionId = Integer.parseInt(paramNameArr[1]);
				question.setId(questionId);
				// if there is no new question test - set old one
				if (request.getParameter(param) != null && (!request.getParameter(param).equals(""))) {
					question.setQuestionText(request.getParameter(param));
				} else {
					for (Question oldQuestion : oldQuestions) {
						if (question.getId() == oldQuestion.getId()) {
							question.setQuestionText(oldQuestion.getQuestionText());
						}
					}
				}
				questionsList.add(question);
			}
		}
		for (int i = 0; i < questionsList.size(); i++) {
			Question question = questionsList.get(i);
			params = request.getParameterNames();
			List<Answer> answersList = extractAnswerList(request, params, oldTest, question);
			question.setAnswerList(answersList);
		}

		return questionsList;
	}

	/**
	 * Extracts answers list of the question from the request
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param params
	 *            {@link Enumeration}
	 * @param oldTest
	 *            {@link Test} with old fields except deleted
	 * @param question
	 *            {@link Question}
	 * @return {@link List} of {@link Answer}
	 */
	private List<Answer> extractAnswerList(HttpServletRequest request, Enumeration<String> params, Test oldTest,
			Question question) {
		List<Answer> answersList = new ArrayList<>();
		Integer questionId = question.getId();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String[] paramNameArr = param.split("_");
			if (paramNameArr[0].equals("answer") && paramNameArr[1].equals(String.valueOf(questionId))) {
				Answer answer = new Answer();
				answer.setId(Integer.parseInt(paramNameArr[2]));
				// if there is no new answer text - set old one
				if (request.getParameter(param) != null && (!request.getParameter(param).equals(""))) {
					answer.setAnswerText(request.getParameter(param));
				} else {
					for (Question oldQuestion : oldTest.getQuestionList()) {
						if (question.getId() == oldQuestion.getId()) {
							for (Answer oldAnswer : oldQuestion.getAnswerList()) {
								if (answer.getId() == oldAnswer.getId()) {
									answer.setAnswerText(oldAnswer.getAnswerText());
								}
							}
						}
					}
				}
				// set correct values to answers
				String checkboxName = "box_" + paramNameArr[1] + "_" + paramNameArr[2];
				String correct = request.getParameter(checkboxName);
				LOG.trace("Request parameter: " + checkboxName + " --> " + correct);
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
