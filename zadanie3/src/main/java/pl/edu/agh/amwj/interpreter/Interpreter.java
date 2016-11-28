package pl.edu.agh.amwj.interpreter;

import pl.edu.agh.amwj.Parser;
import pl.edu.agh.amwj.ast.statement.Statement;
import pl.edu.agh.amwj.tokenizer.Token;
import pl.edu.agh.amwj.tokenizer.Tokenizer;

import java.util.List;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Interpreter {
    public static void interpret(String source) throws Exception {
        // Tokenize.
        List<Token> tokens = Tokenizer.tokenize(source);

        // Parse.
        Parser parser = new Parser(tokens);
        List<Statement> statements = parser.parse();

        // Interpret until we're done.
        for (Statement statement : statements) {
            statement.execute();
        }
    }
}
