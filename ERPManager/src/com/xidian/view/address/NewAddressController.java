package com.xidian.view.address;

import java.time.LocalDate;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.Customer;
import com.xidian.model.UpdateBalance;
import com.xidian.model.UpdateInfo;
import com.xidian.model.address.Address;
import com.xidian.util.DataValicateUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;

/**
 * 保存收货地址的控制器
 *
 * @author lfq
 *
 */
public class NewAddressController {

	@FXML
	private TextField auidField;

	@FXML
	private TextField receiverNameField;

	@FXML
	private TextArea receiverAddressField;

	@FXML
	private TextField receiverPhoneField;

	// @FXML
	// private AnchorPane editAnchorPane;
	//
	private MainApp mainApp;
	private Address address;

	public NewAddressController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {

	}

	/**
	 * 新建客户
	 */
	@FXML
	private void handleSaveAddress() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setResizable(false);
		alert.setTitle("保存结果");
		alert.setHeaderText("");

		String message = "";
		String phone = receiverPhoneField.getText();
		boolean flag = false; //电话号码输入是否正确标志位
		if (phone != null && phone.length() != 0) {
			String fixedRegExp = "(\\d{3,4}\\-)\\d{8}";
			String mobileRegExp = "((13|14|15|17|18))\\d{9}";
			if ((phone.matches(fixedRegExp) || phone.matches(mobileRegExp))) {
				address = new Address();
				address.setAuid(auidField.getText());
				address.setReceiverName(receiverNameField.getText());
				address.setReceiverAddress(receiverAddressField.getText());
				address.setReceiverPhone(phone);

				SqlSession sqlSession = mainApp.getSqlSession(true);// 非自动提交，可用于事务

				int addAddressResult = 0;

				try {
					addAddressResult = sqlSession.insert("com.xidian.model.address.AddressXml.addAddress", address);
				} finally {
					sqlSession.close();
					if (addAddressResult == 1) {
						message = "保存成功！";
						auidField.setText("");
						receiverNameField.setText("");
						receiverAddressField.setText("");
						receiverPhoneField.setText("");
					} else {
						message = "保存失敗!";
					}
				}
			} else {
				flag = true;
				message = "电话号码输入有误！";
			}
		} else {
			flag = true;
			message = "请输入电话号码！";
		}
		alert.setContentText(message);
		if(flag)
		{
			alert.showAndWait();
		}
		else
		{
			alert.show();
			if (alert.isShowing()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				alert.close();
			}
		}

	}

}
