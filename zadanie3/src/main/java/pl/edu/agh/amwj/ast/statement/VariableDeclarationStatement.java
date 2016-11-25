package pl.edu.agh.amwj.ast.statement;

import pl.edu.agh.amwj.ast.expression.Expression;
import pl.edu.agh.amwj.value.*;

import static pl.edu.agh.amwj.Constants.*;
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
        this(type,  name, null);
    }

    public void execute() {
        switch (type) {
            case T_TYPE:
                TValue newTValue = new TValue(null, null, new IntegerValue(0));
                variables.put(name, newTValue);

                heap[currentHeapPosition] = T_HEADER;
                heap[++currentHeapPosition] = 0;
                heap[++currentHeapPosition] = 0;
                heap[++currentHeapPosition] = 0;

                break;
            case S_TYPE:
                SValue newSValue =  new SValue(null);

                newSValue.setIndex(currentHeapPosition);
                heap[currentHeapPosition] = S_HEADER;
                if (value != null) {
                    heap[++currentHeapPosition] = value.evaluate().toString().length();
                    newSValue.setContent(new StringValue(value.evaluate().toString()));
                } else {
                    heap[++currentHeapPosition] = 0;
                    heap[++currentHeapPosition] = 0;
                }

                variables.put(name, newSValue);

                break;
        }
    }
}
