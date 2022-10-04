package exceptions;

public class UnpairedBracketsException extends ParsingException {
    public UnpairedBracketsException(String reason, String expression, int pos) {
        super(reason, expression, pos);
    }
}
