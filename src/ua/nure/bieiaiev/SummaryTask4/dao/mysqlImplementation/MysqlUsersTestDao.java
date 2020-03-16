package ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UsersTestDao;
import ua.nure.bieiaiev.SummaryTask4.entities.User;
import ua.nure.bieiaiev.SummaryTask4.entities.test.UsersTest;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.Messages;

/**
 * Class contains methods that work with UsersTest entity in application and
 * it's representation in Derby DB
 * 
 * @author D.Bieliaiev
 *
 */
public class MysqlUsersTestDao extends MysqlAbstractDao<UsersTest> implements UsersTestDao {

	private static final String SQL_CREATE_USERSTEST_QUERY = "INSERT INTO userstests VALUES(DEFAULT,?,?,?,?)";
	private static final String SQL_GET_ALL_USERSTESTS_QUERY = "SELECT * FROM userstests";
	private static final String SQL_GET_BY_ID_USERSTEST_QUERY = "SELECT * FROM userstests WHERE id=?";
	private static final String SQL_GET_ALL_BY_USERS_ID_USERSTESTS_QUERY = "SELECT * FROM userstests WHERE user_id=?";
	private static final String SQL_GET_TEST_BY_ID = "SELECT * FROM tests WHERE id=?";

	/**
	 * Creates new UsersTest in DB
	 * 
	 * @param entity
	 *            {@link UsersTest} to create in DB
	 */
	@Override
	public boolean create(UsersTest entity) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_CREATE_USERSTEST_QUERY, Statement.RETURN_GENERATED_KEYS);
			int k = 0;
			pstmt.setInt(++k, entity.getTestResult());
			Timestamp timestamp = new Timestamp(new Date().getTime());
			pstmt.setTimestamp(++k, timestamp);
			pstmt.setInt(++k, entity.getUsersId());
			pstmt.setInt(++k, entity.getTestId());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					entity.setId(id);
				}
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_USERSTEST, e);
			throw new DatabaseException(Messages.ERR_CANNOT_CREATE_USERSTEST, e);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * No reason to update UsersTests in DB
	 */
	@Override
	public boolean update(UsersTest entity) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets all UsersTests from DB
	 * 
	 * @return {@link List} of {@link UsersTest} from DB
	 */
	@Override
	public List<UsersTest> getAll() throws DatabaseException {
		List<UsersTest> usersTestsList = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			usersTestsList = new ArrayList<>();
			stmt = con.createStatement();
			// get UsersTests from DB
			rs = stmt.executeQuery(SQL_GET_ALL_USERSTESTS_QUERY);
			while (rs.next()) {
				UsersTest usersTest = extractUsersTest(rs);
				// get Test from DB to fill current UsersTest with info about
				// Test
				PreparedStatement pstmt = con.prepareStatement(SQL_GET_TEST_BY_ID);
				pstmt.setInt(1, usersTest.getTestId());
				ResultSet testRs = pstmt.executeQuery();
				while (testRs.next()) {
					usersTest.setComplexity(testRs.getInt("complexity"));
					usersTest.setTestName(testRs.getString("name"));
					usersTest.setTestSubjectId(testRs.getInt("subject_id"));
					usersTest.setTestTime(testRs.getLong("test_time"));
				}
				close(testRs);
				close(pstmt);
				usersTestsList.add(usersTest);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_USERSTESTS, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_USERSTESTS, e);
		} finally {
			close(con, stmt, rs);
		}
		return usersTestsList;
	}

	/**
	 * Gets all UsersTests by it's id from DB
	 * 
	 * @param id
	 *            {@link UsersTest} id
	 * @return {@link UsersTest} from DB
	 */
	@Override
	public UsersTest getById(Integer id) throws DatabaseException {
		UsersTest usersTest = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			// get UsersTest by ID from DB
			pstmt = con.prepareStatement(SQL_GET_BY_ID_USERSTEST_QUERY);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				usersTest = extractUsersTest(rs);
				// get Test from DB to fill UsersTest with info about Test
				PreparedStatement pstmt1 = con.prepareStatement(SQL_GET_TEST_BY_ID);
				pstmt1.setInt(1, usersTest.getTestId());
				ResultSet testRs = pstmt1.executeQuery();
				while (testRs.next()) {
					usersTest.setComplexity(testRs.getInt("complexity"));
					usersTest.setTestName(testRs.getString("name"));
					usersTest.setTestSubjectId(testRs.getInt("subject_id"));
					usersTest.setTestTime(testRs.getLong("test_time"));
				}
				close(testRs);
				close(pstmt1);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_USERSTEST_BY_ID, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_USERSTEST_BY_ID, e);
		} finally {
			close(con, pstmt, rs);
		}
		return usersTest;
	}

	/**
	 * Gets all UsersTests by Users id from DB
	 * 
	 * @param id
	 *            {@link User} id
	 * @return {@link List} of {@link UsersTest} from DB
	 */
	@Override
	public List<UsersTest> getAllUsersTestsByUsersId(Integer id) throws DatabaseException {
		List<UsersTest> usersTestsList = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			usersTestsList = new ArrayList<>();
			// get all UsersTests by Users id from DB
			pstmt = con.prepareStatement(SQL_GET_ALL_BY_USERS_ID_USERSTESTS_QUERY);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UsersTest usersTest = extractUsersTest(rs);
				// get Test from DB to fill current UsersTest with info about
				// Test
				PreparedStatement pstmt1 = con.prepareStatement(SQL_GET_TEST_BY_ID);
				pstmt1.setInt(1, usersTest.getTestId());
				ResultSet testRs = pstmt1.executeQuery();
				while (testRs.next()) {
					usersTest.setComplexity(testRs.getInt("complexity"));
					usersTest.setTestName(testRs.getString("name"));
					usersTest.setTestSubjectId(testRs.getInt("subject_id"));
					usersTest.setTestTime(testRs.getLong("test_time"));
				}
				close(testRs);
				close(pstmt1);
				usersTestsList.add(usersTest);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_USERSTESTS_BY_USERS_ID, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_USERSTESTS_BY_USERS_ID, e);
		} finally {
			close(con, pstmt, rs);
		}
		return usersTestsList;
	}

	@Override
	public List<UsersTest> getAllUsersTestsByUser(User user) throws DatabaseException {
		return getAllUsersTestsByUsersId(user.getId());
	}

	/**
	 * Extracts UsersTest from Result Set
	 * 
	 * @param rs
	 *            Result set
	 * @return {@link UsersTest} from Result Set
	 * @throws SQLException
	 */
	private UsersTest extractUsersTest(ResultSet rs) throws SQLException {
		UsersTest usersTest = new UsersTest();
		usersTest.setId(rs.getInt("id"));
		usersTest.setTestResult(rs.getInt("result"));
		Timestamp ts = rs.getTimestamp("test_date");
		Long time = ts.getTime();
		Date date = new Date();
		date.setTime(time);
		usersTest.setDate(date);
		usersTest.setUsersId(rs.getInt("user_id"));
		usersTest.setTestId(rs.getInt("test_id"));
		return usersTest;
	}
}
