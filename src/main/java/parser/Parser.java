package parser;

import exceptions.ParsingException;
import expressions.Expression;

public interface Parser {
    Expression parse(String expression) throws ParsingException;
}
