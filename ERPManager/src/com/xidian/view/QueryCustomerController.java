package com.xidian.view;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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

	private MainApp mainApp;
	private Customer customer;
	private ObservableList<Customer> customerData = FXCollections.observableArrayList();

	public QueryCustomerController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {
		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().addAll("请选择", "县代", "省代");
		rankBox.getSelectionModel().select("请选择");

		// areaField.getItems().removeAll(areaField.getItems());
		// areaField.getItems().addAll("西安", "长安", "宝鸡", "凤翔", "歧山");
		// areaField.getSelectionModel().select("西安");

	}

	/**
	 * 查询客户
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

		// 将数据放入表
		customerTable.setItems(customerData);
		customerTable.setTableMenuButtonVisible(true);
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

	}
}
