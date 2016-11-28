package pl.edu.agh.amwj.ast.statement;

import pl.edu.agh.amwj.ast.expression.Expression;
import pl.edu.agh.amwj.exceptions.VariableAlreadyDefinedException;
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
        if (gcRoots.containsKey(name)) {
            throw new VariableAlreadyDefinedException(name);
        }
        switch (type) {
            case T_TYPE:
                TValue newTValue = new TValue(null, null, new IntegerValue(0));

                gcRoots.put(name, newTValue); //Reference
                myHeap.allocateTValue(newTValue);
                graph.addNode(newTValue); //root node

                break;
            case S_TYPE:
                SValue newSValue = new SValue(null);
                newSValue.setContent((StringValue) value.evaluate());

                myHeap.allocateSValue(newSValue);
                graph.addNode(newSValue);
                gcRoots.put(name, newSValue);

                break;
        }
    }
}
