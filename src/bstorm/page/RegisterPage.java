package bstorm.page;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.PasswordField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;

import bstorm.dao.UserDAO;
import bstorm.entity.User;
import bstorm.utils.StringUtils;

public class RegisterPage extends BasePage {
	public Form registerForm = new Form();
	
	public RegisterPage(){
		FieldSet loginFieldSet = new FieldSet("������� ������");
		registerForm.add(loginFieldSet);
		
		TextField usernameField = new TextField("username", "��� ������������", true) {
			@Override
			public void validate() {
				if (!StringUtils.validateUsername(value)) {
					setError(getLabel() + " ������ ��������� ������ ���������� ����� � �����");
					return;
				}
				super.validate();
			}
		};
		usernameField.setMinLength(4);
		usernameField.setMaxLength(25);
		usernameField.setFocus(true);
		loginFieldSet.add(usernameField);
		
		final PasswordField passwordField = new PasswordField("password", "������", true);
		passwordField.setMinLength(6);
		passwordField.setMaxLength(20);
		loginFieldSet.add(passwordField);
		
		PasswordField rePasswordField = new PasswordField("repassword", "��������� ������", true) {
			@Override
			public void validate() {
				if (!value.equals(passwordField.getValue())) {
					setError("��������� ������ �� ���������");
					return;
				}
				super.validate();
			}
		};
		rePasswordField.setMinLength(6);
		rePasswordField.setMaxLength(20);
		loginFieldSet.add(rePasswordField);
		
		FieldSet personalFieldSet = new FieldSet("������������ ������");
		registerForm.add(personalFieldSet);
		
		TextField firstnameField = new TextField("firstname", "���", true);
		firstnameField.setMinLength(1);
		firstnameField.setMaxLength(30);
		personalFieldSet.add(firstnameField);
		
		TextField lastnameField = new TextField("lastname", "�������", true);
		lastnameField.setMinLength(1);
		lastnameField.setMaxLength(40);
		personalFieldSet.add(lastnameField);		
		
		registerForm.add(new Submit("register", "�����������", this, "onSubmit"));
	}
	
	public boolean onSubmit() {
		if (registerForm.isValid()) {
			EntityManager em = getEntityManager();
			if (em != null) {
				try {
					UserDAO userDao = new UserDAO(em);
					final String newUsername = registerForm.getFieldValue("username"); 
					if (userDao.findByName(newUsername) == null) {
						User user = new User();
						
						user.setActive(false);
						user.setFirstname(registerForm.getFieldValue("firstname"));
						user.setLastname(registerForm.getFieldValue("lastname"));
						user.setName(newUsername);
						user.setPassword(registerForm.getFieldValue("password"));
						user.setRole("user");
						
						userDao.update(user);
						
						setRedirect(HomePage.class);
						return true;
					} else {
						registerForm.setError("������������ � ����� ������ ��� ����������!");
					}
				} catch(PersistenceException ex) {
					registerForm.setError("������: " + ex.getMessage());
				}
			}
		}
		return false;
	}
}