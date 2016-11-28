package pl.edu.agh.amwj.value;

/**
 * Created by Kurtzz on 10.11.2016.
 */
public class TValue extends HeapValue{
    private TValue f1;
    private TValue f2;
    private IntegerValue data;

    public TValue(TValue f1, TValue f2, IntegerValue data) {
        this.f1 = f1;
        this.f2 = f2;
        this.data = data;
    }

    public TValue getF1() {
        return f1;
    }

    public void setF1(TValue f1) {
        this.f1 = f1;
    }

    public TValue getF2() {
        return f2;
    }

    public void setF2(TValue f2) {
        this.f2 = f2;
    }

    public IntegerValue getData() {
        return data;
    }

    public void setData(IntegerValue data) {
        this.data = data;
    }

    public Value evaluate() {
        return this;
    }

    public int toNumber() {
        return 0;
    }

//    @Override
//    public String toString() {
//
//        return "TValue{" +
//                "f1=" + f1 +
//                ", f2=" + f2 +
//                ", data=" + data +
//                '}';
//    }
}
