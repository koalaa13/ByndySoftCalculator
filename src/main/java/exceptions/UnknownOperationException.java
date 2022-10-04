package exceptions;

public class UnknownOperationException extends ParsingException {
    public UnknownOperationException(String reason, String expression, int pos) {
        super(reason, expression, pos);
    }
}
