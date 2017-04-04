package com.xidian.util;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xidian.model.Customer;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DataValicateUtil {

	public static boolean isInputValid(Customer customer) {
		String errorMessage = "";

		if (customer.getAuid() == null || customer.getAuid().length() == 0) {
			errorMessage += "请输入授权号!\n";
		}
		if (customer.getCustomerName() == null || customer.getCustomerName().length() == 0) {
			errorMessage += "请输入客户姓名!\n";
		}
		// if (ageField.getText() == null || ageField.getText().length() == 0) {
		// errorMessage += "请输入用户年龄!\n";
		// }
		// else
		// {
		// try {
		// Integer.parseInt(ageField.getText());
		// } catch (NumberFormatException e) {
		// errorMessage += "年龄必须输入整数";
		// }
		// }

		// 判断用户输入是否为身份证号
		if (customer.getIdcard() == null || customer.getIdcard().length() == 0) {
			errorMessage += "请输入身份证号!\n";
		} else {
			// 定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
			Pattern idcardPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
			// 通过Pattern获得Matcher
			Matcher idcardMatcher = idcardPattern.matcher(customer.getIdcard());
			if (idcardMatcher.matches()) {
				// 如果是，定义正则表达式提取出身份证中的出生日期
				Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");// 身份证上的前6位以及出生年月日
				// 通过Pattern获得Matcher
				Matcher birthDateMather = birthDatePattern.matcher(customer.getIdcard());
				// 通过Matcher获得用户的出生年月日
				if (birthDateMather.find()) {
					String year = birthDateMather.group(1);
					// 得到用户的生日
					customer.setAge(LocalDate.now().getYear() - Integer.parseInt(year));
					;
				}
			} else {
				errorMessage += "输入身份证号有误！\n";
			}

		}

		if (customer.getAddress() == null || customer.getAddress().length() == 0) {
			errorMessage += "请输入联系地址!\n";
		}

		if (customer.getPhone() == null ||customer.getPhone().length() == 0) {
			errorMessage += "请输入电话号码!\n";
		} else {
			String fixedRegExp = "(\\d{3,4}\\-)\\d{8}";
			String mobileRegExp = "((13|14|15|17|18))\\d{9}";
			if (!(customer.getPhone().matches(fixedRegExp) || customer.getPhone().matches(mobileRegExp))) {
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
