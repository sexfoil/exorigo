package forms;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import java.sql.SQLException;

import tools.DBworker;
import tools.User;

/**
 * Login form for logging
 * @author Slava Poliakov *
 */
public class LoginForm extends Form {

    private static final long serialVersionUID = 3L;
    
    private String username;
    private String password;
    private String loginStatus = "Hello";
    
    private TextField loginFld;
    private Label loginLbl;
    private Label passLbl;
    private PasswordTextField passFld;
    private Button logInBtn;
    private Button logOutBtn;
    
    public LoginForm(String id) {        
        super(id);
        setDefaultModel(new CompoundPropertyModel(this));
        
        String userLogin = (String) Session.get().getAttribute("loggedUser");
        loginStatus = userLogin == null ? "Hello visitor !" : ("Welcome " + userLogin + " !"); 
        
        loginFld = new TextField("username");        
	    passFld = new PasswordTextField("password");
	    loginLbl = new Label("loginLbl", "Login:");
	    passLbl = new Label("passLbl", "Password:");
	        
	    logInBtn = new Button("logIn"){
	        @Override
			public void onSubmit() {
				User user = null;
			    try {
					DBworker db = new DBworker();
					user = db.getUser(username);
					db.closeAll();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			    			    	
			    if (user != null && ((user.getLogin()).equals(username) && (user.getPassword()).equals(password))){
			        loginStatus = "Welcome " + user.getLogin() + " !";
			            
			        setSessionAtribute(user.getLogin());			        
			    }else{
			        loginStatus = "Wrong username or password !";
			    }
	        }
	    };
	        
	    add(loginFld);
	    add(passFld);
	    add(loginLbl);
	    add(passLbl);        
	       
	    add(logInBtn);
        
	    logOutBtn = new Button("logOut"){
			public void onSubmit() {
	            loginStatus = "Hello";
		        setSessionAtribute(null);		        
			}
	    };
	        
	    logOutBtn.setDefaultFormProcessing(false);	    
	    add(logOutBtn);        
        
        add(new Label("loginStatus"));
        setVisibleLoginFormComponents(userLogin == null);
    }
    
    
    /**
     * Method to set User in session
     * 
     * @param userLogin
     */
    private void setSessionAtribute(String userLogin) {
        Session.get().setAttribute("loggedUser", userLogin);
        Session.get().bind();
        setVisibleLoginFormComponents(userLogin == null);
    }
    
   
    /**
     * Method to hide logging form when logIn and show when logOut
     * @param show
     */
    private void setVisibleLoginFormComponents(boolean show) {
    	loginFld.setVisible(show);
        passFld.setVisible(show);
        loginLbl.setVisible(show);
        passLbl.setVisible(show);
        logInBtn.setVisible(show);
        logOutBtn.setVisible(!show);        
    }
}
