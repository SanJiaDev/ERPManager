package com.xidian.view;

import java.time.LocalDate;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.Customer;
import com.xidian.model.UpdateBalance;
import com.xidian.model.UpdateInfo;
import com.xidian.util.DataValicateUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;

/**保存客户信息控制器
 * @author lfq
 *
 */
public class NewCustomerController {

	@FXML
	private TextField auidField;

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private TextField customernameField;

	@FXML
	private ComboBox<String> sexBox;

	@FXML
	private TextField idcardField;

	@FXML
	private ComboBox<String> areaField;

	@FXML
	private TextField addressField;

	@FXML
	private TextField phoneField;

	@FXML
	private TextField qqField;

	@FXML
	private TextField weixinField;

	@FXML
	private TextField createTimeField;

	@FXML
	private AnchorPane editAnchorPane;

	private MainApp mainApp;
	private Customer customer;

	public NewCustomerController() {

	}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	public void setCustomer(Customer customer)
	{
		//设置客户数据
		this.customer = customer;

		auidField.setText(customer.getAuid());

		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().addAll("县代", "省代");
		rankBox.getSelectionModel().select(customer.getRank());

		customernameField.setText(customer.getCustomerName());

		sexBox.getItems().removeAll(sexBox.getItems());
		sexBox.getItems().addAll("男", "女");
		sexBox.getSelectionModel().select(customer.getSex());

		idcardField.setText(customer.getIdcard());

		areaField.getItems().removeAll(areaField.getItems());
		areaField.getItems().addAll("西安", "长安", "宝鸡", "凤翔", "歧山");
		areaField.getSelectionModel().select(customer.getArea());

		addressField.setText(customer.getAddress());

		phoneField.setText(customer.getPhone());

		qqField.setText(customer.getQq());

		weixinField.setText(customer.getWeixin());

	}

	@FXML
	private void initialize()
	{
		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().addAll("县代", "省代");
		rankBox.getSelectionModel().select("县代");

		sexBox.getItems().removeAll(sexBox.getItems());
		sexBox.getItems().addAll("男", "女");
		sexBox.getSelectionModel().select("男");

		areaField.getItems().removeAll(areaField.getItems());
		areaField.getItems().addAll("西安", "长安", "宝鸡", "凤翔", "歧山");
		areaField.getSelectionModel().select("西安");
	}

	/**
	 * 新建客户
	 */
	@FXML
	private void handleSaveCustomer()
	{
		customer = new Customer();
		Alert alert = new Alert(AlertType.INFORMATION);

		//首先验证输入数据
		customer.setAuid(auidField.getText());
		customer.setRank(rankBox.getSelectionModel().getSelectedItem());
		customer.setCustomerName(customernameField.getText());
		customer.setSex(sexBox.getSelectionModel().getSelectedItem());
		customer.setIdcard(idcardField.getText());
		customer.setArea(areaField.getSelectionModel().getSelectedItem());
		customer.setAddress(addressField.getText());
		customer.setPhone(phoneField.getText());
		customer.setQq(qqField.getText());
		customer.setWeixin(weixinField.getText());
		customer.setCreateTime(LocalDate.now());
		if(DataValicateUtil.isInputValid(customer))
		{
			SqlSession sqlSession = mainApp.getSqlSession(false);//非自动提交，可用于事务

			int addCustomerResult = 0;
			int addBalanceResult = 0;
			int addUpdateInfoResult = 0;
			int addUpdateBalance = 0;
			try {

				addCustomerResult = sqlSession.insert("com.xidian.CustomerXml.addCustomer", customer);

				addBalanceResult = sqlSession.insert("com.xidian.BalanceXml.addBalance", customer.getAuid());

				//封装更新客户信息
				UpdateInfo updateInfo = new UpdateInfo();
				updateInfo.setAuid(customer.getAuid());
				updateInfo.setRank(customer.getRank());
				updateInfo.setState("注册");
				updateInfo.setUpdateTime(LocalDate.now());
				updateInfo.setUpdateReason("注册");

				addUpdateInfoResult = sqlSession.insert("com.xidian.UpdateInfoXml.addUpdateInfo", updateInfo);

				UpdateBalance updateBalance = new UpdateBalance();
				updateBalance.setAuid(customer.getAuid());
				updateBalance.setPreBalance(0);
				updateBalance.setUpdateBalance(0);
				updateBalance.setPosBalance(0);
				updateBalance.setUpdateTime(LocalDate.now());
				updateBalance.setUpdateReason("注册");

				addUpdateBalance = sqlSession.insert("com.xidian.UpdateBalanceXml.addUpdateBalance", updateBalance);

				sqlSession.commit();//提交事务

			}
			finally
			{
				sqlSession.close();
				alert.setResizable(false);
				alert.setTitle("保存结果");
				alert.setHeaderText("");
				if (addCustomerResult == 1 && addBalanceResult == 1 && addUpdateInfoResult == 1 && addUpdateBalance == 1)// 保存成功后清空表单数据
				{
					alert.setContentText("保存成功！");

					auidField.setText("");
					rankBox.getSelectionModel().select("县代");
					customernameField.setText("");
					sexBox.getSelectionModel().select("男");
					idcardField.setText("");
					areaField.getSelectionModel().select("西安");
					addressField.setText("");
					phoneField.setText("");
					qqField.setText("");
					weixinField.setText("");

				}
				else
				{
					alert.setContentText("保存失败！");
				}

				alert.show();
				if(alert.isShowing())
				{
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


}
