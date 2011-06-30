package bstorm.page;

import org.apache.click.control.Form;
import org.apache.click.control.PasswordField;
import org.apache.click.control.TextField;

public class Register extends BasePage {
	public Form registerForm = new Form();
	
	public Register(){
		registerForm.add(new TextField("username", true));
		registerForm.add(new TextField("firstname", true));
		registerForm.add(new TextField("lastname", true));
		registerForm.add(new PasswordField("password", true));
	}
}