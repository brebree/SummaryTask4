package ua.nure.bieiaiev.SummaryTask4.exceptions;

/**
 * Container for error messages
 * 
 * @author D.Bieliaiev
 *
 */
public final class Messages {

	private Messages() {
		super();
	}

	public static final String ERR_CANNOT_GET_CONNECTION = "Can not get a connection from the pool";

	public static final String ERR_CANNOT_GET_USER_BY_ID = "Can not get a user by its id from DB";

	public static final String ERR_CANNOT_GET_USER_BY_LOGIN = "Can not get a user by its login from DB";

	public static final String ERR_CANNOT_UPDATE_USER = "Can not update a user in DB";

	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Can not close a connection";

	public static final String ERR_CANNOT_CLOSE_RESULTSET = "Can not close a result set";

	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Can not close a statement";

	public static final String ERR_CANNOT_GET_DATA_SOURCE = "Can not get the data source";

	public static final String ERR_CANNOT_GET_STATEMENT = "Can not get a statement";

	public static final String ERR_CANNOT_DELETE_ENTITY = "Can not delete entity from DB";

	public static final String ERR_CANNOT_ROLLBACK_TRANSACTION = "Cannot rollback transaction";

	public static final String ERR_CANNOT_CREATE_ROLE = "Can not create new Role in DB";

	public static final String ERR_CANNOT_UPDATE_ROLE = "Can not update Role in DB";

	public static final String ERR_CANNOT_GET_ROLES = "Can not get all Roles from DB";

	public static final String ERR_CANNOT_GET_ROLE = "Can not get Role by ID from DB";

	public static final String ERR_CANNOT_CREATE_SUBJECT = "Can not create new Subject in DB";

	public static final String ERR_CANNOT_UPDATE_SUBJECT = "Can not update Subject in DB";

	public static final String ERR_CANNOT_GET_SUBJECTS = "Can not get all Subjects from DB";

	public static final String ERR_CANNOT_GET_SUBJECT_BY_ID = "Can not get Subject from DB by ID";

	public static final String ERR_CANNOT_CREATE_TEST = "Can not create Test in DB";

	public static final String ERR_CANNOT_UPDATE_TEST = "Can not update Test in DB";

	public static final String ERR_CANNOT_GET_TESTS = "Can not get all Tests from DB";

	public static final String ERR_CANNOT_GET_TEST_BY_ID = "Can not get Test by ID from DB";

	public static final String ERR_CANNOT_GET_TESTS_BY_SUBJECTS = "Can not get Tests by Subject from DB";

	public static final String ERR_CANNOT_CREATE_USER = "Can not create User in DB";

	public static final String ERR_CANNOT_GET_USERS = "Can not get Users from DB";

	public static final String ERR_CANNOT_BAN_USER = "Can not ban User";

	public static final String ERR_CANNOT_UNBAN_USER = "Can not unban User";

	public static final String ERR_CANNOT_CREATE_USERSTEST = "Can not create UsersTest in DB";

	public static final String ERR_CANNOT_GET_USERSTESTS = "Can not get all UsersTests from DB";

	public static final String ERR_CANNOT_GET_USERSTEST_BY_ID = "Can not get UsersTest by ID from DB";

	public static final String ERR_CANNOT_GET_USERSTESTS_BY_USERS_ID = "Can not get all UsersTests by Users ID from DB";

	public static final String ERR_CANNOT_FIND_SUCH_EMAIL_ADDRESS = "Can not find such email address";

	public static final String ERR_CANNOT_SEND_EMAIL = "Can not send email";

	public static final String ERR_CANNOT_GENERATE_REPORT = "Can not generate report";

	public static final String ERR_CANNOT_DOWNLOAD_FILE = "Can not download file";

}