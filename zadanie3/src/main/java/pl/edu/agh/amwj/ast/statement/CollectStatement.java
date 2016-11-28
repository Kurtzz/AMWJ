package pl.edu.agh.amwj.ast.statement;

import pl.edu.agh.amwj.MyCollector;
import pl.edu.agh.amwj.template.NPJ;

import static pl.edu.agh.amwj.Data.myHeap;
import static pl.edu.agh.amwj.Data.gcRoots;

/**
 * Created by Kurtzz on 2016-11-25.
 */
public class CollectStatement implements Statement {
    public void execute() {
        NPJ.collect(myHeap.getHeap(), new MyCollector(), gcRoots);
    }
}
