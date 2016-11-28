package pl.edu.agh.amwj.ast.statement;

import org.apache.commons.beanutils.PropertyUtils;
import pl.edu.agh.amwj.ast.expression.Expression;
import pl.edu.agh.amwj.value.*;

import java.util.Set;

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

    public void execute() throws Exception {
        String[] splittedName = name.split("\\.", 2);
        Value object = (Value) gcRoots.get(splittedName[0]);

        if (object instanceof TValue) {
            assignT(object, splittedName);
        } else if (object instanceof SValue) {
            assignS(object, splittedName);
        }
    }

    private void assignT(Value object, String[] splittedName) throws Exception {
        //varname = <Expression>
        if (splittedName.length == 1) {
            if (value.evaluate() != null && !object.getClass().equals(value.evaluate().getClass())) {
                throw new UnsupportedOperationException(object.getClass().getSimpleName() + " = " + value.evaluate().getClass().getSimpleName());
            }

            if (value.evaluate() == null) { //<Expression> = NULL
                gcRoots.remove(splittedName[0]);
            } else {
                gcRoots.put(splittedName[0], value.evaluate());
            }

            removeFromGraph(object);

            return;
        }

        //varname.field... = <Expression>

        Value oldValue = (Value) PropertyUtils.getNestedProperty(object, splittedName[1]);
        PropertyUtils.setNestedProperty(object, splittedName[1], value.evaluate());

        String[] splittedName2 = splittedName[1].split("\\.");

        TValue updatedValue;
        String lastPart;
        if (splittedName2.length == 1) { //varname.field
            updatedValue = (TValue) object;
            lastPart = splittedName2[0];
        } else if (splittedName2.length == 2) { //varname.field.field
            updatedValue = (TValue) PropertyUtils.getProperty(object, splittedName2[0]);
            lastPart = splittedName2[1];
        } else { //varname.field.field.field ...
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(splittedName2[0]); //varname.FIELD
            for (int i = 1; i < splittedName2.length - 1; i++) { //varname.FIELD.FIELD ...
                stringBuilder.append(".").append(splittedName2[i]);
            }
            lastPart = splittedName2[splittedName2.length - 1]; //varname.field.field.(...).lastpart

            updatedValue = (TValue) PropertyUtils.getNestedProperty(object, stringBuilder.toString());
        }

        if (lastPart.equals("f1") || lastPart.equals("f2")) {
            if (oldValue != null) {
                graph.removeEdge(updatedValue, oldValue);
            }
            graph.putEdge(updatedValue, (HeapValue) value.evaluate());
        }
    }

    private void assignS(Value object, String[] splittedName) {
        Value evaluatedValue = value.evaluate();

        if (evaluatedValue instanceof SValue) {
            gcRoots.put(splittedName[0], evaluatedValue);

        } else if (evaluatedValue instanceof StringValue) {
            SValue newSValue = new SValue((StringValue) evaluatedValue);
            newSValue.setContent((StringValue) evaluatedValue);

            myHeap.allocateSValue(((SValue) newSValue));
            graph.addNode(newSValue);

            gcRoots.put(splittedName[0], newSValue);

        } else if (evaluatedValue == null) {
            gcRoots.remove(splittedName[0]);
        }

        //There is no reference to that String
        if (!gcRoots.containsValue(object)) {
            graph.removeNode(object);
        }
    }

    //TODO: rename it
    private void removeFromGraph(Value value) {
        if (graph.predecessors(value).isEmpty() && !gcRoots.values().contains(value)) {
            Set<HeapValue> successors = graph.successors(value);
            graph.removeNode(value);
            for (Value successor : successors) {
                removeFromGraph(successor);
            }
        }
    }
}
