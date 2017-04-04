package com.xidian.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.Customer;
import com.xidian.model.UpdateInfo;

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

/**查询，修改控制器
 * @author lfq
 *
 */
public class QueryUpdateInfoController {

	@FXML
	private TextField auidField;

	@FXML
	private TextField customernameField;

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private TableView<UpdateInfo> updateInfoTable;

	@FXML
	private TableColumn<UpdateInfo, Integer> idColumn;

	@FXML
	private TableColumn<UpdateInfo, String> auidColumn;

	@FXML
	private TableColumn<UpdateInfo, String> rankColumn;

	@FXML
	private TableColumn<UpdateInfo, String> customernameColumn;

	@FXML
	private TableColumn<UpdateInfo, String> stateColumn;

	@FXML
	private TableColumn<UpdateInfo, LocalDate> updateTimeColumn;

	@FXML
	private TableColumn<UpdateInfo, String> updateReasonColumn;

	//变更客户信息
	@FXML
	private CheckBox stateBox;

	@FXML
	private TextField updateTimeField;

	@FXML
	private TextArea updateReasonField;

	@FXML
	private AnchorPane editAnchorPane;

	private MainApp mainApp;

	private UpdateInfo updateInfo;

	private ObservableList<UpdateInfo> updateInfoData = FXCollections.observableArrayList();

	private Stage addStage;

	public QueryUpdateInfoController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage addStage) {
		this.addStage = addStage;
	}

	public void setUpdateInfo(UpdateInfo updateInfo)
	{
		//设置客户数据到修改表单
		this.updateInfo = updateInfo;

		auidField.setText(updateInfo.getAuid());

		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().addAll("县代", "省代");
		rankBox.getSelectionModel().select(updateInfo.getRank());

		customernameField.setText(updateInfo.getCustomerName());

		updateReasonField.setText(updateInfo.getUpdateReason());

	}

	@FXML
	private void initialize() {

	}

	/**定义列的点击事件类
	 * @author lfq
	 *
	 */
	private class UpdateInfoStringCellFactory implements Callback<TableColumn<UpdateInfo, String>, TableCell<UpdateInfo, String>> {

	    @Override
	    public TableCell<UpdateInfo, String> call(TableColumn<UpdateInfo, String> param) {
	        TextFieldTableCell<UpdateInfo, String> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	UpdateInfo selectedUpdateInfo = updateInfoTable.getSelectionModel().getSelectedItem();
	            	if(selectedUpdateInfo != null)
	            	{
	            		mainApp.showAddUpdateInfo(selectedUpdateInfo);

	            	}
	            }
	        });
	        return cell;
	    }

	}

	private class UpdateInfoLocalDateCellFactory implements Callback<TableColumn<UpdateInfo, LocalDate>, TableCell<UpdateInfo, LocalDate>> {

		@Override
		public TableCell<UpdateInfo, LocalDate> call(TableColumn<UpdateInfo, LocalDate> param) {
			TextFieldTableCell<UpdateInfo, LocalDate> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	UpdateInfo selectedUpdateInfo = updateInfoTable.getSelectionModel().getSelectedItem();
	            	if(selectedUpdateInfo != null)
	            	{
	            		mainApp.showAddUpdateInfo(selectedUpdateInfo);
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
	private void handleQueryUpdateInfo() {
		// 清空表中的数据
		updateInfoTable.getItems().clear();

		updateInfo = new UpdateInfo();
		String auid = auidField.getText();
		String customerName = customernameField.getText();

		SqlSession sqlSession = mainApp.getSqlSession(true);

		// 通过授权号查询客户信息
		if (!"".equals(auid.trim())) {
			List<UpdateInfo> updateInfoByAuid = sqlSession.selectList("com.xidian.UpdateInfoXml.getUpdateInfoByAuid", auid);
			updateInfoData.addAll(updateInfoByAuid);
		} else {
			// 如果没有查询信息，则全部查询
			if (("".equals(customerName.trim()))) {
				List<UpdateInfo> updateInfos = sqlSession.selectList("com.xidian.UpdateInfoXml.getAllUpdateInfo");
				updateInfoData.addAll(updateInfos);
			}

			// 通过客户姓名查询客户信息
			if ((!"".equals(customerName.trim()))) {
				List<UpdateInfo> updateInfosByName = sqlSession.selectList("com.xidian.UpdateInfoXml.getUpdateInfoByName",
						customerName);
				updateInfoData.addAll(updateInfosByName);
			}

		}

		//表中放数据
		updateInfoTable.setItems(updateInfoData);

		//设置显示过滤列的菜单按钮
		updateInfoTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		auidColumn.setStyle( "-fx-alignment: CENTER;");
		rankColumn.setStyle( "-fx-alignment: CENTER;");
		customernameColumn.setStyle( "-fx-alignment: CENTER;");
		stateColumn.setStyle("-fx-alignment: CENTER;");
		updateTimeColumn.setStyle( "-fx-alignment: CENTER;");
		updateReasonColumn.setStyle( "-fx-alignment: CENTER;");

		// 将数据放入表中的列
		auidColumn.setCellValueFactory(cellData -> cellData.getValue().auidProperty());
		rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
		customernameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
		stateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
		updateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().updateTimeProperty());
		updateReasonColumn.setCellValueFactory(cellData -> cellData.getValue().updateReasonProperty());

		//设置每一列的双击事件
		auidColumn.setCellFactory(new UpdateInfoStringCellFactory());
		rankColumn.setCellFactory(new UpdateInfoStringCellFactory());
		customernameColumn.setCellFactory(new UpdateInfoStringCellFactory());
		stateColumn.setCellFactory(new UpdateInfoStringCellFactory());
		updateTimeColumn.setCellFactory(new UpdateInfoLocalDateCellFactory());
		updateReasonColumn.setCellFactory(new UpdateInfoStringCellFactory());

	}

	/**
	 * 修改客户信息
	 */
	@FXML
	private void handleAddUpdateInfo()
	{

		updateInfo.setRank(rankBox.getSelectionModel().getSelectedItem());
		String state = "注册";
		if(stateBox.isSelected())
		{
			//选择撤销，给一个确认框
			Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
			alertConfirm.setTitle("");
			alertConfirm.setHeaderText("");
			alertConfirm.setContentText("确认撤销吗？");
			Optional<ButtonType> result = alertConfirm.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.CANCEL) {
			     return;
			 }
			state = "撤销";
		}
		updateInfo.setState(state);
		updateInfo.setUpdateReason(updateReasonField.getText());

		Customer customer = new Customer();
		customer.setAuid(updateInfo.getAuid());
		customer.setState(state);

		SqlSession sqlSession = mainApp.getSqlSession(false);
		int addUpdateInfoResult = 0;
		int updateCustomerOfStateResult = 0;
		try {
			addUpdateInfoResult = sqlSession.insert("com.xidian.UpdateInfoXml.addUpdateInfo", updateInfo);
			updateCustomerOfStateResult = sqlSession.update("com.xidian.CustomerXml.updateCustomerOfState", customer);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setResizable(false);
		alert.setTitle("变更结果");
		alert.setHeaderText("");
		if (addUpdateInfoResult == 1 && updateCustomerOfStateResult == 1)
		{
			alert.setContentText("变更成功！");
		} else {
			alert.setContentText("变更失败！");
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
		addStage.close();
	}

}
