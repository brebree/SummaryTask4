package ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.TestDao;
import ua.nure.bieiaiev.SummaryTask4.entities.Subject;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Answer;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Question;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Test;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.Messages;

/**
 * Class contains methods that work with Test entity in application and it's
 * representation in Derby DB
 * 
 * @author D.Bieliaiev
 *
 */
public class MysqlTestDao extends MysqlAbstractDao<Test> implements TestDao {

	private static final String SQL_CREATE_TEST_QUERY = "INSERT INTO tests VALUES(DEFAULT,?,?,?,?)";
	private static final String SQL_CREATE_QUESTION_QUERY = "INSERT INTO questions VALUES(?,?,?)";
	private static final String SQL_CREATE_ANSWER_QUERY = "INSERT INTO answers VALUES(?,?,?,?,?)";
	private static final String SQL_UPDATE_TEST_QUERY = "UPDATE tests SET name=?,test_time=?,complexity=?,subject_id=? WHERE id=?";
	private static final String SQL_UPDATE_QUESTION_QUERY = "UPDATE questions SET text=? WHERE id=? AND test_id=?";
	private static final String SQL_UPDATE_ANSWER_QUERY = "UPDATE answers SET text=?,correct=? WHERE id=? AND question_id=? AND test_id=?";
	private static final String SQL_GET_ALL_TEST_QUERY = "SELECT * FROM tests";
	private static final String SQL_GET_BY_ID_TEST_QUERY = "SELECT * FROM tests WHERE id=?";
	private static final String SQL_GET_ALL_BY_SUBJECT_TEST_QUERY = "SELECT * FROM tests WHERE subject_id=?";
	private static final String SQL_GET_ALL_QUESTIONS_ANSWERS_QUERY = "SELECT * FROM answers WHERE question_id=? AND test_id=?";
	private static final String SQL_GET_ALL_TESTS_QUESTIONS_QUERY = "SELECT * FROM questions WHERE test_id=?";

	private static final String SQL_DELETE_REDUNDANT_QUESTIONS = "DELETE FROM questions WHERE test_id=? AND id>?";
	private static final String SQL_DELETE_REDUNDANT_ANSWERS = "DELETE FROM answers WHERE test_id=? AND question_id=? AND id>?";

