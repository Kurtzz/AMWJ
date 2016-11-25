package pl.edu.agh.amwj.value;

import pl.edu.agh.amwj.ast.expression.Expression;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public interface Value extends Expression {
    String toString();
    int toNumber();
}
