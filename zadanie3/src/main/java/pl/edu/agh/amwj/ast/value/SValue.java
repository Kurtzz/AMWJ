package pl.edu.agh.amwj.ast.value;

/**
 * Created by Kurtzz on 10.11.2016.
 */
public class SValue extends HeapValue {
    private StringValue content;

    public SValue(StringValue content) {
        this.content = content;
        this.setSize(content == null ? 0 : content.toString().length() + 2);
    }

    public StringValue getContent() {
        return content;
    }

    public void setContent(StringValue content) {
        if (content == null) {
            return;
        }

        this.content = content;
        this.setSize(content.toString().length() + 2);
    }

    public Value evaluate() {
        return this;
    }

    public int toNumber() {
        return 0;
    }

    @Override
    public String toString() {
        return content == null ? "NULL" : content.toString();
    }
}
