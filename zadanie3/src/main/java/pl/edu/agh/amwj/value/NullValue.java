package pl.edu.agh.amwj.value;

/**
 * Created by P on 27.11.2016.
 */
public class NullValue implements Value {
    public Value evaluate() {
        return null;
    }

    public int toNumber() {
        return 0;
    }
}
