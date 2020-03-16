package ua.nure.bieiaiev.SummaryTask4.entities.test;

import ua.nure.bieiaiev.SummaryTask4.entities.Entity;

/**
 * Component of Test Bean. Contains getters and setters for Answer entity.
 * 
 * @author D.Bieliaiev
 *
 */
public class Answer extends Entity {

	private static final long serialVersionUID = -3960861823534663941L;

	private String answerText;
	private boolean correct;

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public Answer() {
		super();
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Answer) {
			Answer answer = (Answer) obj;
			if (getAnswerText().equals(answer.getAnswerText()) && isCorrect() == answer.isCorrect()
					&& getId() == answer.getId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = result * 37 + getId();
		result = result + 37 + (answerText == null ? 0 : answerText.hashCode());
		result = result + 37 + (correct ? 0 : 1);
		return result;
	}
	
}
