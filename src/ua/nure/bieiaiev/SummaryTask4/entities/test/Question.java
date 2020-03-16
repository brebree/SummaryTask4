package ua.nure.bieiaiev.SummaryTask4.entities.test;

import java.util.List;

import ua.nure.bieiaiev.SummaryTask4.entities.Entity;

/**
 * Component of Test Bean. Contains getters and setters for Question entity.
 * 
 * @author D.Bieliaiev
 *
 */
public class Question extends Entity {

	private static final long serialVersionUID = 1017310282674253093L;

	private String questionText;
	private List<Answer> answerList;

	public Question() {
		super();
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public List<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Question) {
			Question question = (Question) obj;
			if (getQuestionText().equals(question.getQuestionText()) && getId() == question.getId()) {
				if (getAnswerList().size() == question.getAnswerList().size()) {
					for (int i = 0; i < getAnswerList().size(); i++) {
						if (!getAnswerList().get(i).equals(question.getAnswerList().get(i))) {
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = result * 37 + this.getId();
		result = result * 37 + (questionText == null ? 0 : questionText.hashCode());
		result = result * 37 + (answerList == null ? 0 : answerList.hashCode());
		return result;
	}
}
