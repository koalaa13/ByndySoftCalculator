package expressions;

import exceptions.EvaluatingException;
import exceptions.OverflowException;
import exceptions.ParsingException;

public abstract class AbstractUnaryOperation implements Expression {
    private final Expression first;

    protected AbstractUnaryOperation(Expression first) {
        this.first = first;
    }

    protected abstract int calc(int x) throws OverflowException;

    @Override
    public int eval() throws EvaluatingException {
        return calc(first.eval());
    }
}
