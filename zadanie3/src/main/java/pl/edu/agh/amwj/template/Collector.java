package pl.edu.agh.amwj.template;

import java.util.Map;

public interface Collector {
        public void collect(int[] heap, Map<Object, Object> params);
}