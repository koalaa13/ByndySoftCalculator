package exceptions;

public class IncorrectConstException extends ParsingException {
    public IncorrectConstException(String reason, String expression, int pos) {
        super(reason, expression, pos);
    }
}
