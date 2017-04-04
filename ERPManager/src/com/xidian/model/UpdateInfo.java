package com.xidian.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**客户更新级别，状态实体类
 * @author lfq
 *
 */
public class UpdateInfo {
	private IntegerProperty id;
	private StringProperty auid;
	private StringProperty rank;
	private IntegerProperty state;// 0：激活 1：未激活
	private ObjectProperty<LocalDate> updateTime;
	private StringProperty updateReason;

	public UpdateInfo() {

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
	public int getState() {
		return state.get();
	}
	public IntegerProperty stateProperty(){
		return state;
	}
	public LocalDate getUpdateTime() {
	        return updateTime.get();
	}
	public ObjectProperty<LocalDate> updateTimeProperty() {
	        return updateTime;
	}
	public String getUpdateReason() {
		return updateReason.get();
	}
	public StringProperty updateReasonProperty(){
		return updateReason;
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
	public void setState(int state) {
		this.state = new SimpleIntegerProperty(state);
	}
    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = new SimpleObjectProperty<LocalDate>(updateTime);
    }
	public void setUpdateReason(String updateReason) {
		this.updateReason = new SimpleStringProperty(updateReason);
	}

}
