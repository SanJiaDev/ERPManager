package com.xidian.view.order;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.order.Order;
import com.xidian.util.DateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**订单查询控制器
 * @author gwh
 *
 */
public class QueryOrderController {

	@FXML
	private TextField orderIdField;

	@FXML
	private TextField auIdField;

	@FXML
	private TableView<Order> orderTable;

	@FXML
	private TableColumn<Order, Integer> idColumn;

	@FXML
	private TableColumn<Order, String> orderIdColumn;

	@FXML
	private TableColumn<Order, String> wayBillNumberColumn;

	@FXML
	private TableColumn<Order, String> rankColumn;

	@FXML
	private TableColumn<Order, String> auIdColumn;

	@FXML
	private TableColumn<Order, LocalDate> deliveryTimeColumn;

	@FXML
	private TableColumn<Order, String> deliveryNameColumn;

	@FXML
	private TableColumn<Order, String> productIdColumn;

	@FXML
	private TableColumn<Order, Integer> productNumColumn;

	@FXML
	private TableColumn<Order, Integer> productPriceColumn;

	@FXML
	private TableColumn<Order, String> receiverNameColumn;

	@FXML
	private TableColumn<Order, String> receiverPhoneColumn;

	@FXML
	private TableColumn<Order, String> receiverAddressColumn;

	//显示订单属性
	@FXML
	private TextField order2IdField;

	@FXML
	private TextField rankField;

	@FXML
	private TextField auId2Field;

	@FXML
	private TextField wayBillNumberField;

	@FXML
	private TextField deliveryTimeField;

	@FXML
	private TextField deliveryNameField;

	@FXML
	private TextField productIdField;

	@FXML
	private TextField productNumField;

	@FXML
	private TextField productPriceField;

	@FXML
	private TextField receiverNameField;

	@FXML
	private TextField receiverPhoneField;

	@FXML
	private TextField receiverAddressField;

	private MainApp mainApp;

	private Order order;

	private Stage editStage;

	private ObservableList<Order> orderData = FXCollections.observableArrayList();

