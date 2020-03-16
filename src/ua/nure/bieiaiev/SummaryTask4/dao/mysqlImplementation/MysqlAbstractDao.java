package ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.dao.factories.DaoFactory;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.EntityDao;
import ua.nure.bieiaiev.SummaryTask4.entities.Entity;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.Messages;

/**
 * Abstract class that implements common methods for every <T>DAO
 * 
 * @author D.Bieliaiev
 *
 * @param <T>
 */
public abstract class MysqlAbstractDao<T extends Entity> implements EntityDao<T> {

	protected static final Logger LOG = Logger.getLogger(MysqlAbstractDao.class);

	@Override
	/**
	 * Deletes entity from Derby DB Testing
	 */
	public boolean delete(T entity) throws DatabaseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		long id = entity.getId();
		Class<? extends Entity> clazz = entity.getClass();
		String tableName = clazz.getSimpleName() + "s";
		DaoFactory dao = DaoFactory.getDaoFactory(DaoFactory.DERBY);
		con = dao.getConnection();
		try {
			pstmt = con.prepareStatement("DELETE FROM " + tableName + " WHERE id=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_GET_STATEMENT, e);
			throw new DatabaseException(Messages.ERR_CANNOT_DELETE_ENTITY, e);
		} finally {
			close(con, pstmt);
		}
		return true;
	}

	/* Utility methods to close resources */

	/**
	 * Closes connection
	 * 
	 * @param con
	 *            Connection to be closed
	 */
	protected void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	/**
	 * Closes statement
	 * 
	 * @param stmt
	 *            Statement to be closed
	 */
	protected void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes Result Set
	 * 
	 * @param rs
	 *            Result set to be closed
	 */
	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Closes resources
	 */
	protected void close(Connection con, Statement stmt) {
		close(stmt);
		close(con);
	}

	protected void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks transaction
	 * 
	 * @param con
	 */
	protected void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_ROLLBACK_TRANSACTION, ex);
			}
		}
	}

}
