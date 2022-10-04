package expressions;

import exceptions.OverflowException;
import util.Checkers;

public class Negate extends AbstractUnaryOperation {
    public Negate(Expression first) {
        super(first);
    }

    @Override
    protected int calc(int x) throws OverflowException {
        Checkers.checkNegate(x);
        return -x;
    }
}
