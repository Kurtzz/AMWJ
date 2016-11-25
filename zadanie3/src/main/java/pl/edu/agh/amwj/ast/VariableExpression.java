package pl.edu.agh.amwj.ast;

import org.apache.commons.beanutils.PropertyUtils;
import pl.edu.agh.amwj.value.types.IntegerValue;
import pl.edu.agh.amwj.value.types.Value;

import java.lang.reflect.InvocationTargetException;

import static pl.edu.agh.amwj.Data.heap;
import static pl.edu.agh.amwj.Data.heapmap;
import static pl.edu.agh.amwj.Data.variables;

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
        Value value = variables.get(tokens[0]);

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
