package pl.edu.agh.amwj;

import pl.edu.agh.amwj.ast.expression.Expression;
import pl.edu.agh.amwj.ast.expression.VariableExpression;
import pl.edu.agh.amwj.ast.statement.*;
import pl.edu.agh.amwj.tokenizer.Token;
import pl.edu.agh.amwj.tokenizer.TokenType;
import pl.edu.agh.amwj.value.IntegerValue;
import pl.edu.agh.amwj.value.NullValue;
import pl.edu.agh.amwj.value.ObjectType;
import pl.edu.agh.amwj.value.StringValue;

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
     * @return The list of parsed statements.
     */
    public List<Statement> parse() {
        List<Statement> statements = new ArrayList<Statement>();

        while (true) {
            /* LVALUE = RVALUE */
            if (match(TokenType.WORD, TokenType.EQUALS)) {
                String name = last(2).getText();
                Expression value = expression();
                //System.out.println("Assignment statement: " + name + " = [" + value.getClass().getSimpleName() + "] " + value);
                statements.add(new AssignmentStatement(name, value));
                position++;
            }
            /* VarDeclS WORD "STRING"; */
            else if (match(TokenType.S_TYPE, TokenType.WORD, TokenType.STRING, TokenType.SEMICOLON)) {
                ObjectType type = ObjectType.S_TYPE;
                //correct?
                String name = last(3).getText();
                String value = last(2).getText();
                statements.add(new VariableDeclarationStatement(type, name, new StringValue(value)));
//                statements.add(new AssignmentStatement(name, new StringValue(value)));
                //System.out.println("Variable Decl statement: " + type.name() + " " + name + " \"" + value + "\"");
            }
            /* VarDeclS WORD NULL; */
            else if (match(TokenType.S_TYPE, TokenType.WORD, TokenType.NULL, TokenType.SEMICOLON)) {
                ObjectType type = ObjectType.S_TYPE;
                //correct?
                String name = last(3).getText();
                statements.add(new VariableDeclarationStatement(type, name, new NullValue()));
//                statements.add(new AssignmentStatement(name, new StringValue(null)));
                //System.out.println("Variable Decl statement: " + type.name() + " " + name + " NULL");
            }
            /* VarDeclT WORD; */
            else if (match(TokenType.T_TYPE, TokenType.WORD, TokenType.SEMICOLON)) {
                ObjectType type = ObjectType.T_TYPE;
                String name = last(2).getText();
                statements.add(new VariableDeclarationStatement(type, name));
                //System.out.println("Variable Decl statement: " + type.name() + " " + name);
            }
            /* Print sth; */
            else if (match("Print")) {
                //System.out.println("Print statement");
                statements.add(new PrintStatement(expression()));
                position++; //Semicolon
            }
            /* VarDeclT WORD; */
            else if (match(TokenType.WORD, TokenType.SEMICOLON)) {
                String command = last(2).getText();
                if (command.equals("Collect")) {
                    statements.add(new CollectStatement());
                } else if (command.equals("HeapAnalyze")) {
                   statements.add(new HeapAnalyzeStatement());
                }
            }
            else {
                break; // Unexpected token (likely EOF), so end.
            }
        }

        return statements;
    }

    private Expression expression() {
        if (match(TokenType.WORD)) {
            return new VariableExpression(last(1).getText());
        } else if (match(TokenType.INTEGER)) {
            return new IntegerValue(Integer.parseInt(last(1).getText()));
        } else if (match(TokenType.STRING)) {
            return new StringValue(last(1).getText());
        } else if (match(TokenType.NULL)) {
            return new NullValue();
        }
        throw new Error("Couldn't parse :(");
    }

    private boolean match(TokenType type1, TokenType type2, TokenType type3, TokenType type4) {
        if (get(0).getType() != type1) return false;
        if (get(1).getType() != type2) return false;
        if (get(2).getType() != type3) return false;
        if (get(3).getType() != type4) return false;

        position += 4;
        return true;
    }

    private boolean match(TokenType type1, TokenType type2, TokenType type3) {
        if (get(0).getType() != type1) return false;
        if (get(1).getType() != type2) return false;
        if (get(2).getType() != type3) return false;

        position += 3;
        return true;
    }

    private boolean match(TokenType type1, TokenType type2) {
        if (get(0).getType() != type1) return false;
        if (get(1).getType() != type2) return false;

        position += 2;
        return true;
    }

    private boolean match(TokenType type) {
        if (get(0).getType() != type) return false;

        position++;
        return true;
    }

    private boolean match(String name) {
        if (get(0).getType() != TokenType.WORD) return false;
        if (!get(0).getText().equals(name)) return false;
        position++;
        return true;
    }

    private Token consume(TokenType type) {
        if (get(0).getType() != type) throw new Error("Expected " + type + ".");
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