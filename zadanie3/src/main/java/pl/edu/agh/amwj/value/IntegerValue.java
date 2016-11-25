package pl.edu.agh.amwj.value;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class IntegerValue implements Value {
    private final int value;

    public IntegerValue(int value) {
        this.value = value;
    }
    @Override public String toString() { return Integer.toString(value); }
    public double toNumber() { return value; }

    public Value evaluate() { return this; }
}
