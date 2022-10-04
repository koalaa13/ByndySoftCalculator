import exceptions.*;
import expressions.Expression;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.ExpressionParser;
import parser.Parser;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private static Parser parser;

    @BeforeAll
    public static void beforeAll() {
        parser = new ExpressionParser();
    }

    private void correctExpressionTest(String expression, int rightAns) {
        Expression parsedExpression =
                assertDoesNotThrow(() -> parser.parse(expression));
        int res = assertDoesNotThrow(parsedExpression::eval);
        assertEquals(rightAns, res);
    }

    @Test
    public void addTest() {
        String expression = "2 + 2";
        correctExpressionTest(expression, 4);
    }

    @Test
    public void subtractTest() {
        String expression = "2 - 2";
        correctExpressionTest(expression, 0);
    }

    @Test
    public void multiplyTest() {
        String expression = "2 * 3";
        correctExpressionTest(expression, 6);
    }

    @Test
    public void divideTest() {
        String expression = "3 / 2";
        correctExpressionTest(expression, 1);
    }

    @Test
    public void combinedExpressionTest() {
        String expression = "(2 + 3) * -(-22 / 11) - 10";
        correctExpressionTest(expression, 0);
    }

    @Test
    public void operationPrioritiesTest() {
        String expression = "2 + 2 * 2";
        correctExpressionTest(expression, 6);

        expression = "2 - 2 * 2";
        correctExpressionTest(expression, -2);

        expression = "2 + 2 / 2";
        correctExpressionTest(expression, 3);

        expression = "2 - 2 / 2";
        correctExpressionTest(expression, 1);

        expression = "(2 + 2) * 2";
        correctExpressionTest(expression, 8);
    }

    @Test
    public void unpairedBracketsTest() {
        assertThrows(UnpairedBracketsException.class, () -> parser.parse("(2 + 2"));

        assertThrows(UnpairedBracketsException.class, () -> parser.parse("2 + 2)"));
    }

    @Test
    public void nonIntegerConstTest() {
        String expression = "1.2 + 2.21";
        assertThrows(IncorrectConstException.class, () -> parser.parse(expression));
    }

    @Test
    public void missingOperationTest() {
        assertThrows(MissingOperationException.class, () -> parser.parse("(2 + 2) (2 - 3)"));

        assertThrows(MissingOperationException.class, () -> parser.parse("2(2 - 3)"));

        assertThrows(MissingOperationException.class, () -> parser.parse("2 2"));
    }

    @Test
    public void missingOperandTest() {
        assertThrows(MissingOperandException.class, () -> parser.parse("(2 + )"));

        assertThrows(MissingOperandException.class, () -> parser.parse("2 + "));

        assertThrows(MissingOperandException.class, () -> parser.parse("2 + 2 -"));
    }

    @Test
    public void checkDivideTest() {
        Expression parsedExpression = assertDoesNotThrow(() -> parser.parse("1 / 0"));

        assertThrows(DivisionByZeroException.class, parsedExpression::eval);

        String expression = Integer.MIN_VALUE + " / -1";
        parsedExpression = assertDoesNotThrow(() -> parser.parse(expression));
        assertThrows(OverflowException.class, parsedExpression::eval);
    }

    @Test
    public void checkMultiplyTest() {
        Expression parsedExpression = assertDoesNotThrow(() -> parser.parse(Integer.MAX_VALUE + "*" + Integer.MAX_VALUE));
        assertThrows(OverflowException.class, parsedExpression::eval);

        parsedExpression = assertDoesNotThrow(() -> parser.parse(Integer.MAX_VALUE + "*" + Integer.MIN_VALUE));
        assertThrows(OverflowException.class, parsedExpression::eval);

        parsedExpression = assertDoesNotThrow(() -> parser.parse(Integer.MIN_VALUE + "*" + Integer.MIN_VALUE));
        assertThrows(OverflowException.class, parsedExpression::eval);

        parsedExpression = assertDoesNotThrow(() -> parser.parse(Integer.MIN_VALUE + "*" + Integer.MAX_VALUE));
        assertThrows(OverflowException.class, parsedExpression::eval);
    }

    @Test
    public void checkSubtractTest() {
        Expression parsedExpression = assertDoesNotThrow(() -> parser.parse(Integer.MIN_VALUE + "-1"));
        assertThrows(OverflowException.class, parsedExpression::eval);

        parsedExpression = assertDoesNotThrow(() -> parser.parse(Integer.MAX_VALUE + "- -1"));
        assertThrows(OverflowException.class, parsedExpression::eval);

        parsedExpression = assertDoesNotThrow(() -> parser.parse("-" + Integer.MIN_VALUE));
        assertThrows(OverflowException.class, parsedExpression::eval);
    }

    @Test
    public void checkAddTest() {
        Expression parsedExpression = assertDoesNotThrow(() -> parser.parse(Integer.MIN_VALUE + "+ -1"));
        assertThrows(OverflowException.class, parsedExpression::eval);

        parsedExpression = assertDoesNotThrow(() -> parser.parse(Integer.MAX_VALUE + "+ 1"));
        assertThrows(OverflowException.class, parsedExpression::eval);
    }
}
