package ua.nure.bieiaiev.SummaryTask4.web.command.student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UserDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UsersTestDao;
import ua.nure.bieiaiev.SummaryTask4.entities.User;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Answer;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Question;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Test;
import ua.nure.bieiaiev.SummaryTask4.entities.test.UsersTest;
import ua.nure.bieiaiev.SummaryTask4.exceptions.ApplicationException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.utils.ComparatorContainer;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command creates User's Test in DB and calculating User's Tests result.
 * 
 * @author D.Bieliaiev
 *
 */
public class SubmitPassTestCommand extends Command {

	private static final long serialVersionUID = -6109198501527623860L;
	private static final Logger LOG = Logger.getLogger(SubmitPassTestCommand.class);

	/**
	 * Executes Submit Pass Test Command
	 * 
	 * @return {@link String} address to redirect on page with results
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		LOG.debug("Command starts");

		Integer id = Integer.parseInt(request.getParameter("id"));
		LOG.trace("Request parameter: id --> " + id);

		Test test = DaoFactory.getDaoFactory(DaoFactory.DERBY).getTestDao().getById(id);
		LOG.trace("Found in DB: test --> " + test);
		// sort questions and answers by ID
		Collections.sort(test.getQuestionList(), new ComparatorContainer.QuestionsListComporator());

		HttpSession session = request.getSession();
		User user = (User) (session.getAttribute("user"));
		LOG.trace("Get the session attribute: user --> " + user);
		// get user's test from the request
		UsersTest usersTest = extractUsersTest(request, test, user);
		// calculate test result
		Integer testResult = compare(usersTest, test);
		usersTest.setTestResult(testResult);
		// save test in DB
		UsersTestDao usersTestDao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getUsersTestDao();
		usersTestDao.create(usersTest);
		LOG.trace("Insert to DB: usersTest --> " + usersTest);

		updateUsersResult(user, testResult);

		session.setAttribute("test", test);
		LOG.trace("Set the session attribute: test --> " + test);

		session.setAttribute("usersTest", usersTest);
		LOG.trace("Sert the session attribute: usersTest --> " + usersTest);

		LOG.debug("Command finished");
		return Path.COMMAND_VIEW_TEST_RESULT;
	}

	/**
	 * Extracts user's test from the old test , request contains user's answers
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param test
	 *            {@link Test} Model
	 * @param user
	 *            {@link User} who passed {@link Test}
	 * @return {@link UsersTest} with user's answers
	 */
	private UsersTest extractUsersTest(HttpServletRequest request, Test test, User user) {
		// init test model
		UsersTest usersTest = new UsersTest();
		usersTest.setTestId(test.getId());
		usersTest.setUsersId(user.getId());
		usersTest.setQuestionList(new ArrayList<>());
		for (Question question : test.getQuestionList()) {
			Question newQuestion = new Question();
			newQuestion.setId(question.getId());
			newQuestion.setQuestionText(question.getQuestionText());
			newQuestion.setAnswerList(new ArrayList<Answer>());
			for (Answer answer : question.getAnswerList()) {
				Answer newAnswer = new Answer();
				newAnswer.setId(answer.getId());
				newAnswer.setAnswerText(answer.getAnswerText());
				newQuestion.getAnswerList().add(newAnswer);
			}
			usersTest.getQuestionList().add(newQuestion);
		}
		// init user's answers
		initUsersAnswers(request, usersTest);
		return usersTest;
	}

	/**
	 * Get's user's answers from the request and sets them to UsersTest
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param usersTest
	 *            {@link UsersTest} of current {@link User}
	 */
	private void initUsersAnswers(HttpServletRequest request, UsersTest usersTest) {
		List<Question> questionsList = usersTest.getQuestionList();
		for (Question question : questionsList) {
			for (Answer answer : question.getAnswerList()) {
				String checkboxPar = "box_" + question.getId() + "_" + answer.getId();
				String checkbox = request.getParameter(checkboxPar);
				LOG.trace("Request parameter: " + checkboxPar + " --> " + checkbox);
				if ("correct".equals(checkbox)) {
					answer.setCorrect(true);
				} else {
					answer.setCorrect(false);
				}
			}
		}
	}

	/**
	 * Updates user's average result in DB
	 * 
	 * @param user
	 *            {@link User} who passed {@link Test}
	 * @param testResult
	 *            current UsersTest result
	 * @throws DatabaseException
	 */
	private void updateUsersResult(User user, Integer testResult) throws DatabaseException {
		UserDao userDao = DaoFactory.getDaoFactory(DaoFactory.DERBY).getUserDao();
		Integer result = user.getTestsResult();

		Integer testsCount = user.getTestsCount();

		Integer totalResult = (testResult + result * testsCount) / (testsCount + 1);
		user.setTestsCount(testsCount + 1);
		user.setTestsResult(totalResult);
		userDao.update(user);
	}

	/**
	 * Compares User's Test with model (right test)
	 * 
	 * @param usersTest
	 *            {@link UsersTest}
	 * @param test
	 *            {@link Test} model
	 * @return percentage of right answers
	 */
	private Integer compare(UsersTest usersTest, Test test) {
		List<Question> userQ = usersTest.getQuestionList();
		List<Question> testQ = test.getQuestionList();

		Collections.sort(userQ, new ComparatorContainer.QuestionsListComporator());

		Collections.sort(testQ, new ComparatorContainer.QuestionsListComporator());

		int countQuestions = userQ.size();
		int countCorrect = 0;
		for (int i = 0; i < countQuestions; i++) {
			if (userQ.get(i).equals(testQ.get(i))) {
				countCorrect++;
			}
		}
		return (countCorrect * 100) / countQuestions;
	}

}
