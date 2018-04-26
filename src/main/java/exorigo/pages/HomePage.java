package exorigo.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.sql.SQLException;
import java.util.List;

import forms.ActionForm;
import forms.LoginForm;
import forms.SearchForm;
import tools.DBworker;
import tools.User;



public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	private List<User> userList;
	private DBworker db;
	
	private LoginForm loginForm;
	private ActionForm actionForm;
	private SearchForm searchForm;

	public HomePage(final PageParameters parameters) {		
		
		try {
			db = new DBworker();
			userList = db.getAllUsers();			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		loginForm = new LoginForm("loginForm");
		searchForm = new SearchForm("searchForm");
		actionForm = new ActionForm("actionForm", this);
		
		add(loginForm);
		add(searchForm);
		add(actionForm);
		
		add(new ListView("userList", userList) {
			int num = 1;
		    protected void populateItem(ListItem item) {		    	
		        User user = (User) item.getModelObject();
		        item.add(new Label("num", num++));
		        item.add(new Label("firstName", user.getFirstName()));
		        item.add(new Label("lastName", user.getLastName()));
		        item.add(new Label("login", user.getLogin()));
		        if (num > userList.size()) {
		        	num = 1;
		        }
		    }
		});
    }		
}
