package ua.nure.bieiaiev.SummaryTask4.entities;

/**
 * Contains roles of Users in application.
 * 
 * @author D.Bieliaiev
 *
 */
public enum RoleEnum {
	ADMIN, STUDENT;

	public static RoleEnum getRole(User user) {
		int roleId = user.getRoleId();
		return RoleEnum.values()[roleId];
	}

	public String getName() {
		return name().toLowerCase();
	}
}