package pl.edu.agh.amwj.value.types;

import pl.edu.agh.amwj.ast.Expression;

/**
 * Created by Comarch on 2016-11-10.
 */
public interface Value extends Expression {
    String toString();
    double toNumber();
}
