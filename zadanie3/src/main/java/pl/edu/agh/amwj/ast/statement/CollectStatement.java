package pl.edu.agh.amwj.ast.statement;

import pl.edu.agh.amwj.NPJ;

import static pl.edu.agh.amwj.Data.*;

/**
 * Created by Kurtzz on 2016-11-25.
 */
public class CollectStatement implements Statement {
    public void execute() {
        NPJ.collect(npjHeap.getHeap(), npjCollector, declaredVariables);
    }
}
