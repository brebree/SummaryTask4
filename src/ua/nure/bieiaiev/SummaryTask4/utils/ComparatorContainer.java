package ua.nure.bieiaiev.SummaryTask4.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import ua.nure.bieiaiev.SummaryTask4.entities.test.Answer;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Question;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Test;

/**
 * Comparators for business logic
 * 
 * @author D.Bieliaiev
 *
 */
public abstract class ComparatorContainer implements Serializable{

	private static final long serialVersionUID = 405472320439483301L;
	
	private static Map<String, Comparator<Test>> comparators = new HashMap<>();

	static {
		comparators.put("sortByName", new ComporatorByName());
		comparators.put("sortByComplexity", new ComporatorByComplexity());
		comparators.put("sortByQuestionsQuantity", new ComporatorByQuestionsQuantity());
	}

	/**
	 * Gets comparator by it's name defined in the request.
	 * 
	 * @param comparatorName
	 * @return
	 */
	public static Comparator<Test> get(String comparatorName) {
		if (comparatorName == null || !comparators.containsKey(comparatorName)) {
			return null;
		}

		return comparators.get(comparatorName);
	}

	/**
	 * Comparator to sort Tests by name
	 * 
	 * @author D.Bieliaiev
	 *
	 */
	public static class ComporatorByName implements Comparator<Test> {

		@Override
		public int compare(Test test1, Test test2) {
			return test1.getTestName().compareTo(test2.getTestName());
		}

	}

	/**
	 * Comparator to sort Tests by complexity
	 * 
	 * @author D.Bieliaiev
	 *
	 */
	public static class ComporatorByComplexity implements Comparator<Test> {

		@Override
		public int compare(Test test1, Test test2) {
			return test1.getComplexity() - test2.getComplexity();
		}

	}

	/**
	 * Comparator to sort Tests by questions quantity
	 * 
	 * @author D.Bieliaiev
	 *
	 */
	public static class ComporatorByQuestionsQuantity implements Comparator<Test> {

		@Override
		public int compare(Test test1, Test test2) {
			return test1.getQuestionList().size() - test2.getQuestionList().size();
		}

	}

	/**
	 * Comparator to sort Test's questionList by questions ID and answers by
	 * answers ID
	 * 
	 * @author D.Bieliaiev
	 *
	 */
	public static class QuestionsListComporator implements Comparator<Question> {

		@Override
		public int compare(Question o1, Question o2) {
			Collections.sort(o1.getAnswerList(), new AnswersListComporator());
			Collections.sort(o2.getAnswerList(), new AnswersListComporator());
			return o1.getId() - o2.getId();
		}
	}

	/**
	 * Comparator to sort Answers by their ID's
	 * 
	 * @author D.Bieliaiev
	 *
	 */
	public static class AnswersListComporator implements Comparator<Answer> {

		@Override
		public int compare(Answer o1, Answer o2) {
			return o1.getId() - o2.getId();
		}
	}
}
