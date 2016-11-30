package pl.edu.agh.amwj.collector;

import pl.edu.agh.amwj.exceptions.InvalidHeapSizeException;
import pl.edu.agh.amwj.ast.value.HeapValue;
import pl.edu.agh.amwj.ast.value.SValue;
import pl.edu.agh.amwj.ast.value.TValue;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.agh.amwj.collector.NpjHeapConstants.S_HEADER;
import static pl.edu.agh.amwj.collector.NpjHeapConstants.T_HEADER;
import static pl.edu.agh.amwj.Data.npjGraph;

/**
 * Created by P on 27.11.2016.
 */
public class NpjHeap {
    private List<HeapValue> heapValues;
    private int currentHeapIndexPosition;
    private int heapSize; //real size + 1 (NULL)
    private int realSize;
    private int currentHalf; //left = -1, right = 1;

    private NpjHeap(int heapSize) {
        this.heapSize = heapSize;
        this.realSize = heapSize - 1;
        this.heapValues = new ArrayList<HeapValue>();
        this.currentHeapIndexPosition = 0;
        this.currentHalf = -1;
    }

    public static NpjHeap getInstance(int size) throws InvalidHeapSizeException {
        if (size % 8 != 1) {
            throw new InvalidHeapSizeException("Invalid heap size: " + size);
        }

        return new NpjHeap(size);
    }

    public void allocateTValue(TValue value) {
        if (isNotEnoughSpace(4)) {
            sweep();
        }

        if (isNotEnoughSpace(4)) {
            throw new RuntimeException("Out of memory");
        }

        value.setHeapIndex(currentHeapIndexPosition);
        heapValues.add(value);

        currentHeapIndexPosition += 4;
    }

    public void allocateSValue(SValue value) {
        if (isNotEnoughSpace(value.toString().length())) {
            sweep();
        }

        if (isNotEnoughSpace(value.toString().length())) {
            throw new RuntimeException("Out of memory");
        }

        value.setHeapIndex(currentHeapIndexPosition);
        heapValues.add(value);
        currentHeapIndexPosition += value.toString().length() + 1;
    }

    private boolean isNotEnoughSpace(int requireSpace) {
        int spaceLeft = (realSize / 2) - currentHeapIndexPosition % (realSize / 2);

        /* No more space
           OR we accidentally move to right half == left is full
           OR right half is over
        */
        return (spaceLeft - requireSpace) < 0
                || ((currentHeapIndexPosition >= (realSize / 2)) && currentHalf < 0)
                || (currentHeapIndexPosition >= realSize);
    }

    public void sweep() {
        currentHeapIndexPosition = (currentHalf < 0 ? (realSize / 2) : 0);
        currentHalf = (currentHeapIndexPosition / (realSize / 4)) - 1;
        heapValues.retainAll(npjGraph.nodes());

        for (HeapValue value : heapValues) {
            value.setHeapIndex(currentHeapIndexPosition);
            currentHeapIndexPosition += value.getSize();
        }
    }

    public int[] getHeap() {
        int[] heap = new int[heapSize];
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

        heap[heapSize - 1] = Integer.MIN_VALUE; //NULL

        return heap;
    }

    public List<HeapValue> getHeapValues() {
        return heapValues;
    }

    public int getNullIndex() {
        return (heapSize - 1);
    }

    @Override
    public String toString() {
        return "HEAP{currentHalf = " + ((currentHalf < 0) ? "left" : "right") + ", " + heapValues.toString() + "}";
    }
}
