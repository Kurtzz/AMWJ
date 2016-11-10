package pl.edu.agh.amwj.interpreter;

import pl.edu.agh.amwj.Parser;
import pl.edu.agh.amwj.Token;
import pl.edu.agh.amwj.Tokenizer;
import pl.edu.agh.amwj.ast.Statement;

import java.util.List;

import static pl.edu.agh.amwj.Data.currentStatement;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Interpreter {
    public static void interpret(String source) {
        // Tokenize.
        List<Token> tokens = Tokenizer.tokenize(source);

        // Parse.
        Parser parser = new Parser(tokens);
        List<Statement> statements = parser.parse();

        // Interpret until we're done.
        currentStatement = 0;
        while (currentStatement < statements.size()) {
            int thisStatement = currentStatement;
            currentStatement++;
            statements.get(thisStatement).execute();
        }
    }
}
