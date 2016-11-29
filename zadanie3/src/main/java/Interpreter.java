import pl.edu.agh.amwj.Data;
import pl.edu.agh.amwj.exceptions.InvalidHeapSizeException;
import pl.edu.agh.amwj.NpjInterpreter;

import static pl.edu.agh.amwj.utils.FileReader.readFile;

public class Interpreter {
    public static void main(String[] args) {
        // make sure we got one and only one parameter
        if (args.length != 1) {
            System.out.println("Wrong number of arguments - pass program file name");
            System.exit(1);
        }

        // get heap size
        int heapSize = getHeapSize();

        //npj file path
        String path = args[0];

        // initialize Njp VM with program file name, heap size and GC
        try {
            Data.initializeData(heapSize);
        } catch (InvalidHeapSizeException e) {
            System.out.println(e.getMessage());
            System.exit(3);
        }

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
