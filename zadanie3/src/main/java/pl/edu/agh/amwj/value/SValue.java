package pl.edu.agh.amwj.value;

import static pl.edu.agh.amwj.Data.heap;

/**
 * Created by Kurtzz on 10.11.2016.
 */
public class SValue implements Value{
    private StringValue content;
    private int index;

    public SValue(StringValue content) {
        this.content = content;
    }

    public StringValue getContent() {
        return content;
    }

    public void setContent(StringValue content) {
        char[] contentArray = content.toString().toCharArray();
        for (int i = 0; i < contentArray.length; i++) {
            heap[index + 2 + i] = Character.getNumericValue(contentArray[i]);
        }

        //if new content < current content
        for (int i = contentArray.length; i < heap[index + 1]; i++) {
            heap[index + 2 + i] = 0;
        }

        this.content = content;
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
        return content.toString();
    }
}
