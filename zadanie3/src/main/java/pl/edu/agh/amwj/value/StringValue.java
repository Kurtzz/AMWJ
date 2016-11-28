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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringValue that = (StringValue) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