	public QueryOrderController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage editStage) {
		this.editStage = editStage;
	}

	@FXML
	private void initialize() {

	}

	/**
	 * 设置订单信息
	 * @param Order
	 */
	public void setOrder(Order order)
	{
		//设置客户数据到修改表单
		this.order = order;

		order2IdField.setText(order.getAuId());

		rankField.setText(order.getRank());

		auId2Field.setText(order.getAuId());

		wayBillNumberField.setText(order.getWayBillNumber());

		deliveryTimeField.setText(DateUtil.format(order.getDeliveryTime()));

		deliveryNameField.setText(order.getDeliveryName());

		productIdField.setText(order.getProductId());

		productNumField.setText(String.valueOf(order.getProductNum()));

		productPriceField.setText(String.valueOf(order.getProductPrice()));

		receiverNameField.setText(order.getReceiverName());

		receiverPhoneField.setText(order.getReceiverPhone());

		receiverAddressField.setText(order.getReceiverAddress());

	}

	/**定义列的点击事件类
	 * @author gwh
	 *
	 */
	private class OrderIntegerCellFactory implements Callback<TableColumn<Order, Integer>, TableCell<Order, Integer>> {

	    @Override
	    public TableCell<Order, Integer> call(TableColumn<Order, Integer> param) {
	        TextFieldTableCell<Order, Integer> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
	            	if(selectedOrder != null)
	            	{
	            		mainApp.showOrderInfo(selectedOrder);

	            	}
	            }
	        });
	        return cell;
	    }

	}
	private class OrderStringCellFactory implements Callback<TableColumn<Order, String>, TableCell<Order, String>> {

		@Override
		public TableCell<Order, String> call(TableColumn<Order, String> param) {
			TextFieldTableCell<Order, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
	            	if(selectedOrder != null)
	            	{
	            		mainApp.showOrderInfo(selectedOrder);

	            	}
				}
			});
			return cell;
		}
	}
	private class OrderLocalDateCellFactory implements Callback<TableColumn<Order, LocalDate>, TableCell<Order, LocalDate>> {

		@Override
		public TableCell<Order, LocalDate> call(TableColumn<Order, LocalDate> param) {
			TextFieldTableCell<Order, LocalDate> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
	            	if(selectedOrder != null)
	            	{
	            		mainApp.showOrderInfo(selectedOrder);

	            	}
				}
			});
			return cell;
		}
	}


	/**
	 * 查询订单信息
	 */
	@FXML
	private void handleQueryOrder() {
		// 清空表中的数据
		orderTable.getItems().clear();

		order = new Order();
		String orderId = orderIdField.getText();
		String auId = auIdField.getText();

		SqlSession sqlSession = mainApp.getSqlSession(true);

		// 通过订单号、授权号查询客户信息
		if (!"".equals(orderId.trim())) {
			Order orderByOrderid = sqlSession.selectOne("com.xidian.model.order.OrderXml.getOrderByOrderId", orderId);
			orderData.add(orderByOrderid);
		} else {
			// 如果没有订单信息，则查询授权号
			if (!"".equals(auId.trim()) ) {
				List<Order> orders = sqlSession.selectList("com.xidian.model.order.OrderXml.getOrderByAuId", auId);
				orderData.addAll(orders);
			}
			// 如果订单信息、授权号都没有则查询全部的信息
			if ("".equals(auId.trim()) ) {
				List<Order> ordersAll = sqlSession.selectList("com.xidian.model.order.OrderXml.getOrderAll");
				orderData.addAll(ordersAll);
			}

		}

		//表中放数据
		orderTable.setItems(orderData);

		//设置显示过滤列的菜单按钮
		orderTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		orderIdColumn.setStyle( "-fx-alignment: CENTER;");
		rankColumn.setStyle( "-fx-alignment: CENTER;");
		auIdColumn.setStyle( "-fx-alignment: CENTER;");
		wayBillNumberColumn.setStyle( "-fx-alignment: CENTER;");
		deliveryTimeColumn.setStyle( "-fx-alignment: CENTER;");
		deliveryNameColumn.setStyle( "-fx-alignment: CENTER;");
		productIdColumn.setStyle( "-fx-alignment: CENTER;");
		productNumColumn.setStyle( "-fx-alignment: CENTER;");
		productPriceColumn.setStyle( "-fx-alignment: CENTER;");
		receiverNameColumn.setStyle( "-fx-alignment: CENTER;");
		receiverPhoneColumn.setStyle( "-fx-alignment: CENTER;");
		receiverAddressColumn.setStyle( "-fx-alignment: CENTER;");

		// 将数据放入表中的列
		orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
		rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
		auIdColumn.setCellValueFactory(cellData -> cellData.getValue().auIdProperty());
		wayBillNumberColumn.setCellValueFactory(cellData -> cellData.getValue().wayBillNumberProperty());
		deliveryTimeColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryTimeProperty());
		deliveryNameColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryNameProperty());
		productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
		productNumColumn.setCellValueFactory(cellData -> cellData.getValue().productNumProperty().asObject());
		productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
		receiverNameColumn.setCellValueFactory(cellData -> cellData.getValue().receiverNameProperty());
		receiverPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().receiverPhoneProperty());
		receiverAddressColumn.setCellValueFactory(cellData -> cellData.getValue().receiverAddressProperty());

		//设置每一列的双击事件
		orderIdColumn.setCellFactory(new OrderStringCellFactory());
		rankColumn.setCellFactory(new OrderStringCellFactory());
		auIdColumn.setCellFactory(new OrderStringCellFactory());
		wayBillNumberColumn.setCellFactory(new OrderStringCellFactory());
		deliveryTimeColumn.setCellFactory(new OrderLocalDateCellFactory());
		deliveryNameColumn.setCellFactory(new OrderStringCellFactory());
		productIdColumn.setCellFactory(new OrderStringCellFactory());
		productNumColumn.setCellFactory(new OrderIntegerCellFactory());
		productPriceColumn.setCellFactory(new OrderIntegerCellFactory());
		receiverNameColumn.setCellFactory(new OrderStringCellFactory());
		receiverPhoneColumn.setCellFactory(new OrderStringCellFactory());
		receiverAddressColumn.setCellFactory(new OrderStringCellFactory());

	}
	@FXML
	private void handleQyeryInfo(){
		editStage.close();
	}



}
