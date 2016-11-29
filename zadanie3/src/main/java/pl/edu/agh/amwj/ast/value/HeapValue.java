package pl.edu.agh.amwj.ast.value;

/**
 * Created by Kurtzz on 2016-11-28.
 */
public abstract class HeapValue implements Value {
    private int heapIndex;
    private int size;

    public int getHeapIndex() {
        return heapIndex;
    }

    public void setHeapIndex(int heapIndex) {
        this.heapIndex = heapIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
