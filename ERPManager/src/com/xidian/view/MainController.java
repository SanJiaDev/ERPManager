package com.xidian.view;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

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

	@FXML
	private void handleNewCustomer()
	{
		mainApp.showNewCustomer(anchorPaneContent);
	}
}
