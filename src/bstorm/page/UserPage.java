package bstorm.page;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Checkbox;
import org.apache.click.control.Column;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Option;
import org.apache.click.control.PageLink;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.extras.control.LinkDecorator;
import org.apache.click.util.Bindable;

import bstorm.dao.UserDAO;
import bstorm.entity.User;

public class UserPage extends AdminOnlyPage {
	@Bindable
	private Long id;
	private UserDAO userDao = null;
	
	private Table usersTable = new Table("usersTable");
	private PageLink editLink = new PageLink("Edit", UserPage.class);
	private ActionLink deleteLink = new ActionLink("Delete", this, "onDeleteClick");
	
	private Form editUserForm = new Form("editUserForm");
	
	public User theUser = null;
	public List<User> users = null;
	
	private TextField usernameField;
	private Checkbox resetPasswordCheck;
	private Checkbox isActiveCheck;
	private Select roleSelect;
	private TextField firstnameField;
	private TextField lastnameField;
	
	public UserPage() {
		addControl(usersTable);
		
		usersTable.setPageSize(10);
		usersTable.setShowBanner(true);
		usersTable.setSortable(true);
		
		usersTable.addColumn(new Column("id"));
		usersTable.addColumn(new Column("name", "�����"));
		usersTable.addColumn(new Column("firstname", "���"));
		usersTable.addColumn(new Column("lastname", "�������"));
		usersTable.addColumn(new Column("role", "������"));
		
		editLink.setImageSrc("/css/images/user_edit.png");
		editLink.setTitle("�������������");
		
		deleteLink.setImageSrc("/css/images/user_delete.png");
		deleteLink.setTitle("�������");
		deleteLink.setAttribute("onclick", "return window.confirm('�� �������, ��� ������ ������� ����� ������������?')");
		
		Column column = new Column("Action", "��������");
		AbstractLink[] links = new AbstractLink[] { editLink, deleteLink };
		column.setDecorator(new LinkDecorator(usersTable, links, "id"));
		column.setSortable(false);
		usersTable.addColumn(column);
		
		usersTable.setDataProvider(new DataProvider<User>() {
			@Override
			public Iterable<User> getData() {
				return users;
			}
		});
		
		usersTable.getControlLink().setActionListener(new ActionListener() {
			@Override
			public boolean onAction(Control source) {
				usersTable.saveState(getContext());
				return true;
			}
		});
		
		usersTable.restoreState(getContext());

		addControl(editUserForm);
		
		FieldSet loginFieldSet = new FieldSet("������� ������");
		editUserForm.add(loginFieldSet);
		
		usernameField = new TextField("name", "��� ������������");
		usernameField.setReadonly(true);
		loginFieldSet.add(usernameField);
		
		resetPasswordCheck = new Checkbox("resetPassword", "�������� ������");
		loginFieldSet.add(resetPasswordCheck);
		isActiveCheck = new Checkbox("active", "��������");
		loginFieldSet.add(isActiveCheck);
		
		roleSelect = new Select("role", "������");
		roleSelect.add(new Option("user", "������������"));
		roleSelect.add(new Option("moderator", "���������"));
		roleSelect.add(new Option("admin", "�������������"));
		loginFieldSet.add(roleSelect);
		
		FieldSet personalFieldSet = new FieldSet("������������ ������");
		editUserForm.add(personalFieldSet);
		
		firstnameField = new TextField("firstname", "���", true);
		firstnameField.setMinLength(1);
		firstnameField.setMaxLength(30);
		personalFieldSet.add(firstnameField);
		
		lastnameField = new TextField("lastname", "�������", true);
		lastnameField.setMinLength(1);
		lastnameField.setMaxLength(40);
		personalFieldSet.add(lastnameField);		
		
		Submit save = new Submit("save", "���������", this, "onSaveChanges");
		editUserForm.add(save);
		
		editUserForm.add(new HiddenField("id", Long.class));
	}
	
	@Override
	public void onInit() {
		super.onInit();

		EntityManager em = getEntityManager();
		if (em != null) {
			userDao = new UserDAO(em);
			if (id == null) {
				users = userDao.getAllUsers();
			} else {
				users = new ArrayList<User>();
				theUser = userDao.findById(id);
				users.add(theUser);
				
/*				usernameField.setValue(theUser.getName());
				resetPasswordCheck.setValue("" + false);
				isActiveCheck.setValue("" + theUser.isActive());
				roleSelect.setValue(theUser.getRole());
				firstnameField.setValue(theUser.getFirstname());
				lastnameField.setValue(theUser.getLastname());*/
				editUserForm.copyFrom(theUser);
				resetPasswordCheck.setValue("" + false);
			}
		}		
	}
	
	public boolean onDeleteClick() {
		Long id = deleteLink.getValueLong();
		if (user.getId().equals(id)) {
			userDao.deleteUser(id);
			return true;
		}
		return false;
	}
	
	public boolean onSaveChanges() {
		try {
			User usr = userDao.findById(id);
			
			editUserForm.copyTo(usr);
			if (resetPasswordCheck.isChecked()) {
				usr.setPassword(usr.getName());
			}
//			usr.setActive(isActiveCheck.isChecked());
//			usr.setRole(roleSelect.getValue());
//			usr.setFirstname(firstnameField.getValue());
//			usr.setLastname(lastnameField.getValue());
			
			
			userDao.update(usr);			
			setRedirect(UserPage.class);
		} catch (PersistenceException ex) {
			editUserForm.setError("������: " + ex.getMessage());
			return false;
		}
		
		return true;
	}
}