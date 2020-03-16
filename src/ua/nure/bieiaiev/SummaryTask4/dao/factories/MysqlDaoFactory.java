package ua.nure.bieiaiev.SummaryTask4.dao.factories;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation.MysqlRoleDao;
import ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation.MysqlSubjectDao;
import ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation.MysqlTestDao;
import ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation.MysqlUserDao;
import ua.nure.bieiaiev.SummaryTask4.dao.mysqlImplementation.MysqlUsersTestDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.RoleDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.SubjectDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.TestDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UserDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UsersTestDao;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;
import ua.nure.bieiaiev.SummaryTask4.exceptions.Messages;

public class MysqlDaoFactory extends DaoFactory {

	private static final Logger LOG = Logger.getLogger(MysqlDaoFactory.class);

	private static MysqlDaoFactory instance;

	/**
	 * Singleton method
	 * 
	 * @return {@link MysqlDaoFactory} instance
	 * @throws DatabaseException
	 */
	protected static synchronized MysqlDaoFactory getInstance() throws DatabaseException {
		if (instance == null) {
			instance = new MysqlDaoFactory();
		}
		return instance;
	}

	/**
	 * Gets Data Source from Connection Pool
	 * 
	 * @throws DatabaseException
	 */
	private MysqlDaoFactory() throws DatabaseException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/testing");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_GET_DATA_SOURCE, ex);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_DATA_SOURCE, ex);
		}
	}

	private DataSource ds;

	/**
	 * Gets connection with Data Source
	 * 
	 * @return {@link Connection} with Derby DB
	 */
	public Connection getConnection() throws DatabaseException {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_GET_CONNECTION, e);
			throw new DatabaseException(Messages.ERR_CANNOT_GET_CONNECTION, e);
		}
	}

	@Override
	public UserDao getUserDao() {
		return new MysqlUserDao();
	}

	@Override
	public RoleDao gerRoleDao() {
		return new MysqlRoleDao();
	}

	@Override
	public TestDao getTestDao() {
		return new MysqlTestDao();
	}

	@Override
	public SubjectDao getSubectDao() {
		return new MysqlSubjectDao();
	}

	@Override
	public UsersTestDao getUsersTestDao() {
		return new MysqlUsersTestDao();
	}

}
