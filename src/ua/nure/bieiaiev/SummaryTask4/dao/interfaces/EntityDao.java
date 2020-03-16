package ua.nure.bieiaiev.SummaryTask4.dao.interfaces;

import java.util.List;

import ua.nure.bieiaiev.SummaryTask4.entities.Entity;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;

/**
 * Sets common methods for concrete DAO implementation. Contains standard CrUD
 * methods
 * 
 * @author D.Bieliaiev
 *
 * @param <T>
 */
public interface EntityDao<T extends Entity> {
	/**
	 * Gets all entities from Data Source
	 * 
	 * @return {@link List} of {@link Entity} from Data Source
	 * @throws DatabaseException
	 */
	public List<T> getAll() throws DatabaseException;

	/**
	 * Gets entity by ID from Data Source
	 * 
	 * @param id
	 *            entities ID
	 * @return {@link Entity} from Data Source
	 * @throws DatabaseException
	 */
	public T getById(Integer id) throws DatabaseException;

	/**
	 * Creates entity in Data Source
	 * 
	 * @param entity
	 *            {@link Entity} to create in Data Source
	 * @return true if created
	 * @throws DatabaseException
	 */
	public boolean create(T entity) throws DatabaseException;

	/**
	 * Updates entity in Data Source
	 * 
	 * @param entity
	 *            {@link Entity} to update in Data Source
	 * @return true if updated
	 * @throws DatabaseException
	 */
	public boolean update(T entity) throws DatabaseException;

	/**
	 * Deletes entity from Data Source
	 * 
	 * @param entity
	 *            {@link Entity} to delete from Data Source
	 * @return true if deleted
	 * @throws DatabaseException
	 */
	public boolean delete(T entity) throws DatabaseException;
}
