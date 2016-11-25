package pl.edu.agh.amwj.ast;

import pl.edu.agh.amwj.value.types.*;

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
                variables.put(name, new TValue(null, null, new IntegerValue(0)));
                break;
            case S_TYPE:
                if (value == null) {
                    variables.put(name, new SValue(null));
                } else {
                    variables.put(name, new SValue((StringValue) value.evaluate()));
                }
                break;
        }
    }
}
