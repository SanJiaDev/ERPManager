package com.xidian.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xidian.model.order.Order;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class OrderValicateUtil {

	public static boolean isInputValid(Order order,String num) {
		String errorMessage = "";

		if (order.getAuId() == null || order.getAuId().length() == 0) {
			errorMessage += "请输入授权号!\n";
		}
		if (order.getOrderId() == null || order.getOrderId().length() == 0) {
			errorMessage += "请输入订单号!\n";
		}
		if (order.getWayBillNumber() == null || order.getWayBillNumber().length() == 0) {
			errorMessage += "请输入运单号!\n";
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

		// 判断用户输入是否为发货时间
		if (order.getDeliveryTime() == null ) {
			errorMessage += "请输入发货时间!\n";
		}

		if (order.getDeliveryName() == null || order.getDeliveryName().length() == 0) {
			errorMessage += "请输入发货人姓名!\n";
		}

	    //判断用户输入的货物数量是否为整数,并赋值
		if(isInt(num)){
			order.setProductNum(Integer.parseInt(num));
		}else{
			errorMessage += "请正确输入发货数量!\n";
		}

		if (order.getReceiverName() == null || order.getReceiverName().length() == 0) {
			errorMessage += "请输入收货人姓名!\n";
		}

		if (order.getReceiverPhone() == null ||order.getReceiverPhone().length() == 0) {
			errorMessage += "请输入电话号码!\n";
		} else {
			String fixedRegExp = "(\\d{3,4}\\-)\\d{8}";
			String mobileRegExp = "((13|14|15|17|18))\\d{9}";
			if (!(order.getReceiverPhone().matches(fixedRegExp) || order.getReceiverPhone().matches(mobileRegExp))) {
				errorMessage += "电话号码输入有误！\n";
			}

		}

		if (order.getReceiverAddress() == null || order.getReceiverAddress().length() == 0) {
			errorMessage += "请输入收货人地址!\n";
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


    public static boolean isInt (String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }
}