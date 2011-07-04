package bstorm.page;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.click.control.Form;
import org.apache.click.control.PasswordField;
import org.apache.click.control.TextField;

import bstorm.dao.UserDAO;
import bstorm.entity.User;


public class Login extends BasePage {
	public Form form = new Form("form");
	public String errorMessage;
	
	public Login() {
		form.add(new TextField("username", true));
		form.add(new PasswordField("password", true));
		form.setListener(this, "onSubmit");
	}
	
    private void authorizeUser(User user) {
    	getContext().getSession().setAttribute("user", user);
    }
	
	public boolean onSubmit() {
		final String username = form.getFieldValue("username");
		final String password = form.getFieldValue("password");
		if (username.isEmpty() || password.isEmpty()) {
			errorMessage = "Учетная запись и/или пароль не указаны.";
		} else {
			EntityManager em = getEntityManager();
			if (em != null) {
				UserDAO userDao = new UserDAO(em);
				User user = userDao.findByName(username);
				if (user != null && user.getPassword().equals(password)) {
					if (user.isActive()) {
						user.setLastLogin(new Date());
						userDao.update(user);
						authorizeUser(user);
						if (user.getRole().equals("admin")) {
							setRedirect(Admin.class);
						} else {
							setRedirect(Home.class);
						}
					} else {
						errorMessage = "Пользователь не активирован! Обратитесь к администратору!";
					}
				} else {
					errorMessage = "Неправильная учетная запись или пароль!";
				}
			} else {
				errorMessage = "Ошибка подключения к базе данных!";
			}
		}
		return true;
	}
}