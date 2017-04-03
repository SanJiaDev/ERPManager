package com.xidian.view;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.Customer;
import com.xidian.util.DataValicateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**查询，修改控制器
 * @author lfq
 *
 */
public class QueryCustomerController {

	@FXML
	private TextField auidField;

	@FXML
	private TextField customernameField;

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private TableView<Customer> customerTable;

	@FXML
	private TableColumn<Customer, Integer> idColumn;

	@FXML
	private TableColumn<Customer, String> auidColumn;

	@FXML
	private TableColumn<Customer, String> rankColumn;

	@FXML
	private TableColumn<Customer, String> customernameColumn;

	@FXML
	private TableColumn<Customer, String> sexColumn;

	@FXML
	private TableColumn<Customer, Integer> ageColumn;

	@FXML
	private TableColumn<Customer, String> idcardColumn;

	@FXML
	private TableColumn<Customer, String> areaColumn;

	@FXML
	private TableColumn<Customer, String> addressColumn;

	@FXML
	private TableColumn<Customer, String> phoneColumn;

	@FXML
	private TableColumn<Customer, String> qqColumn;

	@FXML
	private TableColumn<Customer, String> weixinColumn;

	@FXML
	private TableColumn<Customer, Integer> stateColumn;

	@FXML
	private TableColumn<Customer, LocalDate> createTimeColumn;

	//修改客户信息属性
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

	private ObservableList<Customer> customerData = FXCollections.observableArrayList();

	private Stage editStage;

	private boolean okClicked = false;

