package pl.edu.agh.amwj.ast.statement;

import pl.edu.agh.amwj.template.NPJ;
import pl.edu.agh.amwj.value.HeapValue;
import pl.edu.agh.amwj.value.SValue;
import pl.edu.agh.amwj.value.TValue;

import java.util.ArrayList;
import java.util.Collection;

import static pl.edu.agh.amwj.Data.myHeap;

/**
 * Created by Kurtzz on 2016-11-25.
 */
public class HeapAnalyzeStatement implements Statement {
    public void execute() {
        Collection<Integer> typeTVariables = new ArrayList<Integer>();
        Collection<String> typeSVariables = new ArrayList<String>();

        for (HeapValue value : myHeap.getHeapValues()) {
            if (value instanceof TValue) {
                typeTVariables.add(((TValue) value).getData().toNumber());
            } else if (value instanceof SValue) {
                typeSVariables.add(((SValue) value).getContent().toString());
            }
        }

        NPJ.heapAnalyze(typeTVariables, typeSVariables);
    }
}
