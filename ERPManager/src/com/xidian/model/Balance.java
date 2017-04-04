package com.xidian.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Balance {

	private IntegerProperty id;
	private StringProperty auid;
	private IntegerProperty balance;

	public Balance() {
		super();
	}

	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public String getAuid() {
		return auid.get();
	}
	public StringProperty auidProperty(){
		return auid;
	}
	public int getBalance() {
		return balance.get();
	}
	public IntegerProperty balanceProperty(){
		return balance;
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public void setAuid(String auid) {
		this.auid= new SimpleStringProperty(auid);
	}
	public void setBalance(int balance) {
		this.balance = new SimpleIntegerProperty(balance);
	}

}
