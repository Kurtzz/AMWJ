package pl.edu.agh.amwj.ast;

import pl.edu.agh.amwj.value.types.NumberValue;
import pl.edu.agh.amwj.value.types.Value;

import static pl.edu.agh.amwj.Data.variables;

/**
 * Created by Comarch on 2016-11-10.
 */
public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    public Value evaluate() {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        return new NumberValue(0);
    }
}
