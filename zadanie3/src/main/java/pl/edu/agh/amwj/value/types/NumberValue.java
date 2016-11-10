package pl.edu.agh.amwj.value.types;

/**
 * Created by Comarch on 2016-11-10.
 */
public class NumberValue implements Value {
    private final double value;

    public NumberValue(double value) {
        this.value = value;
    }
    @Override public String toString() { return Double.toString(value); }
    public double toNumber() { return value; }

    public Value evaluate() { return this; }
}
