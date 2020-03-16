package ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.SubjectDao;
import ua.nure.bieiaiev.SummaryTask4.entities.Subject;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.Messages;

/**
 * Class contains methods that work with Subject entity in application and it's
 * representation in Derby DB
 * 
 * @author D.Bieliaiev
 *
 */
public class MysqlSubjectDao extends MysqlAbstractDao<Subject> implements SubjectDao {

	private static final String SQL_CREATE_SUBJECT_QUERY = "INSERT INTO subjects VALUES(DEFAULT,?)";
	private static final String SQL_UPDATE_SUBJECT_QUERY = "UPDATE subjects SET name=? WHERE id=?";
	private static final String SQL_GET_ALL_SUBJECT_QUERY = "SELECT * FROM subjects";
	private static final String SQL_GET_BY_ID_SUBJECT_QUERY = "SELECT * FROM subjects WHERE id=?";
	private static final String SQL_GET_BY_NAME_SUBJECT_QUERY = "SELECT * FROM subjects WHERE name=?";

	/**
	 * Creates new Subject in DB
	 * 
	 * @param entity
	 *            {@link Subject} to create in DB
	 */
	@Override
	public boolean create(Subject entity) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_CREATE_SUBJECT_QUERY);
			pstmt.setString(1, entity.getName());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_SUBJECT, e);
			throw new DatabaseException(Messages.ERR_CANNOT_CREATE_SUBJECT, e);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Updates Subject in DB
	 * 
	 * @param entity
	 *            {@link Subject} to update
	 */
	@Override
	public boolean update(Subject entity) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_SUBJECT_QUERY);
			int k = 0;
			pstmt.setString(++k, entity.getName());
			pstmt.setInt(++k, entity.getId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_SUBJECT, e);
			throw new DatabaseException(Messages.ERR_CANNOT_UPDATE_SUBJECT, e);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Gets all Subjects from DB
	 * 
	 * @return {@link List} of {@link Subject} from DB
	 */
	@Override
	public List<Subject> getAll() throws DatabaseException {

		List<Subject> list = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_ALL_SUBJECT_QUERY);
			list = new ArrayList<>();
			while (rs.next()) {
				Subject sj = new Subject();
				sj.setId(rs.getInt("id"));
				sj.setName(rs.getString("name"));
				list.add(sj);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_SUBJECTS, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_SUBJECTS, e);
		} finally {
			close(con, stmt, rs);
		}
		return list;
	}

	/**
	 * Gets Subject from DB by its ID
	 * 
	 * @param id
	 *            ID of {@link Subject} representation in DB
	 * @return {@link Subject} from DB
	 */
	@Override
	public Subject getById(Integer id) throws DatabaseException {
		Subject sj = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_GET_BY_ID_SUBJECT_QUERY);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sj = new Subject();
				sj.setId(rs.getInt("id"));
				sj.setName(rs.getString("name"));
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_SUBJECT_BY_ID, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_SUBJECT_BY_ID, e);
		} finally {
			close(con, pstmt, rs);
		}
		return sj;
	}

	/**
	 * Gets Subject from DB by its name
	 * 
	 * @param name
	 *            name of {@link Subject} representation in DB
	 * @return {@link Subject} from DB
	 */
	@Override
	public Subject getSubjectByName(String name) throws DatabaseException {
		Subject sj = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_GET_BY_NAME_SUBJECT_QUERY);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sj = new Subject();
				sj.setId(rs.getInt("id"));
				sj.setName(rs.getString("name"));
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_STATEMENT, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_STATEMENT, e);
		} finally {
			close(con, pstmt, rs);
		}
		return sj;
	}

}
