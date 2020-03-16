package ua.nure.bieiaiev.SummaryTask4.dao.factories;

import java.sql.Connection;

import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.RoleDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.SubjectDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.TestDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UserDao;
import ua.nure.bieiaiev.SummaryTask4.dao.interfaces.UsersTestDao;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;

/**
 * Abstract factory that creates DAO Factory for certain Data Source
 * 
 * @author D.Bieliaiev
 *
 */
public abstract class DaoFactory {

	public static final String DERBY = "Derby";

	public abstract Connection getConnection() throws DatabaseException;

	public abstract UserDao getUserDao();

	public abstract RoleDao gerRoleDao();

	public abstract TestDao getTestDao();

	public abstract SubjectDao getSubectDao();

	public abstract UsersTestDao getUsersTestDao();

	/**
	 * Gets certain DAO Factory. Returns <code>null</code> if parameter is
	 * incorrect
	 * 
	 * @param dsName
	 *            name of Data Source
	 * @return certain {@link DaoFactory} or <code>null</code> if parameter is
	 *         incorrect
	 * @throws DatabaseException
	 */
	public static DaoFactory getDaoFactory(String dsName) throws DatabaseException {
		switch (dsName) {
		case DERBY:
			return MysqlDaoFactory.getInstance();
		default:
			return null;
		}
	}
}
