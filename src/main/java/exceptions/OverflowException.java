package exceptions;

public class OverflowException extends EvaluatingException {
    public OverflowException(String reason) {
        super(reason);
    }
}
