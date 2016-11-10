package pl.edu.agh.amwj;

import pl.edu.agh.amwj.ast.*;
import pl.edu.agh.amwj.value.types.IntegerValue;
import pl.edu.agh.amwj.value.types.StringValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Parser {
    private final List<Token> tokens;
    private int position;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        position = 0;
    }

    /**
     * The top-level function to start parsing. This will keep consuming
     * tokens and routing to the other parse functions for the different
     * grammar syntax until we run out of code to parse.
     *
     * @param labels A map of label names to statement indexes. The
     *               parser will fill this in as it scans the code.
     * @return The list of parsed statements.
     */
    public List<Statement> parse() {
        List<Statement> statements = new ArrayList<Statement>();

        while (true) {
            // Ignore empty lines.
            while (match(TokenType.LINE));

            if (match(TokenType.WORD, TokenType.EQUALS)) {
                String name = last(2).text;
                Expression value = expression();
                statements.add(new AssignStatement(name, value));
            } else if (match("print")) {
                statements.add(new PrintStatement(expression()));
            } else {
                break; // Unexpected token (likely EOF), so end.
            }
        }

        return statements;
    }

    private Expression expression() {
        return operator();
    }

    private Expression operator() {
        Expression expression = atomic();

        // Keep building operator expressions as long as we have operators.
        while (match(TokenType.OPERATOR) ||
                match(TokenType.EQUALS)) {
            char operator = last(1).text.charAt(0);
            Expression right = atomic();
            expression = new OperatorExpression(expression, operator, right);
        }

        return expression;
    }

    private Expression atomic() {
        if (match(TokenType.WORD)) {
            // A word is a reference to a variable.
            return new VariableExpression(last(1).text);
        } else if (match(TokenType.NUMBER)) {
            return new IntegerValue(Double.parseDouble(last(1).text));
        } else if (match(TokenType.STRING)) {
            return new StringValue(last(1).text);
        } else if (match(TokenType.LEFT_PAREN)) {
            // The contents of a parenthesized expression can be any
            // expression. This lets us "restart" the precedence cascade
            // so that you can have a lower precedence expression inside
            // the parentheses.
            Expression expression = expression();
            consume(TokenType.RIGHT_PAREN);
            return expression;
        }
        throw new Error("Couldn't parse :(");
    }

    private boolean match(TokenType type1, TokenType type2) {
        if (get(0).type != type1) return false;
        if (get(1).type != type2) return false;
        position += 2;
        return true;
    }

    private boolean match(TokenType type) {
        if (get(0).type != type) return false;
        position++;
        return true;
    }

    private boolean match(String name) {
        if (get(0).type != TokenType.WORD) return false;
        if (!get(0).text.equals(name)) return false;
        position++;
        return true;
    }

    private Token consume(TokenType type) {
        if (get(0).type != type) throw new Error("Expected " + type + ".");
        return tokens.get(position++);
    }

    private Token consume(String name) {
        if (!match(name)) throw new Error("Expected " + name + ".");
        return last(1);
    }

    private Token last(int offset) {
        return tokens.get(position - offset);
    }

    private Token get(int offset) {
        if (position + offset >= tokens.size()) {
            return new Token("", TokenType.EOF);
        }
        return tokens.get(position + offset);
    }
}