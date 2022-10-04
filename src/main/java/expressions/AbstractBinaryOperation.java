package expressions;

import exceptions.EvaluatingException;
import exceptions.ParsingException;

public abstract class AbstractBinaryOperation implements Expression {
    private final Expression first, second;

    protected AbstractBinaryOperation(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }

    protected abstract int calc(int arg1, int arg2) throws EvaluatingException;

    @Override
    public int eval() throws EvaluatingException {
        return calc(first.eval(), second.eval());
    }
}
