package expressions;

import exceptions.EvaluatingException;
import exceptions.ParsingException;

public interface Expression {
    int eval() throws EvaluatingException;
}
