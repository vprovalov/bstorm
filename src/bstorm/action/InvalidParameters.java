package bstorm.action;

public class InvalidParameters extends ActionException {
	public InvalidParameters() {
		super();
	}
	public InvalidParameters(String message) {
		super(message);
	}
}
