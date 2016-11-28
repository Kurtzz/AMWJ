package pl.edu.agh.amwj.utils;

import pl.edu.agh.amwj.exceptions.InvalidHeapSizeException;
import pl.edu.agh.amwj.value.HeapValue;
import pl.edu.agh.amwj.value.SValue;
import pl.edu.agh.amwj.value.TValue;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.agh.amwj.Constants.S_HEADER;
import static pl.edu.agh.amwj.Constants.T_HEADER;
import static pl.edu.agh.amwj.Data.graph;

/**
 * Created by P on 27.11.2016.
 */
public class MyHeap {
    private List<HeapValue> heapValues;
    private int currentPosition;
    private int size;
    private int currentHalf; //left = -1, right = 1;

    private MyHeap(int size) {
        this.size = size;
        heapValues = new ArrayList<HeapValue>();
        currentPosition = 0;
        currentHalf = -1;
    }

    public static MyHeap getInstance(int size) throws InvalidHeapSizeException {
        if ((size + 1) % 8 != 1) {
            throw new InvalidHeapSizeException("Invalid heap size: " + size + 1);
        }

        return new MyHeap(size);
    }

    public void allocateTValue(TValue value) {
        if (isNotEnoughSpace(4)) {
            sweep();
        }

        if (isNotEnoughSpace(4)) {
            throw new RuntimeException("Out of memory");
        }

//        if (value == null) {
//            value = new TValue(null, null, new IntegerValue(0));
//        }

        value.setHeapIndex(currentPosition);
        heapValues.add(value);

        currentPosition += 4;
    }

    public void allocateSValue(SValue value) {
        if (isNotEnoughSpace(value.toString().length())) {
            sweep();
        }

        if (isNotEnoughSpace(value.toString().length())) {
            throw new RuntimeException("Out of memory");
        }

        value.setHeapIndex(currentPosition);
        heapValues.add(value);
        currentPosition += value.toString().length() + 1;
    }

    private boolean isNotEnoughSpace(int requireSpace) {
        int spaceLeft = (size / 2) - currentPosition % (size / 2);

        /* No more space
           OR we accidentally move to right half == left is full
           OR right half is over
        */
        return (spaceLeft - requireSpace) < 0 || ((currentPosition >= (size / 2)) && currentHalf < 0) || currentPosition >= size;
    }

    public void sweep() {
        heapValues.clear();

        currentPosition = (currentHalf < 0 ? (size / 2) : 0);
        currentHalf = (currentPosition / (size / 4)) - 1;
        for (HeapValue value : graph.nodes()) {
            if (value instanceof TValue) {
                allocateTValue((TValue) value);
            } else if (value instanceof SValue) {
                allocateSValue(((SValue) value));
            }
        }
    }

    public int[] getHeap() {
        int[] heap = new int[size + 1];
        int index;

        for (HeapValue value : heapValues) {
            index = value.getHeapIndex();

            if (value instanceof TValue) {
                TValue tValue = (TValue) value;
                heap[index] = T_HEADER;
                heap[++index] = (tValue.getF1()) == null ? 0 : tValue.getF1().getHeapIndex();
                heap[++index] = (tValue.getF2()) == null ? 0 : tValue.getF2().getHeapIndex();
                heap[++index] = tValue.getData().toNumber();
            }

            if (value instanceof SValue) {
                SValue sValue = (SValue) value;
                heap[index] = S_HEADER;
                heap[++index] = sValue.getContent().toString().length();

                index++;
                char[] contentArray = sValue.toString().toCharArray();
                for (char aContentArray : contentArray) {
                    heap[index++] = (int) aContentArray;
                }
            }
        }

        heap[size] = 0; //NULL

        return heap;
    }

    public List<HeapValue> getHeapValues() {
        return heapValues;
    }

    public int getNullIndex() {
        return size;
    }

    public void print() {
        System.out.println("HEAP currentHalf = " + ((currentHalf < 0) ? "left" : "right") + "\n\t" + heapValues.toString());
    }
}
