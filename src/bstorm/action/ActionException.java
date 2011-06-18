package bstorm.action;

public class ActionException extends Exception {
	public ActionException() {
		super();
	}	
	public ActionException(final String message) {
		super(message);
	}
	public ActionException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
