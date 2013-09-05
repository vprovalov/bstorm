package bstorm.page;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.click.element.Element;
import org.apache.click.element.JsImport;
import org.apache.click.util.Bindable;

import bstorm.dao.SolutionDAO;
import bstorm.dao.TaskDAO;
import bstorm.entity.Solution;
import bstorm.entity.Task;

public class SolutionPage extends BasePage {
	@Bindable
	private Long id;
	
	private SolutionDAO solutionDao = null;
	private TaskDAO taskDao = null;
	
	public Solution solution = null;

	@Override
	public List<Element> getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();
			
			JsImport jsimport = new JsImport("/js/solution-init.js");
			headElements.add(jsimport);
		}
		return headElements;
	}
	
	@Override
	public void onInit() {
		super.onInit();

		EntityManager em = getEntityManager();
		if (em != null) {
			taskDao = new TaskDAO(em);
			solutionDao = new SolutionDAO(em);
			
			if (id != null) {
				solution = solutionDao.findById(id);
				if (solution == null) setRedirect(TaskPage.class);
			}
		}
	}	
	
}
