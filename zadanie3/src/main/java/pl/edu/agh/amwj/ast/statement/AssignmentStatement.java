package pl.edu.agh.amwj.ast.statement;

import org.apache.commons.beanutils.PropertyUtils;
import pl.edu.agh.amwj.ast.expression.Expression;
import pl.edu.agh.amwj.ast.value.*;
import pl.edu.agh.amwj.collector.MyEdge;
import pl.edu.agh.amwj.exceptions.UndeclaredVariableException;

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

        if (!declaredVariables.containsKey(splittedName[0])) {
            throw new UndeclaredVariableException(splittedName[0]);
        }

        Value object = (Value) declaredVariables.get(splittedName[0]);

        if (object instanceof TValue) {
            assignT(object, splittedName);
        } else if (object instanceof SValue) {
            assignS(object, splittedName);
        }
    }

    private void assignT(Value object, String[] splittedName) throws Exception {
        //varname = <Expression>
        Value evaluatedValue = value.evaluate();

        if (splittedName.length == 1) {
            if (evaluatedValue != null && !object.getClass().equals(evaluatedValue.getClass())) {
                throw new UnsupportedOperationException(object.getClass().getSimpleName() + " = " + evaluatedValue.getClass().getSimpleName());
            }

            if (evaluatedValue == null) { //<Expression> = NULL
                declaredVariables.put(splittedName[0], null);
            } else {
                declaredVariables.put(splittedName[0], evaluatedValue);
            }

            removeFromGraph(object);

            return;
        }

        //varname.field... = <Expression>

        Value oldValue = (Value) PropertyUtils.getNestedProperty(object, splittedName[1]);
        PropertyUtils.setNestedProperty(object, splittedName[1], evaluatedValue);

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

            //There is an edge
            if (oldValue != null) {
                MyEdge edgeToRemove = null;
                for (MyEdge edge : npjGraph.edgesConnecting(updatedValue, oldValue)) {
                    if (edge.getType().getName().equals(lastPart)) {
                        edgeToRemove = edge;
                    }
                }
                npjGraph.removeEdge(edgeToRemove);
                removeFromGraph(oldValue);
            }
            if (evaluatedValue != null) {
                npjGraph.addEdge(updatedValue, (HeapValue) evaluatedValue, new MyEdge(lastPart));
            }
        }
    }

    private void assignS(Value object, String[] splittedName) {
        Value evaluatedValue = value.evaluate();

        if (evaluatedValue instanceof SValue) {
            declaredVariables.put(splittedName[0], evaluatedValue);
        } else if (evaluatedValue instanceof StringValue) {
            SValue newSValue = new SValue((StringValue) evaluatedValue);
            newSValue.setContent((StringValue) evaluatedValue);

            npjHeap.allocateSValue(newSValue);
            npjGraph.addNode(newSValue);

            declaredVariables.put(splittedName[0], newSValue);
        } else if (evaluatedValue == null) {
            declaredVariables.put(splittedName[0], new NullValue());
        }

        //There is no reference to that String
        if (!declaredVariables.containsValue(object)) {
            npjGraph.removeNode(object);
        }
    }

    //TODO: rename it
    private void removeFromGraph(Value value) {
        Set<HeapValue> predecessors = npjGraph.predecessors(value);

        //There is no reference to 'value' AND none of variables points to that 'value'
        //OR has only one predecessor - itself
        if (npjGraph.predecessors(value).isEmpty() && !declaredVariables.containsValue(value)
                || (predecessors.contains(value) && predecessors.size() == 1)) {
            Set<HeapValue> successors = npjGraph.successors(value);
            npjGraph.removeNode(value);
            //If object which has been removed was the only one predecessor of another object - it is not reachable
            for (Value successor : successors) {
                removeFromGraph(successor);
            }
        }
    }
}
