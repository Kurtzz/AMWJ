package pl.edu.agh.amwj.utils;

import pl.edu.agh.amwj.value.StringValue;
import pl.edu.agh.amwj.value.TValue;

import static pl.edu.agh.amwj.Constants.S_HEADER;
import static pl.edu.agh.amwj.Constants.T_HEADER;

/**
 * Created by P on 27.11.2016.
 */
public class MyHeap {
    private int[] heap;
    private int currentPosition;

    public MyHeap(int size) {
        heap = new int[size];
        currentPosition = 0;
    }

    public void allocateTValue() {
        heap[currentPosition] = T_HEADER;
        heap[++currentPosition] = 0; //f1 NULL
        heap[++currentPosition] = 0; //f2 NULL
        heap[++currentPosition] = 0; //data 0
        currentPosition++;
    }

    public void allocateSValue(StringValue value) {
        heap[currentPosition] = S_HEADER; //header

        if (value == null) {
            heap[++currentPosition] = 0; //length 0
            heap[++currentPosition] = 0; //nothing
            currentPosition++;

            return;
        }

        //NOT NULL
        heap[++currentPosition] = value.toString().length(); //length
        char[] contentArray = value.toString().toCharArray();
        currentPosition++; //data position

        for (char aContentArray : contentArray) {
            heap[currentPosition++] = (int)aContentArray;
        }
    }

    public void updateTValue(TValue value) {
        int index = value.getHeapIndex();
        if (value.getF1() != null) {
            heap[index + 1] = value.getF1().getHeapIndex();
        }
        if (value.getF2() != null) {
            heap[index + 2] = value.getF2().getHeapIndex();
        }
        heap[index + 3] = value.getData().toNumber();
    }

    public int[] getHeap() {
        return heap;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void print() {
        System.out.print("Heap{");
        for (int i = 0; i < heap.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            if (heap[i] == T_HEADER) {
                System.out.print(i + ": ");
                System.out.print("TVALUE: [" +
                        heap[i] + ", " +
                        heap[++i] + ", " +
                        heap[++i] + ", " +
                        heap[++i] + "]"
                );
            } else if (heap[i] == S_HEADER) {
                System.out.print(i + ": ");
                System.out.print("SVALUE: [" +
                        heap[i] + ", " +
                        heap[++i] + ", \""
                );

                int maxIndex = heap[i] + (++i);
                while(i < maxIndex) {
                    System.out.print((char)heap[i++]);
                }
                System.out.print("\"]"); i--;
            }
        }
    }
}
