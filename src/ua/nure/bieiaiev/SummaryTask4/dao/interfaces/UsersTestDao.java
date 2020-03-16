package ua.nure.bieiaiev.SummaryTask4.dao.interfaces;

import java.util.List;

import ua.nure.bieiaiev.SummaryTask4.entities.User;
import ua.nure.bieiaiev.SummaryTask4.entities.test.UsersTest;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;

/**
 * Interface to set additional methods for UsersTest entitie's DAO
 * implementation
 * 
 * @author D.Bieliaiev
 *
 */
public interface UsersTestDao extends EntityDao<UsersTest> {
	/**
	 * Gets all UsersTests by User from Data Source
	 * 
	 * @param user
	 *            {@link User}
	 * @return {@link List} of {@link UsersTest} from Data Source
	 * @throws DatabaseException
	 */
	public List<UsersTest> getAllUsersTestsByUser(User user) throws DatabaseException;

	/**
	 * Gets all UsersTests by User's id from Data Source
	 * 
	 * @param id
	 *            {@link User} id
	 * @return {@link List} of {@link UsersTest} from Data Source
	 * @throws DatabaseException
	 */
	public List<UsersTest> getAllUsersTestsByUsersId(Integer id) throws DatabaseException;
}