	public QueryCustomerController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage editStage) {
		this.editStage = editStage;
	}

	public void setCustomer(Customer customer)
	{
		//设置客户数据到修改表单
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
	private void initialize() {

		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().addAll("请选择", "县代", "省代");
		rankBox.getSelectionModel().select("请选择");

	}

	/**定义列的点击事件类
	 * @author lfq
	 *
	 */
	private class CustomerIntegerCellFactory implements Callback<TableColumn<Customer, Integer>, TableCell<Customer, Integer>> {

	    @Override
	    public TableCell<Customer, Integer> call(TableColumn<Customer, Integer> param) {
	        TextFieldTableCell<Customer, Integer> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
	            	if(selectedCustomer != null)
	            	{
	            		mainApp.showEditCustomer(selectedCustomer);

	            	}
	            }
	        });
	        return cell;
	    }

	}
	private class CustomerStringCellFactory implements Callback<TableColumn<Customer, String>, TableCell<Customer, String>> {

		@Override
		public TableCell<Customer, String> call(TableColumn<Customer, String> param) {
			TextFieldTableCell<Customer, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
	            	if(selectedCustomer != null)
	            	{
	            		mainApp.showEditCustomer(selectedCustomer);

	            	}
				}
			});
			return cell;
		}
	}
	private class CustomerLocalDateCellFactory implements Callback<TableColumn<Customer, LocalDate>, TableCell<Customer, LocalDate>> {

		@Override
		public TableCell<Customer, LocalDate> call(TableColumn<Customer, LocalDate> param) {
			TextFieldTableCell<Customer, LocalDate> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
	            	if(selectedCustomer != null)
	            	{
	            		mainApp.showEditCustomer(selectedCustomer);

	            	}
				}
			});
			return cell;
		}
	}
	/**
	 * 查询客户信息
	 */
	@FXML
	private void handleQueryCustomer() {
		// 清空表中的数据
		customerTable.getItems().clear();

		customer = new Customer();
		String auid = auidField.getText();
		String customerName = customernameField.getText();
		String rank = rankBox.getSelectionModel().getSelectedItem();

		SqlSession sqlSession = mainApp.getSqlSession();

		// 通过授权号查询客户信息
		if (!"".equals(auid.trim())) {
			Customer customerByAuid = sqlSession.selectOne("com.xidian.CustomerXml.getCustomerByAuid", auid);
			customerData.add(customerByAuid);
		} else {
			// 如果没有查询信息，则全部查询
			if (("".equals(customerName.trim())) && ("请选择".equals(rank))) {
				List<Customer> customers = sqlSession.selectList("com.xidian.CustomerXml.getAllCustomer");
				customerData.addAll(customers);
			}
			// 通过代理级别查询客户信息
			if (("".equals(customerName.trim())) && (!"请选择".equals(rank))) {
				List<Customer> customersByRank = sqlSession.selectList("com.xidian.CustomerXml.getCustomerByRank",
						rank);
				customerData.addAll(customersByRank);
			}

			// 通过客户姓名查询客户信息
			if ((!"".equals(customerName.trim())) && ("请选择".equals(rank))) {
				List<Customer> customersByName = sqlSession.selectList("com.xidian.CustomerXml.getCustomerByName",
						customerName);
				customerData.addAll(customersByName);
			}

			// 通过代理级别和客户姓名查询客户信息
			if ((!"".equals(customerName.trim())) && (!"请选择".equals(rank))) {
				customer.setRank(rank);
				customer.setCustomerName(customerName);
				List<Customer> customersByRankAndName = sqlSession
						.selectList("com.xidian.CustomerXml.getCustomerByRankAndName", customer);
				customerData.addAll(customersByRankAndName);
			}
		}

		//表中放数据
		customerTable.setItems(customerData);

		//设置显示过滤列的菜单按钮
		customerTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		idColumn.setStyle( "-fx-alignment: CENTER;");
		auidColumn.setStyle( "-fx-alignment: CENTER;");
		rankColumn.setStyle( "-fx-alignment: CENTER;");
		customernameColumn.setStyle( "-fx-alignment: CENTER;");
		sexColumn.setStyle( "-fx-alignment: CENTER;");
		ageColumn.setStyle( "-fx-alignment: CENTER;");
		idcardColumn.setStyle( "-fx-alignment: CENTER;");
		areaColumn.setStyle( "-fx-alignment: CENTER;");
		addressColumn.setStyle( "-fx-alignment: CENTER;");
		phoneColumn.setStyle( "-fx-alignment: CENTER;");
		qqColumn.setStyle( "-fx-alignment: CENTER;");
		weixinColumn.setStyle( "-fx-alignment: CENTER;");
		stateColumn.setStyle( "-fx-alignment: CENTER;");
		createTimeColumn.setStyle( "-fx-alignment: CENTER;");

		// 将数据放入表中的列
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		auidColumn.setCellValueFactory(cellData -> cellData.getValue().auidProperty());
		rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
		customernameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
		sexColumn.setCellValueFactory(cellData -> cellData.getValue().sexProperty());
		ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
		idcardColumn.setCellValueFactory(cellData -> cellData.getValue().idcardProperty());
		areaColumn.setCellValueFactory(cellData -> cellData.getValue().areaProperty());
		addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
		phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		qqColumn.setCellValueFactory(cellData -> cellData.getValue().qqProperty());
		weixinColumn.setCellValueFactory(cellData -> cellData.getValue().weixinProperty());
		stateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty().asObject());
		createTimeColumn.setCellValueFactory(cellData -> cellData.getValue().createTimeProperty());

		//设置每一列的双击事件
		idColumn.setCellFactory(new CustomerIntegerCellFactory());
		auidColumn.setCellFactory(new CustomerStringCellFactory());
		rankColumn.setCellFactory(new CustomerStringCellFactory());
		customernameColumn.setCellFactory(new CustomerStringCellFactory());
		sexColumn.setCellFactory(new CustomerStringCellFactory());
		ageColumn.setCellFactory(new CustomerIntegerCellFactory());
		idcardColumn.setCellFactory(new CustomerStringCellFactory());
		areaColumn.setCellFactory(new CustomerStringCellFactory());
		addressColumn.setCellFactory(new CustomerStringCellFactory());
		phoneColumn.setCellFactory(new CustomerStringCellFactory());
		qqColumn.setCellFactory(new CustomerStringCellFactory());
		weixinColumn.setCellFactory(new CustomerStringCellFactory());
		stateColumn.setCellFactory(new CustomerIntegerCellFactory());
		auidColumn.setCellFactory(new CustomerStringCellFactory());
		createTimeColumn.setCellFactory(new CustomerLocalDateCellFactory());

	}

	/**
	 * 修改客户信息
	 */
	@FXML
	private void handleEditCustomer()
	{
		Alert alert = new Alert(AlertType.INFORMATION);

		// 首先验证输入数据
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
//		customer.setCreateTime(LocalDate.now()); //修改，创建客户时间不变
		if (DataValicateUtil.isInputValid(customer)) {

			SqlSession sqlSession = mainApp.getSqlSession();
			int result = sqlSession.update("com.xidian.CustomerXml.updateCustomer", customer);
			sqlSession.close();

			alert.setResizable(false);
			alert.setTitle("修改结果");
			alert.setHeaderText("");
			if (result == 1)// 修改成功
			{
				alert.setContentText("修改成功！");
			}
			else {
				alert.setContentText("修改失败！");
			}
			alert.show();
			if (alert.isShowing()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				alert.close();
			}
			editStage.close();// 关闭修改客户信息界面
		}
	}

}
