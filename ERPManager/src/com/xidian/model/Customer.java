package com.xidian.model;


public class Customer {

	private int id;
	private String auid;
	private String rank;
	private String customerName;
	private String sex;
	private int age;
	private String idcard;
	private String area;
	private String address;
	private String phone;
	private String qq;
	private String weixin;
	private int state;// 0：激活 1：未激活
	public Customer() {

	}
	public int getId() {
		return id;
	}
	public String getAuid() {
		return auid;
	}
	public String getRank() {
		return rank;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getSex() {
		return sex;
	}
	public int getAge() {
		return age;
	}
	public String getIdcard() {
		return idcard;
	}
	public String getArea() {
		return area;
	}
	public String getAddress() {
		return address;
	}
	public String getPhone() {
		return phone;
	}
	public String getQq() {
		return qq;
	}
	public String getWeixin() {
		return weixin;
	}
	public int getState() {
		return state;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAuid(String auid) {
		this.auid = auid;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", auid=" + auid + ", rank=" + rank + ", customerName=" + customerName + ", sex="
				+ sex + ", age=" + age + ", idcard=" + idcard + ", area=" + area + ", address=" + address + ", phone="
				+ phone + ", qq=" + qq + ", weixin=" + weixin + ", state=" + state + "]";
	}



}
