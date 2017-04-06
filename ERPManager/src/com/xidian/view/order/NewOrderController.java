package com.xidian.view.order;

import java.time.LocalDate;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.order.Order;
import com.xidian.model.UpdateInfo;
import com.xidian.util.DataValicateUtil;
import com.xidian.util.DateUtil;
import com.xidian.util.OrderValicateUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;

/**新建客户订单控制器
 * @author gwh
 *
 */
public class NewOrderController {

	@FXML
	private TextField auIdField;

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private TextField deliveryNameField;

	@FXML
	private TextField orderIdField;

	@FXML
	private TextField wayBillNumberField;

	@FXML
	private ComboBox<String> productIdBox;

	@FXML
	private TextField productNumField;

	@FXML
	private ComboBox<String> productPriceBox;

	@FXML
	private TextField receiverNameField;

	@FXML
	private TextField receiverPhoneField;

	@FXML
	private TextField receiverAddressField;

	@FXML
	private AnchorPane editAnchorPane;

	private MainApp mainApp;
	private Order order;

	public NewOrderController() {

	}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	@FXML
	private void initialize()
	{
		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().addAll("县代", "省代");
		rankBox.getSelectionModel().select("县代");

		productIdBox.getItems().removeAll(productIdBox.getItems());
		productIdBox.getItems().addAll("产品一");
		productIdBox.getSelectionModel().select("产品一");

		productPriceBox.getItems().removeAll(productPriceBox.getItems());
		productPriceBox.getItems().addAll("97元", "67元");
		productPriceBox.getSelectionModel().select("97元");
	}

	/**
	 * 新建客户
	 */
	@FXML
	private void handleSaveOrder()
	{
		order = new Order();
		Alert alert = new Alert(AlertType.INFORMATION);

		//首先验证输入数据
		order.setAuId(auIdField.getText());
		order.setRank(rankBox.getSelectionModel().getSelectedItem());
		order.setDeliveryName(deliveryNameField.getText());
		order.setOrderId(orderIdField.getText());
		order.setWayBillNumber(wayBillNumberField.getText());
		order.setProductId(productIdBox.getSelectionModel().getSelectedItem());
//		order.setProductNum(Integer.parseInt(productNumField.getText()));
		int productPrice = 0;
		if(productPriceBox.getSelectionModel().getSelectedItem().equals("97元"))
		{
			productPrice = 97;
		}
		else
		{
			productPrice = 67;
		}
		order.setProductPrice(productPrice);
		order.setReceiverName(receiverNameField.getText());
		order.setReceiverPhone(receiverPhoneField.getText());
		order.setReceiverAddress(receiverAddressField.getText());
		order.setDeliveryTime(LocalDate.now());
		String tempory=productNumField.getText();
		if(OrderValicateUtil.isInputValid(order,tempory))
		{
			SqlSession sqlSession = mainApp.getSqlSession(false);//非自动提交，可用于事务

			int addOrderResult = 0;
			try {

				addOrderResult = sqlSession.insert("com.xidian.model.order.OrderXml.addOrder", order);

				sqlSession.commit();//提交事务

			}
			finally
			{
				sqlSession.close();
				alert.setResizable(false);
				alert.setTitle("保存结果");
				alert.setHeaderText("");
				if (addOrderResult == 1)// 保存成功后清空表单数据
				{
					alert.setContentText("保存成功！");

					auIdField.setText("");
					rankBox.getSelectionModel().select("县代");
					deliveryNameField.setText("");
					orderIdField.setText("");
					wayBillNumberField.setText("");
					productIdBox.getSelectionModel().select("产品一");
					productNumField.setText("");
					productPriceBox.getSelectionModel().select("97元");
					receiverNameField.setText("");
					receiverPhoneField.setText("");
					receiverAddressField.setText("");

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
