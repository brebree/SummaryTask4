package ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.RoleDao;
import ua.nure.bieiaiev.SummaryTask4.entities.Role;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.Messages;

/**
 * Class contains methods that work with Role entity in application and it's
 * representation in Derby DB
 * 
 * @author D.Bieliaiev
 *
 */
public class MysqlRoleDao extends MysqlAbstractDao<Role> implements RoleDao {

	private static final String SQL_CREATE_ROLE_QUERY = "INSERT INTO roles VALUES(?,?,?)";
	private static final String SQL_UPDATE_ROLE_QUERY = "UPDATE roles SET name=?, description=? WHERE id=?";
	private static final String SQL_GET_ALL_ROLE_QUERY = "SELECT * FROM roles";
	private static final String SQL_GET_BY_ID_ROLE_QUERY = "SELECT * FROM roles WHERE id=?";

	/**
	 * Creates new Role in DB
	 * 
	 * @param entity
	 *            {@link Role} to create in DB
	 */
	@Override
	public boolean create(Role entity) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_CREATE_ROLE_QUERY);
			int k = 0;
			pstmt.setInt(++k, entity.getId());
			pstmt.setString(++k, entity.getRoleName());
			pstmt.setString(++k, entity.getDescription());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_CREATE_ROLE, e);
			throw new DatabaseException(Messages.ERR_CANNOT_CREATE_ROLE, e);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Updates Role in DB
	 * 
	 * @param entity
	 *            {@link Role} to update
	 */
	@Override
	public boolean update(Role entity) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_ROLE_QUERY);
			int k = 0;
			pstmt.setString(++k, entity.getRoleName());
			pstmt.setString(++k, entity.getDescription());
			pstmt.setInt(++k, entity.getId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_ROLE, e);
			throw new DatabaseException(Messages.ERR_CANNOT_UPDATE_ROLE, e);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Get all roles from DB
	 * 
	 * @return {@link List} of all {@link Role} from DB
	 */
	@Override
	public List<Role> getAll() throws DatabaseException {
		List<Role> list = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_ALL_ROLE_QUERY);
			list = new ArrayList<>();
			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt("id"));
				role.setRoleName(rs.getString("name"));
				role.setDescription(rs.getString("description"));
				list.add(role);
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_ROLES, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_ROLES, e);
		} finally {
			close(con, stmt, rs);
		}
		return list;
	}

	/**
	 * Gets Role by it's ID
	 * 
	 * @param id
	 *            ID of {@link Role} representation in DB
	 * @return {@link Role} Role from DB
	 */
	@Override
	public Role getById(Integer id) throws DatabaseException {
		Role role = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement(SQL_GET_BY_ID_ROLE_QUERY);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				role = new Role();
				role.setId(rs.getInt("id"));
				role.setRoleName(rs.getString("name"));
				role.setDescription(rs.getString("description"));
			}
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_ROLE, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_ROLE, e);
		} finally {
			close(con, pstmt, rs);
		}
		return role;
	}

}
