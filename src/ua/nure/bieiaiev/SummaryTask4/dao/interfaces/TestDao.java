package ua.nure.bieiaiev.SummaryTask4.dao.interfaces;

import java.util.List;

import ua.nure.bieiaiev.SummaryTask4.entities.Subject;
import ua.nure.bieiaiev.SummaryTask4.entities.test.Test;
import ua.nure.bieiaiev.SummaryTask4.exceptions.DatabaseException;

/**
 * Interface to set additional methods for Test entitie's DAO implementation
 * 
 * @author D.Bieliaiev
 *
 */
public interface TestDao extends EntityDao<Test> {
	/**
	 * Gets all Tests by Subject's id from Data Source
	 * 
	 * @param subjectID
	 *            {@link Subject} id
	 * @return {@link List} of {@link Test} from Data Source
	 * @throws DatabaseException
	 */
	public List<Test> getAllBySubject(Integer subjectID) throws DatabaseException;

	/**
	 * Gets all Tests by Subject from Data Source
	 * 
	 * @param sj
	 *            {@link Subject}
	 * @return {@link List} of {@link Test} from Data Source
	 * @throws DatabaseException
	 */
	public List<Test> getAllBySubject(Subject sj) throws DatabaseException;
}