package francisco.barbosa.tinyLedger.app.exception;

public class OperationNotAllowed extends RuntimeException {

    public OperationNotAllowed(String message) {
        super(message);
    }
}
