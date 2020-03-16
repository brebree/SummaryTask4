package ua.nure.bieiaiev.SummaryTask4.entities.test;

import java.util.List;

import ua.nure.bieiaiev.SummaryTask4.entities.Entity;

/**
 * Test Bean. Contains getters and setters for Test entity.
 * 
 * @author D.Bieliaiev
 *
 */
public class Test extends Entity {

	private static final long serialVersionUID = -299407905924716375L;

	private String testName;
	private Integer testSubjectId;
	private Long testTime;
	private Integer complexity;
	private List<Question> questionList;

	public Test() {
		super();
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Integer getTestSubjectId() {
		return testSubjectId;
	}

	public void setTestSubjectId(Integer testSubjectId) {
		this.testSubjectId = testSubjectId;
	}

	public long getTestTime() {
		return testTime;
	}

	public void setTestTime(Long testTime) {
		this.testTime = testTime;
	}

	public Integer getComplexity() {
		return complexity;
	}

	public void setComplexity(Integer complexity) {
		this.complexity = complexity;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	@Override
	public String toString() {
		return "Test [testName=" + testName + ", testSubjectId=" + testSubjectId + ", complexity=" + complexity + "]";
	}
	
}
