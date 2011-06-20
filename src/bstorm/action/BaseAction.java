package bstorm.action;

public abstract class BaseAction implements Action {
	public String getName() {
		String name = ActionUtils.getActionName(this);
		return (name != null) ? name : "";
	}
}
