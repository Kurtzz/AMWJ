package pl.edu.agh.amwj;

import pl.edu.agh.amwj.exceptions.InvalidHeapSizeException;
import pl.edu.agh.amwj.interpreter.Interpreter;

import static pl.edu.agh.amwj.utils.FileReader.readFile;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Main {
    public static void main(String[] args) {
        // Just show the usage and quit if a script wasn't provided.
        if (args.length != 2) {
            System.out.println("Usage: main -Dnpj.heap.size=<size> <npj file path>");
            return;
        }
        String path;
        int size;

        if (args[0].startsWith("-Dnpj.heap.size")) {
            size = Integer.parseInt(args[0].split("=")[1]);
            path = args[1];
        } else {
            size = Integer.parseInt(args[1].split("=")[1]);
            path = args[0];
        }

        try {
            Data.initializeData(size);
        } catch (InvalidHeapSizeException e) {
            e.printStackTrace();
            return;
        }

        // Read the file.
        String contents = readFile(path);

        // Run it.
        try {
            Interpreter.interpret(contents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
