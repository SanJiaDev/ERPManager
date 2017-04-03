package com.xidian.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**用户管理实体类
 * @author lfq
 *
 */
public class ManagerUser {

	private  IntegerProperty id;
	private final StringProperty username;
	private final StringProperty password;
	public ManagerUser() {
		this(null, null);
	}
	public ManagerUser(String username, String password) {

		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
	}
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public IntegerProperty getIdProperty(){
		return id;
	}

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String firstName) {
        this.username.set(firstName);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String lastName) {
        this.password.set(lastName);
    }

    public StringProperty passwordProperty() {
        return password;
    }


}
