package exceptions;

public class ParsingException extends Exception {
    private final String fullMessage;

    public ParsingException(String reason, String expression, int pos) {
        super(reason);
        StringBuilder sb = new StringBuilder(String.format("%s at index %d:\n", reason, pos + 1));
        int l = Integer.max(0, pos - 5), r = Integer.min(expression.length(), pos + 5);
        sb.append(expression, l, r).append('\n');
        for (int i = l; i < r; ++i) {
            if (i == pos) {
                sb.append('^');
            } else {
                sb.append('~');
            }
        }
        if (pos >= expression.length()) {
            sb.append('^');
        }
        sb.append('\n');
        this.fullMessage = sb.toString();
    }

    public String getFullMessage() {
        return fullMessage;
    }
}
