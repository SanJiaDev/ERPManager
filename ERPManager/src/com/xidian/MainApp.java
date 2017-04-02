package com.xidian;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import com.xidian.util.MybatisUtils;
import com.xidian.view.LoginController;
import com.xidian.view.MainController;
import com.xidian.view.NewCustomerController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane loginView;

	private  SqlSession sqlSession = MybatisUtils.getSqlSession();

	public MainApp() {
		super();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public SqlSession getSqlSession()
	{
		return sqlSession;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("登录");
		 // Set the application icon.
	    this.primaryStage.getIcons().add(new Image("file:resources/images/person.png"));

	    showLoginView();
	}



	public void showLoginView() {
	    try {
	        // Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class
	                .getResource("view/Login.fxml"));
	        loginView = (AnchorPane) loader.load();

	        // Show the scene containing the root layout.
	        Scene scene = new Scene(loginView);
	        primaryStage.setScene(scene);

	        // Give the controller access to the main app.
	        LoginController loginController = loader.getController();
	        loginController.setMainApp(this);

	        primaryStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}

    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public void showMainWindow() {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Main.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			primaryStage.close();//关闭登录界面
			Stage mainStage = new Stage();
			mainStage.setTitle("管理界面");
			mainStage.initModality(Modality.WINDOW_MODAL);
//			mainStage.setFullScreen(true);
			mainStage.getIcons().add(new Image("file:resources/images/person.png"));

			Scene scene = new Scene(page);
			mainStage.setScene(scene);

			MainController mainController = loader.getController();
			mainController.setMainApp(this);

			mainStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showNewCustomer(AnchorPane anchorPaneContent) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/NewCustomer.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			anchorPaneContent.getChildren().add(page);

			NewCustomerController newCustomerController = loader.getController();
			newCustomerController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
