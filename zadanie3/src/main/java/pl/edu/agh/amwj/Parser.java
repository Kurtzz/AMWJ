package pl.edu.agh.amwj;

import pl.edu.agh.amwj.ast.expression.Expression;
import pl.edu.agh.amwj.ast.expression.VariableExpression;
import pl.edu.agh.amwj.ast.statement.*;
import pl.edu.agh.amwj.exceptions.InvalidIdentifierException;
import pl.edu.agh.amwj.exceptions.InvalidVariableNameException;
import pl.edu.agh.amwj.tokenizer.Token;
import pl.edu.agh.amwj.tokenizer.TokenType;
import pl.edu.agh.amwj.ast.value.IntegerValue;
import pl.edu.agh.amwj.ast.value.NullValue;
import pl.edu.agh.amwj.ast.value.ObjectType;
import pl.edu.agh.amwj.ast.value.StringValue;

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
    public List<Statement> parse() throws InvalidVariableNameException, InvalidIdentifierException {
        List<Statement> statements = new ArrayList<Statement>();

        while (true) {
            /* LVALUE = RVALUE */
            if (match(TokenType.WORD, TokenType.EQUALS)) {
                String identifier = last(2).getText();
                Expression value = expression();
                if (!validateIdentifier(identifier)) {
                    throw new InvalidIdentifierException(identifier);
                }
                statements.add(new AssignmentStatement(identifier, value));
                position++;
            }
            /* VarDeclS WORD "STRING"; */
            else if (match(TokenType.S_TYPE, TokenType.WORD, TokenType.STRING, TokenType.SEMICOLON)) {
                ObjectType type = ObjectType.S_TYPE;
                String name = last(3).getText();
                String value = last(2).getText();
                if (!validateVariableName(name)) {
                    throw new InvalidVariableNameException(name);
                }
                statements.add(new VariableDeclarationStatement(type, name, new StringValue(value)));
            }
            /* VarDeclS WORD NULL; */
            else if (match(TokenType.S_TYPE, TokenType.WORD, TokenType.NULL, TokenType.SEMICOLON)) {
                ObjectType type = ObjectType.S_TYPE;
                String name = last(3).getText();
                if (!validateVariableName(name)) {
                    throw new InvalidVariableNameException(name);
                }
                statements.add(new VariableDeclarationStatement(type, name, new NullValue()));
            }
            /* VarDeclT WORD; */
            else if (match(TokenType.T_TYPE, TokenType.WORD, TokenType.SEMICOLON)) {
                ObjectType type = ObjectType.T_TYPE;
                String name = last(2).getText();
                if (!validateVariableName(name)) {
                    throw new InvalidVariableNameException(name);
                }
                statements.add(new VariableDeclarationStatement(type, name));
            }
            /* Print sth; */
            else if (match("Print")) {
                statements.add(new PrintStatement(expression()));
                position++; //Semicolon
            }
            /* Collect || HeapAnalyze */
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

    private boolean validateVariableName(String variableName) {
        return variableName.matches("^[a-z][a-z\\d]*$");
    }

    private boolean validateIdentifier(String identifier) {
        return identifier.matches("^[a-z][a-z\\d]*([.][a-z][a-z\\d]+)*$");
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