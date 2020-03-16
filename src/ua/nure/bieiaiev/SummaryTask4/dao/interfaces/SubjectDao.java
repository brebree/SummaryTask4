package ua.nure.bieiaiev.SummaryTask4.dao.interfaces;

import ua.nure.bieiaiev.SummaryTask4.entities.Subject;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;

/**
 * Interface to set additional methods for Subject entitie's DAO implementation
 * 
 * @author D.Bieliaiev
 *
 */
public interface SubjectDao extends EntityDao<Subject> {
	/**
	 * Gets subject by it's name from Data Source
	 * 
	 * @param name
	 *            {@link Subject} name
	 * @return {@link Subject} from Data Source
	 * @throws DatabaseException
	 */
	public Subject getSubjectByName(String name) throws DatabaseException;

}
