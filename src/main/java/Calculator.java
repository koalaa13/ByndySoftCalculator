import exceptions.EvaluatingException;
import exceptions.ParsingException;
import expressions.Expression;
import parser.ExpressionParser;
import parser.Parser;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        String expression;
        Scanner scanner = new Scanner(System.in);
        Parser parser = new ExpressionParser();
        expression = scanner.nextLine();
        try {
            Expression parsedExpression = parser.parse(expression);
            System.out.println("Result of evaluating an expression = " + parsedExpression.eval());
        } catch (ParsingException e) {
            System.out.println(e.getFullMessage());
        } catch (EvaluatingException e) {
            System.out.println(e.getMessage());
        }
    }
}
