package pl.edu.agh.amwj.ast;

import pl.edu.agh.amwj.value.types.IntegerValue;
import pl.edu.agh.amwj.value.types.StringValue;
import pl.edu.agh.amwj.value.types.Value;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class OperatorExpression implements Expression {
    private final Expression left;
    private final char operator;
    private final Expression right;

    public OperatorExpression(Expression left, char operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Value evaluate() {
        Value leftVal = left.evaluate();
        Value rightVal = right.evaluate();

        switch (operator) {
            case '=':
                // Coerce to the left argument's type, then compare.
                if (leftVal instanceof IntegerValue) {
                    return new IntegerValue((leftVal.toNumber() ==
                            rightVal.toNumber()) ? 1 : 0);
                } else {
                    return new IntegerValue(leftVal.toString().equals(
                            rightVal.toString()) ? 1 : 0);
                }
        }
        throw new Error("Unknown operator.");
    }
}
