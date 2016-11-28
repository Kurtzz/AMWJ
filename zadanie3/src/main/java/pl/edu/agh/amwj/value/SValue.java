package pl.edu.agh.amwj.value;

/**
 * Created by Kurtzz on 10.11.2016.
 */
public class SValue implements Value {
    private StringValue content;
    private int heapIndex;

    public SValue(StringValue content) {
        this.content = content;
    }

    public StringValue getContent() {
        return content;
    }

    public void setContent(StringValue content) {
        if (content == null) {
            return;
        }

        this.content = content;
    }

    public int getHeapIndex() {
        return heapIndex;
    }

    public void setHeapIndex(int heapIndex) {
        this.heapIndex = heapIndex;
    }

    public Value evaluate() {
        return this;
    }

    public int toNumber() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SValue sValue = (SValue) o;

        return content != null ? content.equals(sValue.content) : sValue.content == null;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public String toString() {
        return content == null ? "null" : content.toString();
    }
}
