package pl.edu.agh.amwj.ast.expression;

import org.apache.commons.beanutils.PropertyUtils;
import pl.edu.agh.amwj.ast.value.Value;

import java.lang.reflect.InvocationTargetException;

import static pl.edu.agh.amwj.Data.declaredVariables;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    public Value evaluate() {
        String[] tokens = name.split("\\.", 2);
        Value value = (Value) declaredVariables.get(tokens[0]);

        if (tokens.length == 1) {
            return value;
        }

        try {
            Object property = PropertyUtils.getNestedProperty(value, tokens[1]);
            return (Value) property;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
