package pl.edu.agh.amwj;

import pl.edu.agh.amwj.value.types.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Comarch on 2016-11-10.
 */
public class Data {
    public static final Map<String, Value> variables = new HashMap<String, Value>();
    public static final Map<String, Integer> labels = new HashMap<String, Integer>();
    public static InputStreamReader converter = new InputStreamReader(System.in);
    public static final BufferedReader lineIn = new BufferedReader(converter);
    public static int currentStatement;

}
