package pl.edu.agh.amwj.collector;

import java.util.Map;

public interface Collector {
    void collect(int[] heap, Map<Object, Object> params);
}
