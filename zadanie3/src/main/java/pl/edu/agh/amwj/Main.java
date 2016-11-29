package pl.edu.agh.amwj;

import pl.edu.agh.amwj.exceptions.InvalidHeapSizeException;

import static pl.edu.agh.amwj.utils.FileReader.readFile;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Main {
    public static void main(String[] args) {
        // Just show the usage and quit if a script wasn't provided.
        if (args.length != 1) {
            System.out.println("Usage: main <npj file path>");
            return;
        }
        String path;
        int size;

        path = args[0];
        size = getHeapSize();

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
            NpjInterpreter.interpret(contents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getHeapSize() {
        return Integer.parseInt(System.getProperty("npj.heap.size"));
    }
}
