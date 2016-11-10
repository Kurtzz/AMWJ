package pl.edu.agh.amwj.ast;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class PrintStatement implements Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    public void execute() {
        System.out.println(expression.evaluate().toString());
    }
}
