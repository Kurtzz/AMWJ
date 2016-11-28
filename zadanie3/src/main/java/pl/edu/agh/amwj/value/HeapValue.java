package pl.edu.agh.amwj.value;

/**
 * Created by Kurtzz on 2016-11-28.
 */
public abstract class HeapValue implements Value {
    private int heapIndex;

    public int getHeapIndex() {
        return heapIndex;
    }

    public void setHeapIndex(int heapIndex) {
        this.heapIndex = heapIndex;
    }
}
