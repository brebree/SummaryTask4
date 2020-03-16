package ua.nure.bieiaiev.SummaryTask4.entities;

/**
 * Role Bean. Contains getters and setters for Role entity.
 * 
 * @author D.Bieliaiev
 *
 */
public class Role extends Entity {

	private static final long serialVersionUID = -7102881622146345824L;

	private String roleName;
	private String description;

	public Role() {
		super();
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [roleName=" + roleName + ", description=" + description + "]";
	}

}
