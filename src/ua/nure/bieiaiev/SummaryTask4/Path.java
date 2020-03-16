package ua.nure.bieiaiev.SummaryTask4;

/**
 * 
 * Container for paths to jsp and commands
 * 
 * @author D.Bieliaiev
 *
 */
public final class Path {

	private Path() {
		super();
	}

	public static final String PAGE_LOGIN = "/SummaryTask4";
	public static final String PAGE_LOGOUT = "/SummaryTask4";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_VIEW_TESTS = "/WEB-INF/jsp/view_tests.jsp";
	public static final String PAGE_VIEW_USERS = "/WEB-INF/jsp/admin/view_all_users_page.jsp";
	public static final String PAGE_VIEW_CREATE_TEST = "/WEB-INF/jsp/admin/create_test.jsp";
	public static final String PAGE_VIEW_EDIT_TEST = "/WEB-INF/jsp/admin/edit_test.jsp";
	public static final String PAGE_VIEW_TEST_TO_PASS = "/WEB-INF/jsp/student/pass_test.jsp";
	public static final String PAGE_VIEW_USERS_PROFILE = "/WEB-INF/jsp/student/user_profile.jsp";
	public static final String PAGE_VIEW_REGISTER = "/WEB-INF/jsp/register_page.jsp";
	public static final String PAGE_VIEW_TEST_RESULT = "/WEB-INF/jsp/student/test_result.jsp";
	public static final String COMMAND_VIEW_USERS = "controller?command=viewAllUsers";
	public static final String COMMAND_VIEW_TESTS = "controller?command=viewAllTests";
	public static final String COMMAND_VIEW_REGISTER = "controller?command=viewRegister";
	public static final String COMMAND_VIEW_TEST_RESULT = "controller?command=viewTestResult";
}