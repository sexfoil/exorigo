package forms;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import java.sql.SQLException;

import tools.DBworker;
import tools.User;

/**
 * Search form for searching
 * @author Slava Poliakov *
 */
public class SearchForm extends Form {

    private String searchName;    
    private String searchStatus;
    
    private TextField searchFld;    
    private Button findBtn;

	public SearchForm(String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel(this));
		
	    searchFld = new TextField("searchName");
	    
        findBtn = new Button("findUser"){
        	@Override
			public void onSubmit() {
				User user = null;
		    	
		    	String[] nameSet;
		    	if (searchName == null) {
		    		nameSet = new String[]{"_", "_"};
		    	} else {
		    		nameSet = searchName.replace("  ", " ").split(" ");
		    	}
		    	
		    	String name1 = nameSet[0].trim();
		    	String name2 = nameSet.length < 2 ? "" : nameSet[1].trim();

		    	try {
					DBworker db = new DBworker();					
					user = db.findUser(name1, name2);
					db.closeAll();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
		    			    	
		        if (user != null){
		        	searchStatus = "RESULT:  " + user.getUserInfo() + " !";		            
		        }else{
		        	searchStatus = "User not found !";
		        }
			}
        };
       

        add(findBtn);
        add(searchFld);
        add(new Label("searchStatus")); 
	}

}