	/**
	 * Creates new Test in DB
	 * 
	 * @param entity
	 *            {@link Test} to create in DB
	 */
	@Override
	public boolean create(Test entity) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			// create Test in DB
			pstmt = con.prepareStatement(SQL_CREATE_TEST_QUERY, Statement.RETURN_GENERATED_KEYS);
			int k = 0;
			pstmt.setString(++k, entity.getTestName());
			pstmt.setLong(++k, entity.getTestTime());
			pstmt.setInt(++k, entity.getComplexity());
			pstmt.setInt(++k, entity.getTestSubjectId());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					entity.setId(id);
				}
			}
			List<Question> questionList = entity.getQuestionList();
			// create Questions in DB
			for (Question question : questionList) {
				PreparedStatement questioPpstmt = con.prepareStatement(SQL_CREATE_QUESTION_QUERY);
				int j = 0;
				questioPpstmt.setInt(++j, question.getId());
				questioPpstmt.setString(++j, question.getQuestionText());
				questioPpstmt.setInt(++j, entity.getId());
				questioPpstmt.executeUpdate();
				List<Answer> answerList = question.getAnswerList();
				// create Answers for every Question
				for (Answer answer : answerList) {
					PreparedStatement answerPstmt = con.prepareStatement(SQL_CREATE_ANSWER_QUERY);
					int m = 0;
					answerPstmt.setInt(++m, answer.getId());
					answerPstmt.setString(++m, answer.getAnswerText());
					answerPstmt.setBoolean(++m, answer.isCorrect());
					answerPstmt.setInt(++m, entity.getId());
					answerPstmt.setInt(++m, question.getId());
					answerPstmt.executeUpdate();
					close(answerPstmt);
				}
				close(questioPpstmt);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_TEST, e);
			throw new DatabaseException(Messages.ERR_CANNOT_CREATE_TEST, e);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Updates Test in DB
	 * 
	 * @param entity
	 *            {@link Test} to update
	 */
	@Override
	public boolean update(Test entity) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			// update Test in DB
			pstmt = con.prepareStatement(SQL_UPDATE_TEST_QUERY);
			int k = 0;
			pstmt.setString(++k, entity.getTestName());
			pstmt.setLong(++k, entity.getTestTime());
			pstmt.setInt(++k, entity.getComplexity());
			pstmt.setInt(++k, entity.getTestSubjectId());
			pstmt.setInt(++k, entity.getId());
			pstmt.executeUpdate();
			List<Question> questionList = entity.getQuestionList();
			// update Test's questions
			for (Question question : questionList) {
				PreparedStatement questionPstmt = con.prepareStatement(SQL_UPDATE_QUESTION_QUERY);
				int j = 0;
				questionPstmt.setString(++j, question.getQuestionText());
				questionPstmt.setInt(++j, question.getId());
				questionPstmt.setInt(++j, entity.getId());
				if (questionPstmt.executeUpdate() == 0) {
					PreparedStatement addPstmt = con.prepareStatement(SQL_CREATE_QUESTION_QUERY);
					int f = 0;
					addPstmt.setInt(++f, question.getId());
					addPstmt.setString(++f, question.getQuestionText());
					addPstmt.setInt(++f, entity.getId());
					addPstmt.executeUpdate();
					close(addPstmt);
				}
				List<Answer> answerList = question.getAnswerList();
				// update Question's Answers
				for (Answer answer : answerList) {
					PreparedStatement answerPstmt = con.prepareStatement(SQL_UPDATE_ANSWER_QUERY);
					int m = 0;
					answerPstmt.setString(++m, answer.getAnswerText());
					answerPstmt.setBoolean(++m, answer.isCorrect());
					answerPstmt.setInt(++m, answer.getId());
					answerPstmt.setInt(++m, question.getId());
					answerPstmt.setInt(++m, entity.getId());
					if (answerPstmt.executeUpdate() == 0) {
						PreparedStatement addPstmt = con.prepareStatement(SQL_CREATE_ANSWER_QUERY);
						int f = 0;
						addPstmt.setInt(++f, answer.getId());
						addPstmt.setString(++f, answer.getAnswerText());
						addPstmt.setBoolean(++f, answer.isCorrect());
						addPstmt.setInt(++f, entity.getId());
						addPstmt.setInt(++f, question.getId());
						addPstmt.executeUpdate();
						close(addPstmt);
					}
					close(answerPstmt);
				}
				close(questionPstmt);
				// if there redundant Answers for current Question in DB -
				// Delete them
				PreparedStatement redAnsDelPstmt = con.prepareStatement(SQL_DELETE_REDUNDANT_ANSWERS);
				int g = 0;
				redAnsDelPstmt.setInt(++g, entity.getId());
				redAnsDelPstmt.setInt(++g, question.getId());
				redAnsDelPstmt.setInt(++g, question.getAnswerList().size());
				redAnsDelPstmt.executeUpdate();
			}
			// if there redundant Questions for current Test in DB - Delete them
			PreparedStatement resQuesDelPstmt = con.prepareStatement(SQL_DELETE_REDUNDANT_QUESTIONS);
			int p = 0;
			resQuesDelPstmt.setInt(++p, entity.getId());
			resQuesDelPstmt.setInt(++p, entity.getQuestionList().size());
			resQuesDelPstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_TEST, e);
			throw new DatabaseException(Messages.ERR_CANNOT_UPDATE_TEST, e);
		} finally {
			close(con, pstmt);
		}
		return true;
	}

	/**
	 * Gets all Tests from DB
	 * 
	 * @return {@link List} of {@link Test} from DB
	 */
	@Override
	public List<Test> getAll() throws DatabaseException {
		List<Test> testList = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			testList = new ArrayList<>();
			stmt = con.createStatement();
			// get all Tests
			rs = stmt.executeQuery(SQL_GET_ALL_TEST_QUERY);
			while (rs.next()) {
				Test test = extractTest(rs);
				// get all Test's questions
				PreparedStatement questionPstmt = con.prepareStatement(SQL_GET_ALL_TESTS_QUESTIONS_QUERY);
				questionPstmt.setInt(1, test.getId());
				ResultSet questionsRs = questionPstmt.executeQuery();
				List<Question> questionsList = new ArrayList<>();
				while (questionsRs.next()) {
					Question question = extractQuestion(questionsRs);
					// get all Question's Answers
					PreparedStatement answerPstmt = con.prepareStatement(SQL_GET_ALL_QUESTIONS_ANSWERS_QUERY);
					int k = 0;
					answerPstmt.setInt(++k, question.getId());
					answerPstmt.setInt(++k, test.getId());
					ResultSet answersRs = answerPstmt.executeQuery();
					List<Answer> answersList = new ArrayList<>();
					while (answersRs.next()) {
						answersList.add(extractAnswer(answersRs));
					}
					close(answersRs);
					close(answerPstmt);
					question.setAnswerList(answersList);
					questionsList.add(question);
				}
				close(questionsRs);
				close(questionPstmt);
				test.setQuestionList(questionsList);
				testList.add(test);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_TESTS, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_TESTS, e);
		} finally {
			close(con, stmt, rs);
		}
		return testList;
	}

	/**
	 * Gets Test from DB by its ID
	 * 
	 * @param id
	 *            ID of {@link Test} representation in DB
	 * @return {@link Test} from DB
	 */
	@Override
	public Test getById(Integer id) throws DatabaseException {
		Test test = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			// get Test by id
			pstmt = con.prepareStatement(SQL_GET_BY_ID_TEST_QUERY);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				test = extractTest(rs);
				// get Test's Questions
				PreparedStatement questionsPstmt = con.prepareStatement(SQL_GET_ALL_TESTS_QUESTIONS_QUERY);
				questionsPstmt.setInt(1, test.getId());
				ResultSet questionsRs = questionsPstmt.executeQuery();
				List<Question> questionsList = new ArrayList<>();
				while (questionsRs.next()) {
					Question question = extractQuestion(questionsRs);
					// get Question's Answers
					PreparedStatement answerPstmt = con.prepareStatement(SQL_GET_ALL_QUESTIONS_ANSWERS_QUERY);
					int k = 0;
					answerPstmt.setInt(++k, question.getId());
					answerPstmt.setInt(++k, test.getId());
					ResultSet answersRs = answerPstmt.executeQuery();
					List<Answer> answersList = new ArrayList<>();
					while (answersRs.next()) {
						answersList.add(extractAnswer(answersRs));
					}
					close(answersRs);
					close(answerPstmt);
					question.setAnswerList(answersList);
					questionsList.add(question);
				}
				close(questionsRs);
				close(questionsPstmt);
				test.setQuestionList(questionsList);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_TEST_BY_ID, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_TEST_BY_ID, e);
		} finally {
			close(con, pstmt, rs);
		}
		return test;
	}

	/**
	 * Gets Test from DB by its subjects ID
	 * 
	 * @param subjectID
	 *            ID of {@link Subject} representation in DB
	 * @return {@link Test} from DB
	 */
	@Override
	public List<Test> getAllBySubject(Integer subjectID) throws DatabaseException {
		List<Test> testList = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			testList = new ArrayList<>();
			// get Tests by Subject's id
			pstmt = con.prepareStatement(SQL_GET_ALL_BY_SUBJECT_TEST_QUERY);
			pstmt.setInt(1, subjectID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Test test = extractTest(rs);
				// get all Test's Questions
				PreparedStatement questionPstmt = con.prepareStatement(SQL_GET_ALL_TESTS_QUESTIONS_QUERY);
				questionPstmt.setInt(1, test.getId());
				ResultSet questionsRs = questionPstmt.executeQuery();
				List<Question> questionsList = new ArrayList<>();
				while (questionsRs.next()) {
					Question question = extractQuestion(questionsRs);
					// get all Question's Answers
					PreparedStatement answerPstmt = con.prepareStatement(SQL_GET_ALL_QUESTIONS_ANSWERS_QUERY);
					int k = 0;
					answerPstmt.setInt(++k, question.getId());
					answerPstmt.setInt(++k, test.getId());
					ResultSet answersRs = answerPstmt.executeQuery();
					List<Answer> answersList = new ArrayList<>();
					while (answersRs.next()) {
						answersList.add(extractAnswer(answersRs));
					}
					close(answersRs);
					close(answerPstmt);
					question.setAnswerList(answersList);
					questionsList.add(question);
				}
				close(questionsRs);
				close(questionPstmt);
				test.setQuestionList(questionsList);
				testList.add(test);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_TESTS_BY_SUBJECTS, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_TESTS_BY_SUBJECTS, e);
		} finally {
			close(con, pstmt, rs);
		}
		return testList;
	}

	@Override
	public List<Test> getAllBySubject(Subject sj) throws DatabaseException {
		return getAllBySubject(sj.getId());
	}

	/**
	 * Extracts Test entity from result set
	 * 
	 * @param rs
	 *            Result Set
	 * @return extracted {@link Test}
	 * @throws SQLException
	 */
	private Test extractTest(ResultSet rs) throws SQLException {
		Test test = new Test();
		test.setId(rs.getInt("id"));
		test.setTestName(rs.getString("name"));
		test.setTestTime(rs.getLong("test_time"));
		test.setComplexity(rs.getInt("complexity"));
		test.setTestSubjectId(rs.getInt("subject_id"));
		return test;
	}

	/**
	 * Extracts Question entity from result set
	 * 
	 * @param rs
	 *            Result Set
	 * @return extracted {@link Question}
	 * @throws SQLException
	 */
	private Question extractQuestion(ResultSet rs) throws SQLException {
		Question question = new Question();
		question.setId(rs.getInt("id"));
		question.setQuestionText(rs.getString("text"));
		return question;
	}

	/**
	 * Extracts Answer entity from result set
	 * 
	 * @param rs
	 *            Result Set
	 * @return extracted {@link Answer}
	 * @throws SQLException
	 */
	private Answer extractAnswer(ResultSet rs) throws SQLException {
		Answer answer = new Answer();
		answer.setId(rs.getInt("id"));
		answer.setAnswerText(rs.getString("text"));
		answer.setCorrect(rs.getBoolean("correct"));
		return answer;
	}
}
