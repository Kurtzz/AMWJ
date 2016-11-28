package pl.edu.agh.amwj;

import pl.edu.agh.amwj.utils.GCGraph;
import pl.edu.agh.amwj.utils.MyHeap;
import pl.edu.agh.amwj.value.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Data {
    public static final MyHeap myHeap = new MyHeap(128);
    public static final Map<Object, Object> gcRoots = new HashMap<Object, Object>();
    public static final GCGraph graph = new GCGraph();
    public static int currentStatement;
}
