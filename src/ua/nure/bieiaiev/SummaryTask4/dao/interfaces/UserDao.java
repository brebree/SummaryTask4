package ua.nure.bieiaiev.SummaryTask4.dao.interfaces;

import ua.nure.bieiaiev.SummaryTask4.entities.User;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;

/**
 * Interface to set additional methods for User entitie's DAO implementation
 * 
 * @author D.Bieliaiev
 *
 */
public interface UserDao extends EntityDao<User> {
	/**
	 * Bans User by it's ID.Changes User's status to "banned" in Data Source
	 * 
	 * @param id
	 *            {@link User} id
	 * @throws DatabaseException
	 */
	public void banUser(Integer id) throws DatabaseException;

	/**
	 * Unbans User by it's ID.Changes User's status to "unbanned" in Data Source
	 * 
	 * @param id
	 *            {@link User} id
	 * @throws DatabaseException
	 */
	public void unbanUser(Integer id) throws DatabaseException;

	/**
	 * Gets User by it's login from Data Source
	 * 
	 * @param login
	 *            {@link User} login
	 * @return
	 * @throws DatabaseException
	 */
	public User getUserByLogin(String login) throws DatabaseException;
}
