package com.xidian.view;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.Customer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class NewCustomerController {

	@FXML
	private TextField auidField;

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private TextField customernameField;

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

	private MainApp mainApp;
	private Customer customer;

	public NewCustomerController() {

	}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	@FXML
	private void initialize()
	{
		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().addAll("县代", "省代");
		rankBox.getSelectionModel().select("县代");

		sexBox.getItems().removeAll(sexBox.getItems());
		sexBox.getItems().addAll("男", "女");
		sexBox.getSelectionModel().select("男");

		areaField.getItems().removeAll(areaField.getItems());
		areaField.getItems().addAll("西安", "长安", "宝鸡", "凤翔", "歧山");
		areaField.getSelectionModel().select("西安");
	}

	/**
	 * 新建客户
	 */
	@FXML
	private void handleSaveCustomer()
	{

		customer = new Customer();
		//首先验证输入数据
		if(isInputValid(customer))
		{
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

			SqlSession sqlSession = mainApp.getSqlSession();
			int result = sqlSession.insert("com.xidian.CustomerXml.addCustomer", customer);
			sqlSession.close();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setResizable(false);
			alert.setTitle("保存结果");
			alert.setHeaderText("");
			if(result == 1)
			{
				alert.setContentText("保存成功！");
				auidField.setText("");
				rankBox.getSelectionModel().select("县代");
				customernameField.setText("");
				sexBox.getSelectionModel().select("男");
				idcardField.setText("");
				areaField.getSelectionModel().select("西安");
				addressField.setText("");
				phoneField.setText("");
				qqField.setText("");
				weixinField.setText("");
			}
			else
			{
				alert.setContentText("保存失败！");
			}
			alert.show();
			if(alert.isShowing())
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				alert.close();
			}
		}
	}

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid(Customer customer) {
        String errorMessage = "";

        if (auidField.getText() == null || auidField.getText().length() == 0) {
            errorMessage += "请输入授权号!\n";
        }
        if (customernameField.getText() == null || customernameField.getText().length() == 0) {
            errorMessage += "请输入客户姓名!\n";
        }
//        if (ageField.getText() == null || ageField.getText().length() == 0) {
//            errorMessage += "请输入用户年龄!\n";
//        }
//        else
//        {
//        	try {
//        		Integer.parseInt(ageField.getText());
//			} catch (NumberFormatException e) {
//				errorMessage += "年龄必须输入整数";
//			}
//        }


        //判断用户输入是否为身份证号
        if (idcardField.getText() == null || idcardField.getText().length() == 0) {
            errorMessage += "请输入省份证号!\n";
        }
        else {
        	//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
            Pattern idcardPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
            //通过Pattern获得Matcher
            Matcher idcardMatcher = idcardPattern.matcher(idcardField.getText());
            if(idcardMatcher.matches())
            {
            	//如果是，定义正则表达式提取出身份证中的出生日期
                Pattern birthDatePattern= Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");//身份证上的前6位以及出生年月日
                //通过Pattern获得Matcher
                Matcher birthDateMather= birthDatePattern.matcher(idcardField.getText());
                //通过Matcher获得用户的出生年月日
                if(birthDateMather.find()){
                    String year = birthDateMather.group(1);
                    //得到用户的生日
                    customer.setAge(LocalDate.now().getYear() - Integer.parseInt(year));;
                }
            }
            else
            {
            	errorMessage += "输入身份证号有误！\n";
            }

        }

        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "请输入联系地址!\n";
        }

        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "请输入电话号码!\n";
        }
        else
        {
        	String fixedRegExp = "(\\d{3,4}\\-)\\d{8}";
        	String mobileRegExp = "((13|14|15|17|18))\\d{9}";
            if(!(phoneField.getText().matches(fixedRegExp) || phoneField.getText().matches(mobileRegExp)))
            {
            	errorMessage += "电话号码输入有误！\n";
            }

        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("输入数据验证");
        	alert.setHeaderText("请在输入框中输入正确格式数据");
        	alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }
}
