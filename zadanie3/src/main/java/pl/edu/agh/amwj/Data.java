package pl.edu.agh.amwj;

import pl.edu.agh.amwj.utils.GCGraph;
import pl.edu.agh.amwj.exceptions.InvalidHeapSizeException;
import pl.edu.agh.amwj.utils.MyHeap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Data {
    public static MyHeap myHeap;
    public static Map<Object, Object> gcRoots;// = new HashMap<Object, Object>();
    public static GCGraph graph;// = new GCGraph();

    public static void initializeData(int size) throws InvalidHeapSizeException {
        gcRoots = new HashMap<Object, Object>();
        graph = new GCGraph();
        myHeap = MyHeap.getInstance(size);
    }
}
