package bstorm;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.mysql.jdbc.log.Log4JLogger;

import bstorm.entity.Task;
import bstorm.entity.User;

public class Main {
	public final static String JPA_UNIT = "BrainstormingJPA";
	private static Map getPersistenceProperties() {
		String propsFname = System.getenv("gstorm.properties");
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(new File(propsFname)));
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return props;
	}

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(JPA_UNIT, getPersistenceProperties());
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		User user = new User();
		user.setName("user");
		user.setPassword("password");
		user.setRole("user");
		user.setActive(true);
		
		em.persist(user);
		
		Task task = new Task();
		task.setShortDescription("ƒорожка дл€ робота");
		task.setDescription("— произвольной белой клетки диаграммы проложите дорожку, идущую через все белые клетки. ƒорожка может пересекать себ€, но не должна дважды проходить через белый квадрат ни горизонтально, ни вертикально. ѕоворачивать на 90 градусов дорожка может, только дойд€ до кра€ пол€ или до черного квадрата.  онец дорожки должен уперетьс€ в край пол€ или черный квадрат. „ерез начальную точку не могут проходить никакие дополнительные (пересекающиес€) линии (в примере начальна€ точка обозначена кружком).");
		task.setState("INIT");
		task.setMaxParticipants(10);
		em.persist(task);
		t.commit();
		
		
		Task ttt = (Task)em.find(Task.class, task.getId());
		System.out.println(ttt.getDescription());
	}
}
