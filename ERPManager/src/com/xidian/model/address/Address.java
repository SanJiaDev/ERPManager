package com.xidian.model.address;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**收货地址实体类
 * @author lfq
 *
 */
public class Address {

	private IntegerProperty id;
	private StringProperty auid;
	private StringProperty receiverName;
	private StringProperty receiverAddress;
	private StringProperty receiverPhone;

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
	public String getReceiverName() {
		return receiverName.get();
	}
	public StringProperty receiverNameProperty(){
		return receiverName;
	}
	public String getReceiverAddress() {
		return receiverAddress.get();
	}
	public StringProperty receiverAddressProperty(){
		return receiverAddress;
	}
	public String getReceiverPhone() {
		return receiverPhone.get();
	}
	public StringProperty receiverPhoneProperty(){
		return receiverPhone;
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public void setAuid(String auid) {
		this.auid = new SimpleStringProperty(auid);
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = new SimpleStringProperty(receiverName);
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress= new SimpleStringProperty(receiverAddress);
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone= new SimpleStringProperty(receiverPhone);
	}
}
