package pl.edu.agh.amwj;

import com.google.common.collect.Maps;
import com.google.common.graph.*;
import pl.edu.agh.amwj.ast.value.HeapValue;
import pl.edu.agh.amwj.collector.Collector;
import pl.edu.agh.amwj.collector.MyEdge;
import pl.edu.agh.amwj.collector.NpjCollector;
import pl.edu.agh.amwj.exceptions.InvalidHeapSizeException;
import pl.edu.agh.amwj.collector.NpjHeap;

import java.util.Map;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Data {
    public static NpjHeap npjHeap;
    public static Map<Object, Object> declaredVariables;
    public static MutableNetwork<HeapValue, MyEdge> npjGraph;
    public static Collector npjCollector;

    public static void initializeData(int size) throws InvalidHeapSizeException {
        declaredVariables = Maps.newHashMap();
        npjGraph = NetworkBuilder.directed().allowsParallelEdges(true).allowsSelfLoops(true).build();
        npjHeap = NpjHeap.getInstance(size);
        npjCollector = new NpjCollector();
    }
}
