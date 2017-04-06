package com.xidian;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import com.xidian.model.Customer;
import com.xidian.model.UpdateInfo;
import com.xidian.model.address.Address;
import com.xidian.model.order.Order;
import com.xidian.util.MybatisUtils;
import com.xidian.view.LoginController;
import com.xidian.view.MainController;
import com.xidian.view.NewCustomerController;
import com.xidian.view.QueryCustomerController;
import com.xidian.view.QueryUpdateInfoController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.xidian.view.address.NewAddressController;
import com.xidian.view.address.QueryAddressController;
import com.xidian.view.order.NewOrderController;
import com.xidian.view.order.QueryOrderController;

/**程序入口类，管理界面，控制器
 * @author lfq
 *
 */
public class MainApp extends Application {

	private Stage primaryStage;
	private Stage mainStage;
	private AnchorPane loginView;

	public MainApp() {
		super();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public SqlSession getSqlSession(boolean flag)
	{
		return MybatisUtils.getSqlSession(flag);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("登录");
		 // Set the application icon.
	    this.primaryStage.getIcons().add(new Image("file:resources/images/person.png"));

	    showLoginView();
	}



	/**
	 * 显示登录界面
	 */
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

	/**
	 * 显示主界面
	 */
	public void showMainWindow() {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Main.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			primaryStage.close();//关闭登录界面
			mainStage = new Stage();
			mainStage.setTitle("管理界面");
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.getIcons().add(new Image("file:resources/images/person.png"));

			Scene scene = new Scene(page);
			mainStage.setScene(scene);

			MainController mainController = loader.getController();
			mainController.setMainApp(this);

			mainStage.setMaximized(true);
			mainStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示新建客户信息界面
	 * @param anchorPaneContent
	 */
	public void showNewCustomer(AnchorPane anchorPaneContent) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/NewCustomer.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			NewCustomerController newCustomerController = loader.getController();
			newCustomerController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示查询客户信息界面
	 * @param anchorPaneContent
	 */
	public void showQueryCustomer(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/QueryCustomer.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加查询内容
			anchorPaneContent.getChildren().add(page);

			QueryCustomerController queryCustomerController = loader.getController();
			queryCustomerController.setMainApp(this);

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示查询客户信息界面
	 * @param anchorPaneContent
	 */
	public void showQueryUpdateInfo(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/QueryUpdateInfo.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加查询内容
			anchorPaneContent.getChildren().add(page);

			QueryUpdateInfoController queryUpdateInfoController = loader.getController();
			queryUpdateInfoController.setMainApp(this);

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示修改客户信息界面
	 * @param customer
	 */
	public void showEditCustomer(Customer customer) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EditCustomer.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("修改客户信息");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			QueryCustomerController queryCustomerController = loader.getController();
			queryCustomerController.setMainApp(this);
			queryCustomerController.setCustomer(customer);
			queryCustomerController.setEditStage(editStage);

			editStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showAddUpdateInfo(UpdateInfo selectedUpdateInfo) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AddUpdateInfo.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage addStage = new Stage();
			addStage.setTitle("变更客户信息");
			addStage.initModality(Modality.WINDOW_MODAL);
			addStage.initOwner(mainStage);
			addStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			addStage.setScene(scene);

			QueryUpdateInfoController queryUpdateInfoController = loader.getController();
			queryUpdateInfoController.setMainApp(this);
			queryUpdateInfoController.setUpdateInfo(selectedUpdateInfo);
			queryUpdateInfoController.setEditStage(addStage);

			addStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showNewAddress(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/address/NewAddress.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			NewAddressController newAddressController = loader.getController();
			newAddressController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showQueryAddress(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/address/QueryAddress.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加查询内容
			anchorPaneContent.getChildren().add(page);

			QueryAddressController queryAddressController = loader.getController();
			queryAddressController.setMainApp(this);

		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void showEditAddress(Address selectedAddress)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/address/EditAddress.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("收件地址信息");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			QueryAddressController queryAddressController = loader.getController();
			queryAddressController.setMainApp(this);
			queryAddressController.setAddress(selectedAddress);
			queryAddressController.setEditStage(editStage);

			editStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示新建订单界面
	 * @param anchorPaneContent
	 */
	public void showNewOrder(AnchorPane anchorPaneContent) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/order/NewOrder.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			NewOrderController newOrderController = loader.getController();
			newOrderController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示查询订单界面
	 * @param anchorPaneContent
	 */
	public void showQueryOrder(AnchorPane anchorPaneContent) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/order/QueryOrder.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			QueryOrderController queryOrderController = loader.getController();
			queryOrderController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示单个详细订单信息界面
	 * @param order
	 */
	public void showOrderInfo(Order order) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/order/QueryOrderInfo.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("订单信息");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			QueryOrderController queryOrderController = loader.getController();
			queryOrderController.setMainApp(this);
			queryOrderController.setOrder(order);
			queryOrderController.setEditStage(editStage);

			editStage.showAndWait();

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}


}
