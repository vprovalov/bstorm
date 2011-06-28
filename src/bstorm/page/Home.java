package bstorm.page;

public class Home extends org.apache.click.Page {
	public Home(){
	}
	
	@Override
	public String getTemplate() {
		return "/WEB-INF/html/home.html";	
	}
}