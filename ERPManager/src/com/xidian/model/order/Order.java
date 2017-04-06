package com.xidian.model.order;

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

/**客户订单实体类
 * @author gwh
 *
 */
public class Order {

	private IntegerProperty id;
	private StringProperty  auId;
	private StringProperty  rank;
	private StringProperty  deliveryName;
	private StringProperty  orderId;
	private StringProperty  wayBillNumber;
	private StringProperty  productId;
	private IntegerProperty productNum;
	private IntegerProperty  productPrice;
	private StringProperty  receiverName;
	private StringProperty  receiverPhone;
	private StringProperty  receiverAddress;
	private ObjectProperty<LocalDate> deliveryTime;

	public Order() {

	}
	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public String getAuId() {
		return auId.get();
	}
	public StringProperty auIdProperty(){
		return auId;
	}
	public String getRank() {
		return rank.get();
	}
	public StringProperty rankProperty(){
		return rank;
	}
	public String getDeliveryName() {
		return deliveryName.get();
	}
	public StringProperty deliveryNameProperty(){
		return deliveryName;
	}
	public String getOrderId() {
		return orderId.get();
	}
	public StringProperty orderIdProperty(){
		return orderId;
	}
	public String getWayBillNumber() {
		return wayBillNumber.get();
	}
	public StringProperty wayBillNumberProperty(){
		return wayBillNumber;
	}
	public String getProductId() {
		return productId.get();
	}
	public StringProperty productIdProperty(){
		return productId;
	}
	public int getProductNum() {
		return productNum.get();
	}
	public IntegerProperty productNumProperty(){
		return productNum;
	}
	public int getProductPrice() {
		return productPrice.get();
	}
	public IntegerProperty productPriceProperty(){
		return productPrice;
	}
	public String getReceiverName() {
		return receiverName.get();
	}
	public StringProperty receiverNameProperty(){
		return receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone.get();
	}
	public StringProperty receiverPhoneProperty(){
		return receiverPhone;
	}
	public String getReceiverAddress() {
		return receiverAddress.get();
	}
	public StringProperty receiverAddressProperty(){
		return receiverAddress;
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public void setAuId(String auId) {
		this.auId= new SimpleStringProperty(auId);
	}
	public void setRank(String rank) {
		this.rank = new SimpleStringProperty(rank);
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = new SimpleStringProperty(deliveryName);
	}
	public void setOrderId(String orderId) {
		this.orderId = new SimpleStringProperty(orderId);
	}
	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = new SimpleStringProperty(wayBillNumber);
	}
	public void setProductId(String productId) {
		this.productId = new SimpleStringProperty(productId);
	}
	public void setProductNum(int productNum) {
		this.productNum = new SimpleIntegerProperty(productNum);
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = new SimpleIntegerProperty(productPrice);
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = new SimpleStringProperty(receiverName);
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = new SimpleStringProperty(receiverPhone);
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = new SimpleStringProperty(receiverAddress);
	}
    public LocalDate getDeliveryTime() {
        return deliveryTime.get();
    }

    public void setDeliveryTime(LocalDate deliveryTime) {
        this.deliveryTime = new SimpleObjectProperty<LocalDate>(deliveryTime);
    }

    public ObjectProperty<LocalDate> deliveryTimeProperty() {
        return deliveryTime;
    }




}
