package com.xidian.view;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.ManagerUser;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

/**管理用户登录控制器
 * @author lfq
 *
 */
public class LoginController {
	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Label messageLabel;

	private MainApp mainApp;
	private ManagerUser managerUser;

	public LoginController() {

	}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	@FXML
	private void initialize()
	{

	}

	/**
	 * 处理登录
	 */
	@FXML
	private void handleLogin()
	{
		String username = usernameField.getText();
		String password = passwordField.getText();
		SqlSession sqlSession = mainApp.getSqlSession(true);
		try {
			managerUser = sqlSession.selectOne("com.xidian.ManagerUserXml.getUser", username);
			if(managerUser != null)
			{
				if(password.equals(managerUser.getPassword()))
				{
					mainApp.showMainWindow();
				}
				else
				{
					messageLabel.setText("用户名或密码错误！");
				}
			}
			else
			{
				messageLabel.setText("用户名或密码错误！");
			}
		}
		finally{
			sqlSession.close();
		}

	}


}
