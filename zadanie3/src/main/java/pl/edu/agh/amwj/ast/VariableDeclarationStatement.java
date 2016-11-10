package pl.edu.agh.amwj.ast;

import static pl.edu.agh.amwj.Data.variables;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class VariableDeclarationStatement implements Statement {
    private String name;
    private Expression value;

    public VariableDeclarationStatement(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    public void execute() {
        variables.put(name, value.evaluate());
    }
}
