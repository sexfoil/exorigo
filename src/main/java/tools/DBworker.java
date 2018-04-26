package tools;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for working with data base
 * @author Slava Poliakov *
 */
public class DBworker implements Serializable{
	
	private static final long serialVersionUID = 9999L;
	
	private static final String BD_DRIVER = "com.mysql.cj.jdbc.Driver";	
	private static final String CONNECTION_URL = "jdbc:mysql://localhost/exorigo?serverTimezone=UTC";	
	private static final String BD_USER = "root";
	private static final String BD_PASS = "";
	private static final String TABLE_NAME = "test";
	
	Connection connection = null;
	Statement statement = null;
	
	public DBworker() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
        Class.forName(BD_DRIVER).newInstance();
        connection = DriverManager.getConnection(CONNECTION_URL, BD_USER, BD_PASS);
        statement = connection.createStatement();        
	}	
	
	
	public void closeAll() {		
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {			
			e.printStackTrace();
		}        
	}
	
	/**
	 * Method to write new user in DB
	 * 
	 * @param lastName
	 * @param firstName
	 * @param login
	 * @param password
	 * @return User in case successfully creation then null
	 */
	
    public User createUser(String lastName, String firstName, String login, String password) {
    	User user = null;
    	
    	if (!isUserExist(login)) {    	
	        StringBuilder query = new StringBuilder("INSERT INTO ");
	        query.append(TABLE_NAME);
	        query.append(" (last_name, first_name, login, password");
	        query.append(") VALUES ('");
	        query.append(lastName).append("', '");
	        query.append(firstName).append("', '");
	        query.append(login).append("', '");
	        query.append(password).append("');");	        
	       
	        try {        	
	            statement.executeUpdate(query.toString());
	            user = getUser(login);
	        } catch (SQLException e) {        	
	        	e.printStackTrace();
	        }
    	}
        return user;
    }
        
    /**
     * Method to get User from DB
     * 
     * @param login
     * @return User if exist then null
     */
    public User getUser(String login) {
    	User user = null;
    	StringBuilder query = new StringBuilder("SELECT * FROM  ");
    	query.append(TABLE_NAME);
    	query.append(" WHERE login = '");
    	query.append(login).append("'");

        try {
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("last_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("login"),                        
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;        
    }

    /**
     * Method to update existing User's data
     * 
     * @param login
     * @param field
     * @param value
     */
    public void updateUser(String login, String field, String value) {
    	
    	if (isUserExist(login)) {    	
	        StringBuilder query = new StringBuilder("UPDATE ");
	        query.append(TABLE_NAME);
	        query.append(" SET ");
	        query.append(field).append(" = '");
	        query.append(value).append("' WHERE login = '");
	        query.append(login).append("';");	               
	        
	        try {        	
	            statement.executeUpdate(query.toString());
	            
	        } catch (SQLException e) {        	
	        	e.printStackTrace();
	        }
    	}        
    }
    

    /**
     * Method to delete User from DB
     *     
     * @param login
     */
    public void deleteUser(String login) {
    	    	
    	if (isUserExist(login)) {    	
	        StringBuilder query = new StringBuilder("DELETE FROM ");
	        query.append(TABLE_NAME);
	        query.append(" WHERE login = '");
	        query.append(login).append("';");	        
	        
	        try {        	
	            statement.executeUpdate(query.toString());
	            
	        } catch (SQLException e) {        	
	        	e.printStackTrace();
	        }
    	}        
    }
        
    
    /**
     * Method to find User by name and last name
     * 
     * @param name1
     * @param name2
     * @return User if exist then null
     */
    public User findUser(String name1, String name2) {
        User user = null;
        StringBuilder query = new StringBuilder("SELECT * FROM ");        
        query.append(TABLE_NAME);
        query.append(" WHERE (last_name = '");
        query.append(name1).append("' AND first_name = '");
        query.append(name2).append("') OR (last_name = '");
        query.append(name2).append("' AND first_name = '");
        query.append(name1).append("');");       
       
        
        try {
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("last_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("login"),                        
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;        
    }
    
    
    /**
     * Method to get list of Users
     * 
     * @return List<User>
     */
    public List<User> getAllUsers() {
        String query = "SELECT * FROM  " + TABLE_NAME;
        List<User> userList = new ArrayList<User>();
       
        try {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("last_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("login"),                        
                        resultSet.getString("password")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userList;        
    }
    
    
    /**
     * Method to check is login exist in DB
     * 
     * @param login
     * @return true if User with such 'login' exist then false
     */
    public boolean isUserExist(String login) {
        StringBuilder query = new StringBuilder("SELECT * FROM  ");
        query.append(TABLE_NAME);
        query.append(" WHERE login = '");
        query.append(login).append("';");
        ResultSet resultSet;        
        boolean isExist = false;
        
        try {
        	resultSet = statement.executeQuery(query.toString());
        	resultSet.next();
        	isExist = login.equals(resultSet.getString("login"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return isExist;
    }

}
