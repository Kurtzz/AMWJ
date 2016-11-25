package pl.edu.agh.amwj.value;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class StringValue implements Value {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }
    @Override public String toString() { return value; }
    public int toNumber() { return Integer.parseInt(value); }

    public Value evaluate() { return this; }
}