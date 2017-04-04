package com.xidian.view;

import com.xidian.MainApp;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * 管理界面控制器
 * @author lfq
 *
 */
public class MainController {

	@FXML
	private AnchorPane anchorPaneContent;

	private MainApp mainApp;

	public MainController() {

	}

	@FXML
	private void initialize()
	{

	}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	/**
	 * 打开新建客户面板
	 */
	@FXML
	private void handleNewCustomer()
	{
		mainApp.showNewCustomer(anchorPaneContent);
	}

	/**
	 * 打开查询客户面板
	 */
	@FXML
	private void handleQueryCustomer()
	{
		mainApp.showQueryCustomer(anchorPaneContent);
	}

	/**
	 * 打开查询变更客户信息面板
	 */
	@FXML
	private void handleQueryUpdateInfo()
	{
		mainApp.showQueryUpdateInfo(anchorPaneContent);
	}
}
