package ua.nure.bieiaiev.SummaryTask4.entities.test;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * UsersTest Bean. Contains getters and setters for UsersTest entity.
 * 
 * @author D.Bieliaiev
 *
 */
@XmlRootElement
public class UsersTest extends Test {

	private static final long serialVersionUID = -5403156758514273662L;

	private Integer testId;
	private Integer testResult;
	private Integer usersId;
	private Date date;

	public Date getDate() {
		return date;
	}

	@XmlElement
	public void setDate(Date date) {
		this.date = date;
	}

	public UsersTest() {
		super();
	}

	public Integer getTestId() {
		return testId;
	}

	@XmlAttribute
	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public Integer getTestResult() {
		return testResult;
	}

	@XmlElement
	public void setTestResult(Integer testResult) {
		this.testResult = testResult;
	}

	public Integer getUsersId() {
		return usersId;
	}

	@XmlElement
	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	@Override
	public String toString() {
		return "UsersTest [testId=" + testId + ", usersId=" + usersId + ", date=" + date + "]";
	}

}
