package pl.edu.agh.amwj.value.types;

/**
 * Created by Comarch on 2016-11-10.
 */
public class StringValue implements Value {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }
    @Override public String toString() { return value; }
    public double toNumber() { return Double.parseDouble(value); }

    public Value evaluate() { return this; }
}
