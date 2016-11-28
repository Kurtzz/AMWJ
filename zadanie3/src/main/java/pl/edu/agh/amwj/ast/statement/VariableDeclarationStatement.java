package pl.edu.agh.amwj.ast.statement;

import pl.edu.agh.amwj.ast.expression.Expression;
import pl.edu.agh.amwj.value.*;

import static pl.edu.agh.amwj.Data.*;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class VariableDeclarationStatement implements Statement {
    private ObjectType type;
    private String name;
    private Expression value;

    public VariableDeclarationStatement(ObjectType type, String name, Expression value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public VariableDeclarationStatement(ObjectType type, String name) {
        this(type, name, null);
    }

    public void execute() {
        switch (type) {
            case T_TYPE:
                TValue newTValue = new TValue(null, null, new IntegerValue(0));
                gcRoots.put(name, newTValue); //Reference
                graph.addNode(newTValue); //root node
                newTValue.setHeapIndex(myHeap.getCurrentPosition()); //TODO: move to MyHeap
                myHeap.allocateTValue();

                break;
            case S_TYPE:
                SValue newSValue = new SValue(null);
                newSValue.setContent((StringValue) value.evaluate());

                //If String Pool already contains value
                if (value.evaluate() != null && gcRoots.containsValue(newSValue)) {
                    for (Object sValue : gcRoots.values()) {
                        if (sValue.equals(newSValue)) {
                            newSValue.setHeapIndex(((SValue) sValue).getHeapIndex()); //TODO: move to MyHeap
                            break;
                        }
                    }
                } else {
                    newSValue.setHeapIndex(myHeap.getCurrentPosition());
                    myHeap.allocateSValue((StringValue) value.evaluate());
                    graph.addNode(newSValue);
                }

                gcRoots.put(name, newSValue);

                break;
        }
    }
}
