package parser;

import exceptions.ParsingException;
import exceptions.UnpairedBracketsException;
import expressions.*;

public class ExpressionParser implements Parser {
    private Tokenizer tokenizer;

    private Expression unaryOperations() throws ParsingException {
        Expression res;
        switch (tokenizer.getNextToken()) {
            case NUMBER:
                res = new Const(tokenizer.getValue());
                tokenizer.getNextToken();
                break;
            case SUB:
                res = new Negate(unaryOperations());
                break;
            case OPEN_BRACKET:
                res = addAndSub();
                if (tokenizer.getCurToken() != Token.CLOSE_BRACKET) {
                    throw new UnpairedBracketsException("There is unpaired open bracket in a expression", tokenizer.getExpression(), tokenizer.getInd());
                }
                tokenizer.getNextToken();
                break;
            default:
                throw new ParsingException("Incorrect expression", tokenizer.getExpression(), tokenizer.getInd());
        }
        return res;
    }

    private Expression mulAndDiv() throws ParsingException {
        Expression res = unaryOperations();
        for (; ; ) {
            switch (tokenizer.getCurToken()) {
                case MUL:
                    res = new Multiply(res, unaryOperations());
                    break;
                case DIV:
                    res = new Divide(res, unaryOperations());
                    break;
                default:
                    return res;
            }
        }
    }

    private Expression addAndSub() throws ParsingException {
        Expression res = mulAndDiv();
        for (; ; ) {
            switch (tokenizer.getCurToken()) {
                case ADD:
                    res = new Add(res, mulAndDiv());
                    break;
                case SUB:
                    res = new Subtract(res, mulAndDiv());
                    break;
                default:
                    return res;
            }
        }
    }

    @Override
    public Expression parse(String expression) throws ParsingException {
        tokenizer = new Tokenizer(expression);
        return addAndSub();
    }
}
