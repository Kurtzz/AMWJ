package pl.edu.agh.amwj.template;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Interpreter {
    public static void main(String[] args) {
        // make sure we got one and only one parameter
        if (args.length != 1) {
            System.out.println("Wrong number of arguments - pass program file name");
            System.exit(1);
        }

        // get heap size
        int heapSize = getHeapSize();

        // initialize Njp VM with program file name, heap size and GC
        Interpreter interpreter = new Interpreter(args[0], heapSize);
    }

    /**
     * Initializes Njp interpreter with specified program and heap size.
     * 
     * @param programFile
     *            path to program file
     * @param heapSize
     *            heap size
     */
    public Interpreter(String programFile, int heapSize) {
        // get program code stream
        InputStream programStream = getProgramStream(programFile);
    }

    /**
     * Opens program file and returns program code input stream.
     * 
     * @param programFile
     *            path to program file
     * @return input stream for program code
     */
    private InputStream getProgramStream(String programFile) {
        InputStream programStream = null;
        try {
            programStream = new FileInputStream(programFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open program file: " + programFile + ", exiting.");
            System.exit(2);
        }

        return programStream;
    }

	private static int getHeapSize() {
		return 4; //dummy implementation
	}
}
