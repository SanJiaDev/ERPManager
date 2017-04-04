package com.xidian.model;

import java.time.LocalDate;

import com.xidian.util.DateUtil;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;

/**客户信息实体类
 * @author lfq
 *
 */
public class Customer {

	private IntegerProperty id;
	private StringProperty auid;
	private StringProperty rank;
	private StringProperty customerName;
	private StringProperty sex;
	private IntegerProperty age;
	private StringProperty idcard;
	private StringProperty area;
	private StringProperty address;
	private StringProperty phone;
	private StringProperty qq;
	private StringProperty weixin;
	private StringProperty state;
	private IntegerProperty balance;
	private ObjectProperty<LocalDate> createTime;

	public Customer() {

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
	public String getRank() {
		return rank.get();
	}
	public StringProperty rankProperty(){
		return rank;
	}
	public String getCustomerName() {
		return customerName.get();
	}
	public StringProperty customerNameProperty(){
		return customerName;
	}
	public String getSex() {
		return sex.get();
	}
	public StringProperty sexProperty(){
		return sex;
	}
	public int getAge() {
		return age.get();
	}
	public IntegerProperty ageProperty(){
		return age;
	}
	public String getIdcard() {
		return idcard.get();
	}
	public StringProperty idcardProperty(){
		return idcard;
	}
	public String getArea() {
		return area.get();
	}
	public StringProperty areaProperty(){
		return area;
	}
	public String getAddress() {
		return address.get();
	}
	public StringProperty addressProperty(){
		return address;
	}
	public String getPhone() {
		return phone.get();
	}
	public StringProperty phoneProperty(){
		return phone;
	}
	public String getQq() {
		return qq.get();
	}
	public StringProperty qqProperty(){
		return qq;
	}
	public String getWeixin() {
		return weixin.get();
	}
	public StringProperty weixinProperty(){
		return weixin;
	}
	public int getBalance() {
		return balance.get();
	}
	public IntegerProperty balanceProperty(){
		return balance;
	}
	public String getState() {
		return state.get();
	}
	public StringProperty stateProperty(){
		return state;
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public void setAuid(String auid) {
		this.auid= new SimpleStringProperty(auid);
	}
	public void setRank(String rank) {
		this.rank = new SimpleStringProperty(rank);
	}
	public void setCustomerName(String customerName) {
		this.customerName = new SimpleStringProperty(customerName);
	}
	public void setSex(String sex) {
		this.sex = new SimpleStringProperty(sex);
	}
	public void setAge(int age) {
		this.age = new SimpleIntegerProperty(age);
	}
	public void setIdcard(String idcard) {
		this.idcard = new SimpleStringProperty(idcard);
	}
	public void setArea(String area) {
		this.area = new SimpleStringProperty(area);
	}
	public void setAddress(String address) {
		this.address = new SimpleStringProperty(address);
	}
	public void setPhone(String phone) {
		this.phone = new SimpleStringProperty(phone);
	}
	public void setQq(String qq) {
		this.qq = new SimpleStringProperty(qq);
	}
	public void setWeixin(String weixin) {
		this.weixin = new SimpleStringProperty(weixin);
	}
	public void setState(String state) {
		this.state = new SimpleStringProperty(state);
	}
    public LocalDate getCreateTime() {
        return createTime.get();
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = new SimpleObjectProperty<LocalDate>(createTime);
    }

	public void setBalance(int balance) {
		this.balance = new SimpleIntegerProperty(balance);
	}

    public ObjectProperty<LocalDate> createTimeProperty() {
        return createTime;
    }




}
