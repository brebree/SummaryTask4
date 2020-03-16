package ua.nure.bieiaiev.SummaryTask4.web.command.admin;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Question;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Test;
import ua.nure.bieiaiev.SummaryTask4.utils.ComparatorContainer;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command deletes chosen question while editing test
 * 
 * @author D.Bieliaiev
 *
 */
public class DeleteQuestionCommand extends Command {

	private static final long serialVersionUID = -5504763186959473600L;

	private static final Logger LOG = Logger.getLogger(DeleteQuestionCommand.class);

	/**
	 * Executes Delete Question Command. Question won't be deleted from DB while
	 * user don't submit test editing
	 * 
	 * @return {@link String} address to go forward
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");
		// get question's id
		String id = request.getParameter("id");
		LOG.trace("Request parameter: id --> " + id);
		Integer questionId = Integer.parseInt(id);
		HttpSession session = request.getSession();

		Test test = (Test) session.getAttribute("test");
		LOG.trace("Get the session attribute: test --> " + test);

		List<Question> questions = test.getQuestionList();
		// delete chosen question from the test
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getId() == questionId) {
				questions.remove(i);
			}
		}
		// sort questions by IDs
		Collections.sort(questions, new ComparatorContainer.QuestionsListComporator() {
		});
		// give a new IDs to questions by their position
		for (int i = 0; i < questions.size(); i++) {
			questions.get(i).setId(i + 1);
		}

		session.setAttribute("test", test);
		LOG.trace("Set the session attribute: test -->" + test);

		LOG.debug("Command finished");
		return Path.PAGE_VIEW_EDIT_TEST;
	}

}
