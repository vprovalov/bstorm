package bstorm.page;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.click.element.Element;
import org.apache.click.element.JsImport;

import bstorm.entity.User;

public class AdminPage extends AdminOnlyPage {
	@Override
	public List<Element> getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();

			JsImport jsBaseInit = new JsImport("/js/admin-init.js");
			headElements.add(jsBaseInit);
		}
		return headElements;
	}
}