package pl.edu.agh.amwj.ast.expression;

import pl.edu.agh.amwj.ast.value.Value;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public interface Expression {
    Value evaluate();
}