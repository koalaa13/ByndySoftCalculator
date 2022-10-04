package expressions;

import exceptions.OverflowException;
import util.Checkers;

public class Add extends AbstractBinaryOperation {
    public Add(Expression first, Expression second) {
        super(first, second);
    }

    @Override
    protected int calc(int arg1, int arg2) throws OverflowException {
        Checkers.checkAdd(arg1, arg2);
        return arg1 + arg2;
    }
}
