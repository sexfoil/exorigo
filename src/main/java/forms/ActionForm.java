package forms;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.Session;

import java.sql.SQLException;

import exorigo.pages.HomePage;
import tools.DBworker;
import tools.User;

/**
 * Main form for CRUD operation
 * @author Slava Poliakov *
 */

public class ActionForm extends Form {

    private static final long serialVersionUID = 5L;
    
    private String lastName;
    private String firstName;
    private String login;
    private String password;
    private String actionMessage;
    private String titleMessage = "Filling the fields and press needed action button: ";
    
    private HomePage parentPage;
    private DBworker db;
    
    
    public ActionForm(String id, final HomePage parentPage) {        
        super(id);
        this.parentPage = parentPage;
        setDefaultModel(new CompoundPropertyModel(this));
        
        add(new TextField("lastName"));
        add(new TextField("firstName"));
        add(new TextField("login"));
        add(new PasswordTextField("password"));        
        add(new Label("actionMessage"));
        add(new Label("titleMessage"));
        
        try {
        	db = new DBworker();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {			
			e.printStackTrace();
		}

        Button createUser = new Button("createUser"){
        	public void onSubmit() {
        		if (lastName != null && firstName != null && login != null && password != null) {
        			if (!db.isUserExist(login)) {
        				db.createUser(lastName, firstName, login, password);
        				parentPage.setResponsePage(HomePage.class);
        			} else {
        				actionMessage = "User with such login is already exist !";
        			}
        		} else {
        			actionMessage = "All field must be not empty !";
        		}        		
        	}        	
        };
        add(createUser);
        
        
        Button updateUser = new Button("updateUser"){
        	public void onSubmit() {
        		if (lastName != null && firstName != null && login != null && password != null) {
        			if (db.isUserExist(login)) {
        				User user = db.getUser(login);        				
        				if (Session.get().getAttribute("loggedUser") != null 
        						&& login.equals((String)Session.get().getAttribute("loggedUser"))) {	        				
	        				if (password.equals(user.getPassword()) 
	        						&& lastName.equals(user.getLastName()) 
	        						&& firstName.equals(user.getFirstName())) {
	        					actionMessage = "You entered same data. Nothing to update !";
	        				} else {
	        					if (!password.equals(user.getPassword())) {
	        						db.updateUser(login, "password", password);
	        					}
	        					if (!lastName.equals(user.getLastName())) {
	        						db.updateUser(login, "last_name", lastName);
	        					}
	        					if (!password.equals(user.getPassword())) {
	        						db.updateUser(login, "first_name", firstName);
	        					}     						      						
	            				parentPage.setResponsePage(HomePage.class);
	        				}
        				} else {
        					actionMessage = "You can't change user data. Login as '" + login + "' first !";
        				}
        			} else {
        				actionMessage = "There is no user with such login !";
        			}
        		} else {
        			actionMessage = "All field must be not empty !";
        		}
        	}
        };        
        add(updateUser);
        
        
        Button deleteUser = new Button("deleteUser"){
        	public void onSubmit() {
        		if (lastName != null && firstName != null && login != null && password != null) {
        			if (db.isUserExist(login)) {
        				User user = db.getUser(login);
        				if (password.equals(user.getPassword())) {
        					if (lastName.equals(user.getLastName()) && firstName.equals(user.getFirstName())) {
        						db.deleteUser(login);
            					parentPage.setResponsePage(HomePage.class);
        					} else {
        						actionMessage = "Incorrect name or last name !";
        					}        					
        				} else {
        					actionMessage = "Incorrect password !";
        				}
        			} else {
        				actionMessage = "There is no user with such login !";
        			}
        		} else {
        			actionMessage = "All field must be not empty !";
        		}
        	}
        };        
        add(deleteUser);        
    }
    
}
