package ua.nure.bieiaiev.SummaryTask4.entities;

/**
 * Subject Bean. Contains getters and setters for Subject entity.
 * 
 * @author D.Bieliaiev
 *
 */
public class Subject extends Entity {

	private static final long serialVersionUID = -217871300115006949L;

	private String name;

	public Subject() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Subject [name=" + name + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Subject) {
			Subject sub = (Subject) obj;
			return getId() == sub.getId();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return getId();
	}
}
