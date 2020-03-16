package ua.nure.bieiaiev.SummaryTask4.web.command.admin;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.Path;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Answer;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Question;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Test;
import ua.nure.bieiaiev.SummaryTask4.utils.ComparatorContainer;
import ua.nure.bieiaiev.SummaryTask4.web.command.Command;

/**
 * Command deletes chosen answer while editing test
 * 
 * @author D.Bieliaiev
 *
 */
public class DeleteAnswerCommand extends Command {

	private static final long serialVersionUID = 3029245691092431344L;

	private static final Logger LOG = Logger.getLogger(DeleteAnswerCommand.class);

	/**
	 * Executes Delete Answer Command. Answer won't be deleted from DB while
	 * user don't submit test editing
	 * 
	 * @return {@link String} address to go forward
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");
		// get answer's id
		String id = request.getParameter("id");
		LOG.trace("Request parameter: id --> " + id);
		// button has name pattern -> questionID_answerID
		String[] param = id.split("_");
		Integer questionId = Integer.parseInt(param[0]);
		Integer answerId = Integer.parseInt(param[1]);

		HttpSession session = request.getSession();

		Test test = (Test) session.getAttribute("test");
		LOG.trace("Get the session attribute: test --> " + test);
		List<Question> questionList = test.getQuestionList();
		List<Answer> answers = null;
		// delete chosen answer from the test
		for (int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			if (question.getId() == questionId) {
				answers = question.getAnswerList();
				for (int j = 0; j < answers.size(); j++) {
					if (answers.get(j).getId() == answerId) {
						answers.remove(j);
					}
				}
				// if there are no answers in question - delete question
				if (question.getAnswerList().isEmpty()) {
					questionList.remove(i);
				}
			}
		}
		// sort answers by IDs
		Collections.sort(questionList, new ComparatorContainer.QuestionsListComporator() {
		});
		// give a new IDs to answers by their position
		for (int i = 0; i < answers.size(); i++) {
			answers.get(i).setId(i + 1);
		}
		// give a new IDs to questions by their position
		for (int i = 0; i < questionList.size(); i++) {
			questionList.get(i).setId(i + 1);
		}
		session.setAttribute("test", test);
		LOG.trace("Set the session attribute: test -->" + test);

		LOG.debug("Command finished");
		return Path.PAGE_VIEW_EDIT_TEST;
	}

}
