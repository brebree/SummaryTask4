package ua.nure.bieiaiev.SummaryTask4.entities;

/**
 * User Bean. Contains getters and setters for User entity.
 * 
 * @author D.Bieliaiev
 *
 */
public class User extends Entity {

	private static final long serialVersionUID = 8275674264939555151L;

	private String login;
	private String password;
	private String firstName;
	private String lastName;
	private Integer testsResult;
	private Integer roleId;
	private Integer statusId;
	private Integer testsCount;
	private String email;

	public Integer getTestsCount() {
		return testsCount;
	}

	public void setTestsCount(Integer testsCount) {
		this.testsCount = testsCount;
	}

	public User() {
		super();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getTestsResult() {
		return testsResult;
	}

	public void setTestsResult(Integer testsResult) {
		this.testsResult = testsResult;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ "]";
	}

}
