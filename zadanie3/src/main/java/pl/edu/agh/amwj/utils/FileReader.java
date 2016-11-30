package pl.edu.agh.amwj.utils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class FileReader {
    public static String readFile(String path) {
        try {
            FileInputStream stream = new FileInputStream(path);

            try {
                InputStreamReader input = new InputStreamReader(stream, Charset.defaultCharset());
                Reader reader = new BufferedReader(input);

                StringBuilder builder = new StringBuilder();
                char[] buffer = new char[8192];
                int read;

                while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                    builder.append(buffer, 0, read);
                }

                return builder.toString();
            } finally {
                stream.close();
            }
        } catch (IOException ex) {
            return null;
        }
    }
}
