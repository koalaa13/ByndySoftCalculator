package parser;

import exceptions.*;

import java.util.HashSet;

public class Tokenizer {
    private final String expression;
    private int ind, value, balance;
    private Token curToken;
    private static final HashSet<Token> operations = new HashSet<>();

    public Tokenizer(String newExpression) {
        expression = newExpression;
        ind = balance = 0;
        curToken = Token.BEGIN;
    }

    static {
        operations.add(Token.ADD);
        operations.add(Token.SUB);
        operations.add(Token.MUL);
        operations.add(Token.DIV);
    }

    public String getExpression() {
        return expression;
    }

    int getInd() {
        return ind;
    }

    int getValue() {
        return value;
    }

    Token getCurToken() {
        return curToken;
    }

    Token getNextToken() throws ParsingException {
        nextToken();
        return curToken;
    }

    private void skipWhiteSpaces() {
        while (ind < expression.length() && Character.isWhitespace(expression.charAt(ind))) {
            ++ind;
        }
    }

    private boolean isPartOfNumber(char c) {
        return Character.isDigit(c) || c == '.' || c == 'e';
    }

    private String getNumber() {
        int l = ind;
        while (ind < expression.length() && isPartOfNumber(expression.charAt(ind))) {
            ++ind;
        }
        int r = ind--;
        return expression.substring(l, r);
    }

    private int getInt(String s) throws IncorrectConstException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IncorrectConstException("Non integer const", expression, ind - s.length() + 1);
        }
    }

    private boolean isPartOfIdentifier(char c) {
        return Character.isLetterOrDigit(c);
    }

    private String getIdentifier() {
        int l = ind;
        while (ind < expression.length() && isPartOfIdentifier(expression.charAt(ind))) {
            ++ind;
        }
        int r = ind--;
        return expression.substring(l, r);
    }

    private void checkForOperand() throws MissingOperandException {
        if (curToken == Token.BEGIN || curToken == Token.OPEN_BRACKET || operations.contains(curToken)) {
            throw new MissingOperandException(expression, ind);
        }
    }

    private void checkForOperation() throws MissingOperationException {
        if (curToken == Token.CLOSE_BRACKET || curToken == Token.NUMBER) {
            throw new MissingOperationException(expression, ind);
        }
    }

    private void nextToken() throws ParsingException {
        skipWhiteSpaces();
        if (ind >= expression.length()) {
            checkForOperand();
            curToken = Token.END;
            return;
        }
        char c = expression.charAt(ind);
        switch (c) {
            case '+':
                checkForOperand();
                curToken = Token.ADD;
                break;
            case '*':
                checkForOperand();
                curToken = Token.MUL;
                break;
            case '/':
                checkForOperand();
                curToken = Token.DIV;
                break;
            case '-':
                if (curToken == Token.NUMBER || curToken == Token.CLOSE_BRACKET) {
                    curToken = Token.SUB;
                } else {
                    if (ind + 1 >= expression.length()) {
                        throw new MissingOperandException(expression, ind);
                    } else {
                        if (isPartOfNumber(expression.charAt(ind + 1))) {
                            ind++;
                            value = getInt("-" + getNumber());
                            curToken = Token.NUMBER;
                        } else {
                            curToken = Token.SUB;
                        }
                    }
                }
                break;
            case '(':
                if (curToken == Token.CLOSE_BRACKET || curToken == Token.NUMBER) {
                    throw new MissingOperationException(expression, ind);
                }
                balance++;
                curToken = Token.OPEN_BRACKET;
                break;
            case ')':
                if (operations.contains(curToken) || curToken == Token.OPEN_BRACKET) {
                    throw new MissingOperandException(expression, ind);
                }
                if (balance == 0) {
                    throw new UnpairedBracketsException("There is unpaired close bracket in a expression", expression, ind);
                }
                balance--;
                curToken = Token.CLOSE_BRACKET;
                break;
            default:
                if (Character.isDigit(c)) {
                    checkForOperation();
                    value = getInt(getNumber());
                    curToken = Token.NUMBER;
                } else {
                    String cur = getIdentifier();
                    // handling some functions here for example abs or min
                    throw new UnknownOperationException("Unknown function in expression \"" + cur + "\"", expression, ind - cur.length() + 1);
                }
        }
        ++ind;
    }

}
