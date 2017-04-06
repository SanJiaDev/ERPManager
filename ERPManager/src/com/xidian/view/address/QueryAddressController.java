package com.xidian.view.address;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.Customer;
import com.xidian.model.UpdateInfo;
import com.xidian.model.address.Address;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**查询，修改,删除收件地址控制器
 * @author lfq
 *
 */
public class QueryAddressController {

	@FXML
	private TextField auidField;

	@FXML
	private TextField receiverNameField;

	@FXML
	private TableView<Address> addressTable;

	@FXML
	private TableColumn<Address, String> auidColumn;

	@FXML
	private TableColumn<Address, String> receiverNameColumn;

	@FXML
	private TableColumn<Address, String> receiverAddressColumn;

	@FXML
	private TableColumn<Address, String> receiverPhoneColumn;

	//修改地址信息
	@FXML
	private TextArea receiverAddressField;

	@FXML
	private TextField receiverPhoneField;

	@FXML
	private AnchorPane editAnchorPane;

	private MainApp mainApp;

	private Address address;

	private ObservableList<Address> addressData = FXCollections.observableArrayList();

	private Stage addStage;

	public QueryAddressController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage addStage) {
		this.addStage = addStage;
	}

	public void setAddress(Address address)
	{
		//设置客户数据到修改表单
		this.address = address;
		auidField.setText(address.getAuid());
		receiverNameField.setText(address.getReceiverName());
		receiverAddressField.setText(address.getReceiverAddress());
		receiverPhoneField.setText(address.getReceiverPhone());

	}

	@FXML
	private void initialize() {

	}

	/**定义列的点击事件类
	 * @author lfq
	 *
	 */
	private class AddressStringCellFactory implements Callback<TableColumn<Address, String>, TableCell<Address, String>> {

	    @Override
	    public TableCell<Address, String> call(TableColumn<Address, String> param) {
	        TextFieldTableCell<Address, String> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	Address selectedAddress = addressTable.getSelectionModel().getSelectedItem();
	            	if(selectedAddress != null)
	            	{
	            		mainApp.showEditAddress(selectedAddress);

	            	}
	            }
	        });
	        return cell;
	    }

	}

	/**
	 * 查询地址信息
	 */
	@FXML
	private void handleQueryAddress() {
		// 清空表中的数据
		addressTable.getItems().clear();

		address = new Address();
		String auid = auidField.getText();
		String receiverName = receiverNameField.getText();

		SqlSession sqlSession = mainApp.getSqlSession(true);

		// 通过授权号查询客户信息
		if (!"".equals(auid.trim())) {
			List<Address> addressByAuid = sqlSession.selectList("com.xidian.model.address.AddressXml.getAddressByAuid", auid);
			addressData.addAll(addressByAuid);
		} else {
			// 如果没有查询信息，则全部查询
			if (("".equals(receiverName.trim()))) {
				List<Address> allAddress = sqlSession.selectList("com.xidian.model.address.AddressXml.getAllAddress");
				addressData.addAll(allAddress);
			}

			// 通过收件人姓名查询客户信息
			if ((!"".equals(receiverName.trim()))) {
				List<Address> addressByName = sqlSession.selectList("com.xidian.model.address.AddressXml.getAddressByName",
						receiverName.trim());
				addressData.addAll(addressByName);
			}

		}

		//表中放数据
		addressTable.setItems(addressData);

		//设置显示过滤列的菜单按钮
		addressTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		auidColumn.setStyle( "-fx-alignment: CENTER;");
		receiverNameColumn.setStyle( "-fx-alignment: CENTER;");
		receiverAddressColumn.setStyle( "-fx-alignment: CENTER;");
		receiverPhoneColumn.setStyle("-fx-alignment: CENTER;");

		// 将数据放入表中的列
		auidColumn.setCellValueFactory(cellData -> cellData.getValue().auidProperty());
		receiverNameColumn.setCellValueFactory(cellData -> cellData.getValue().receiverNameProperty());
		receiverAddressColumn.setCellValueFactory(cellData -> cellData.getValue().receiverAddressProperty());
		receiverPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().receiverPhoneProperty());

		//设置每一列的双击事件
		AddressStringCellFactory addressCellFactory = new AddressStringCellFactory();
		auidColumn.setCellFactory(addressCellFactory);
		receiverNameColumn.setCellFactory(addressCellFactory);
		receiverAddressColumn.setCellFactory(addressCellFactory);
		receiverPhoneColumn.setCellFactory(addressCellFactory);

	}

	/**
	 * 修改地址信息
	 */
	@FXML
	private void handleEditAddress()
	{
		String message = "";
		boolean flag = false; //数据输入是否合法标志位
		if(receiverNameField.getText() == null || "".equals(receiverNameField.getText()))
		{
			message = "请输入收件人姓名";
			flag = true;
		}
		else
		{
			address.setReceiverName(receiverNameField.getText());
		}
		if(receiverAddressField.getText() == null || "".equals(receiverAddressField.getText()))
		{
			message = "请输入收件人地址";
			flag = true;
		}
		else
		{
			address.setReceiverAddress(receiverAddressField.getText());
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setResizable(false);
		alert.setTitle("修改结果");
		alert.setHeaderText("");

		String phone = receiverPhoneField.getText();
		if (phone != null && phone.length() != 0) {
			String fixedRegExp = "(\\d{3,4}\\-)\\d{8}";
			String mobileRegExp = "((13|14|15|17|18))\\d{9}";
			if ((phone.matches(fixedRegExp) || phone.matches(mobileRegExp))) {

				address.setReceiverPhone(phone);

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
			alert.setContentText(message);
			alert.showAndWait();
			return;
		}
		else
		{
			SqlSession sqlSession = mainApp.getSqlSession(true);// 非自动提交，可用于事务

			int editAddressResult = 0;

			try {
				editAddressResult = sqlSession.update("com.xidian.model.address.AddressXml.updateAddress", address);
			} finally {
				sqlSession.close();
				if (editAddressResult == 1) {
					message = "修改成功！";
				} else {
					message = "修改失敗!";
				}
			}
			alert.setContentText(message);
			alert.show();
			if (alert.isShowing()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				alert.close();
			}
			addStage.close();
		}

	}

	@FXML
	private void handleDeleteAddress()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setResizable(false);
		alert.setTitle("");
		alert.setHeaderText("确认删除吗");
		Optional<ButtonType> result = alert.showAndWait();

		Alert alertDelete = new Alert(AlertType.INFORMATION);
		alertDelete.setResizable(false);
		alertDelete.setTitle("删除结果");
		alertDelete.setHeaderText("");

		if(result.get() == ButtonType.OK)
		{

			SqlSession sqlSession = mainApp.getSqlSession(true);
			int deleteResult = 0;
			try
			{
				deleteResult = sqlSession.delete("com.xidian.model.address.AddressXml.deleteAddress",address.getAuid());
			}
			finally
			{
				if(deleteResult == 1)
				{
//					int selectedIndex = addressTable.getSelectionModel().getSelectedIndex();
//					addressTable.getItems().remove(selectedIndex);
					alertDelete.setContentText("删除成功！");
				}
				else
				{
					alertDelete.setContentText("删除失败！");
				}
			}

		}
		else
		{
			return;
		}
		alertDelete.show();
		if (alertDelete.isShowing()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alertDelete.close();
		}
		addStage.close();
	}

}
