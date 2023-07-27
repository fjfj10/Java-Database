package utils;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CustomSwingTextUtil {

	//	빈값이면(str이 null이나 blank = true) 오류메세지를 띄우고 if(isTextEmpty(productColorName)= true) return된다
	//	내용이 있으면 false로 if(isTextEmpty(productColorName) = false) return이 되지않고 if문을 빠져나간다
	public static boolean isTextEmpty(Component targetComponent, String str) {
		if(str != null) {
			if(!str.isBlank()) {
				return false;
			}
		}
		JOptionPane.showMessageDialog(targetComponent, "내용을 입력하세요.", "입력오류", JOptionPane.ERROR_MESSAGE);
		return true;
	}
	
	public static void clearTextField(JTextField textField) {
		textField.setText("");
	}
}
