package pl.edu.agh.amwj;

import pl.edu.agh.amwj.value.types.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Data {
    public static final int[] heap = new int[128];
    public static final Map<String, Integer> heapmap = new HashMap<String, Integer>();
    public static final Map<String, Value> variables = new HashMap<String, Value>();
    public static int currentStatement;

}
