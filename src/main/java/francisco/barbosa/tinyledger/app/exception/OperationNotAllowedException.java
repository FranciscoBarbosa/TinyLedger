package francisco.barbosa.tinyledger.app.exception;

public class OperationNotAllowedException extends RuntimeException {

	public OperationNotAllowedException(String message) {
		super(message);
	}
}
