package francisco.barbosa.tinyLedger.app.exception;

public class OperationNotAllowedException extends RuntimeException {

	public OperationNotAllowedException(String message) {
		super(message);
	}
}
