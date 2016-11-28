package pl.edu.agh.amwj;

import pl.edu.agh.amwj.interpreter.Interpreter;
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

        try {
            Data.initializeData(24);
        } catch (InvalidHeapSizeException e) {
            e.printStackTrace();
            return;
        }

        // Read the file.
        String contents = readFile(args[0]);

        // Run it.
        Interpreter.interpret(contents);
    }
}
