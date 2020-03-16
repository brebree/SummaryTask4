package ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UserDao;
import ua.nure.bieiaiev.SummaryTask4.entities.User;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.Messages;

/**
 * Class contains methods that work with User entity in application and it's
 * representation in Derby DB
 * 
 * @author D.Bieliaiev
 *
 */
public class MysqlUserDao extends MysqlAbstractDao<User> implements UserDao {

	private static final String SQL_CREATE_USER_QUERY = "INSERT INTO users VALUES(DEFAULT,?,?,?,?,DEFAULT,DEFAULT,?,?,?)";
	private static final String SQL_UPDATE_USER_QUERY = "UPDATE users SET login=?,password=?,first_name=?,last_name=?,test_results=?,tests_count=?,role_id=?,status_id=?,email=? WHERE id=?";
	private static final String SQL_GET_ALL_USER_QUERY = "SELECT * FROM users";
	private static final String SQL_GET_BY_ID_USER_QUERY = "SELECT * FROM users WHERE id=?";
	private static final String SQL_GET_BY_LOGIN_USER_QUERY = "SELECT * FROM users WHERE login=?";
	private static final String SQL_BAN_USER_QUERY = "UPDATE users SET status_id=0 WHERE id=?";
	private static final String SQL_UNBAN_USER_QUERY = "UPDATE users SET status_id=1 WHERE id=?";

	/**
	 * Creates new User in DB
	 * 
	 * @param entity
	 *            {@link User} to create in DB
	 */
	@Override
	public boolean create(User entity) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_CREATE_USER_QUERY);
			int k = 0;
			pstmt.setString(++k, entity.getLogin());
			pstmt.setString(++k, entity.getPassword());
			pstmt.setString(++k, entity.getFirstName());
			pstmt.setString(++k, entity.getLastName());
			pstmt.setInt(++k, entity.getRoleId());
			pstmt.setInt(++k, entity.getStatusId());
			pstmt.setString(++k, entity.getEmail());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_USER, e);
			throw new DatabaseException(Messages.ERR_CANNOT_CREATE_USER, e);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Updates User in DB
	 * 
	 * @param entity
	 *            {@link User} to update
	 */
	@Override
	public boolean update(User entity) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER_QUERY);
			int k = 0;
			pstmt.setString(++k, entity.getLogin());
			pstmt.setString(++k, entity.getPassword());
			pstmt.setString(++k, entity.getFirstName());
			pstmt.setString(++k, entity.getLastName());
			pstmt.setInt(++k, entity.getTestsResult());
			pstmt.setInt(++k, entity.getTestsCount());
			pstmt.setInt(++k, entity.getRoleId());
			pstmt.setInt(++k, entity.getStatusId());
			pstmt.setString(++k, entity.getEmail());
			pstmt.setInt(++k, entity.getId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_USER, e);
			throw new DatabaseException(Messages.ERR_CANNOT_UPDATE_USER, e);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Get all Users from DB
	 * 
	 * @return {@link List} of all {@link User} from DB
	 */
	@Override
	public List<User> getAll() throws DatabaseException {
		List<User> list = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_ALL_USER_QUERY);
			list = new ArrayList<>();
			while (rs.next()) {
				list.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_USERS, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_USERS, e);
		} finally {
			close(con, stmt, rs);
		}
		return list;
	}

	/**
	 * Gets User by ID from DB
	 * 
	 * @param id
	 *            {@link User} ID
	 * @return {@link User} by it's ID
	 */
	@Override
	public User getById(Integer id) throws DatabaseException {
		User user = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_GET_BY_ID_USER_QUERY);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_USER_BY_ID, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_USER_BY_ID, e);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Gets User by it's login from DB
	 * 
	 * @param login
	 *            {@link User} login
	 * @return {@link User} by it's login
	 */
	@Override
	public User getUserByLogin(String login) throws DatabaseException {
		User user = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_GET_BY_LOGIN_USER_QUERY);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_USER_BY_LOGIN, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_USER_BY_LOGIN, e);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Bans User by it's ID.Changes User's status to "banned" in Data Source
	 * 
	 * @param id
	 *            {@link User} id
	 */
	@Override
	public void banUser(Integer id) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_BAN_USER_QUERY);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_BAN_USER, e);
			throw new DatabaseException(Messages.ERR_CANNOT_BAN_USER, e);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Unbans User by it's ID.Changes User's status to "unbanned" in Data Source
	 * 
	 * @param id
	 *            {@link User} id
	 */
	@Override
	public void unbanUser(Integer id) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_UNBAN_USER_QUERY);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UNBAN_USER, e);
			throw new DatabaseException(Messages.ERR_CANNOT_UNBAN_USER, e);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Extracts User from result set
	 * 
	 * @param rs
	 *            ResultSet
	 * @return {@link User} from Result set
	 * @throws SQLException
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		user.setPassword(rs.getString("password"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setTestsResult(rs.getInt("test_results"));
		user.setTestsCount(rs.getInt("tests_count"));
		user.setRoleId(rs.getInt("role_id"));
		user.setStatusId(rs.getInt("status_id"));
		user.setEmail(rs.getString("email"));
		return user;
	}

}
