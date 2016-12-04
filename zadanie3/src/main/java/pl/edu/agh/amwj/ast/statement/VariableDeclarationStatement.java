package pl.edu.agh.amwj.ast.statement;

import pl.edu.agh.amwj.ast.expression.Expression;
import pl.edu.agh.amwj.ast.value.*;
import pl.edu.agh.amwj.exceptions.VariableAlreadyDefinedException;

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
        if (declaredVariables.containsKey(name)) {
            throw new VariableAlreadyDefinedException(name);
        }

        switch (type) {
            case T_TYPE:
                TValue newTValue = new TValue(null, null, new IntegerValue(0));

                declaredVariables.put(name, newTValue); //reference
                npjHeap.allocateTValue(newTValue);
                npjGraph.addNode(newTValue); //root node

                break;
            case S_TYPE:
                SValue newSValue = new SValue((StringValue) value.evaluate());
                declaredVariables.put(name, newSValue);

                if (newSValue.getContent() == null) {
                    newSValue.setHeapIndex(npjHeap.getNullIndex());
                } else {
                    npjHeap.allocateSValue(newSValue);
                    npjGraph.addNode(newSValue);
                }

                break;
        }
    }

    @Override
    public String toString() {
        return (type.equals(ObjectType.T_TYPE) ? "VarDeclT " : "VarDeclS ")
                + name + " " + value;
    }
}
