package tools;

import java.io.Serializable;

/**
 * Class User
 * @author Slava Poliakov *
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 4L;
	
	private int id;
	private String lastName;
	private String firstName;
	private String login;
	private String password;

	public User(int id, String lastName, String firstName, String login, String password) {		
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.login = login;
		this.password = password;
	}
	
	public String getUserInfo() {
		return "Login: " + getLogin() + " | Full name: " + getFirstName() + " " + getLastName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
}

