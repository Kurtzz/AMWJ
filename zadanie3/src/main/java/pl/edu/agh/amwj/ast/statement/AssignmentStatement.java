package pl.edu.agh.amwj.ast.statement;

import org.apache.commons.beanutils.PropertyUtils;
import pl.edu.agh.amwj.ast.expression.Expression;
import pl.edu.agh.amwj.value.Value;

import java.lang.reflect.InvocationTargetException;

import static pl.edu.agh.amwj.Data.*;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class AssignmentStatement implements Statement {
    private final String name;
    private final Expression value;

    public AssignmentStatement(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    public void execute() {
        String[] splittedName = name.split("\\.");
        Value object = variables.get(splittedName[0]);

        try {
            PropertyUtils.setNestedProperty(object, splittedName[1], value.evaluate());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
