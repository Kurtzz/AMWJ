package pl.edu.agh.amwj.collector;

import java.util.Map;

import static pl.edu.agh.amwj.Data.npjHeap;

/**
 * Created by Kurtzz on 2016-11-25.
 */
public class NpjCollector implements Collector {
    public void collect(int[] heap, Map<Object, Object> params) {
        npjHeap.sweep();
    }
}
