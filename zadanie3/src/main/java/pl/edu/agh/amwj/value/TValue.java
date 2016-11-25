package pl.edu.agh.amwj.value;

import static pl.edu.agh.amwj.Data.heap;

/**
 * Created by Kurtzz on 10.11.2016.
 */
public class TValue implements Value {
    private TValue f1;
    private TValue f2;
    private IntegerValue data;
    private int index;

    public TValue(TValue f1, TValue f2, IntegerValue data) {
        this.f1 = f1;
        this.f2 = f2;
        this.data = data;
    }

    public TValue getF1() {
        return f1;
    }

    public void setF1(TValue f1) {
        heap[index + 1] = f1.getIndex();
        this.f1 = f1;
    }

    public TValue getF2() {
        return f2;
    }

    public void setF2(TValue f2) {
        heap[index + 2] = f2.getIndex();
        this.f2 = f2;
    }

    public IntegerValue getData() {
        return data;
    }

    public void setData(IntegerValue data) {
        heap[index + 3] = data.toNumber();
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Value evaluate() {
        return this;
    }

    public int toNumber() {
        return 0;
    }

    @Override
    public String toString() {
        return "TValue{" +
                "f1=" + f1 +
                ", f2=" + f2 +
                ", data=" + data +
                '}';
    }
}
