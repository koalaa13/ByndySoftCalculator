package expressions;

import exceptions.DivisionByZeroException;
import exceptions.OverflowException;
import util.Checkers;

public class Divide extends AbstractBinaryOperation {
    public Divide(Expression first, Expression second) {
        super(first, second);
    }

    @Override
    protected int calc(int arg1, int arg2) throws OverflowException, DivisionByZeroException {
        Checkers.checkDivide(arg1, arg2);
        return arg1 / arg2;
    }
}
