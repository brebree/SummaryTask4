package ua.nure.bieiaiev.SummaryTask4.entities;

import java.io.Serializable;

/**
 * Model of Java Bean. Contains getter and setter for ID.
 * 
 * @author D.Bieliaiev
 *
 */
public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 5266098138581746810L;

	private int id;

	public Entity() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
