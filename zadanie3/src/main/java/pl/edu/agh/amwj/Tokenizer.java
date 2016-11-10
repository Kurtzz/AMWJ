package pl.edu.agh.amwj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Tokenizer {
    public static List<Token> tokenize(String source) {
        List<Token> tokens = new ArrayList<Token>();

        String token = "";
        TokenizeState state = TokenizeState.DEFAULT;

        // Many tokens are a single character, like operators and ().
        String charTokens = "\n=+-*/<>()";
        TokenType[] tokenTypes = { TokenType.LINE, TokenType.EQUALS,
                TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR,
                TokenType.OPERATOR, TokenType.OPERATOR, TokenType.OPERATOR,
                TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN
        };

        // Scan through the code one character at a time, building up the list
        // of tokens.
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (state) {
                case DEFAULT:
                    if (charTokens.indexOf(c) != -1) {
                        tokens.add(new Token(Character.toString(c), tokenTypes[charTokens.indexOf(c)]));
                    } else if (Character.isLetter(c) && Character.isLowerCase(c)) {
                        token += c;
                        state = TokenizeState.WORD;
                    } else if (Character.isLetter(c) && Character.isUpperCase(c)) {
                        token += c;
                        state = TokenizeState.TYPE;
                    } else if (Character.isDigit(c)) {
                        token += c;
                        state = TokenizeState.NUMBER;
                    } else if (c == '"') {
                        state = TokenizeState.STRING;
                    }
                    break;

                case WORD:
                    if (Character.isLetterOrDigit(c)) {
                        token += c;
                    } else {
                        tokens.add(new Token(token, TokenType.WORD));
                        token = "";
                        state = TokenizeState.DEFAULT;
                        i--; // Reprocess this character in the default state.
                    }
                    break;

                case NUMBER:
                    // HACK: Negative numbers and floating points aren't supported.
                    // To get a negative number, just do 0 - <your number>.
                    // To get a floating point, divide.
                    if (Character.isDigit(c)) {
                        token += c;
                    } else {
                        tokens.add(new Token(token, TokenType.NUMBER));
                        token = "";
                        state = TokenizeState.DEFAULT;
                        i--; // Reprocess this character in the default state.
                    }
                    break;

                case STRING:
                    if (c == '"') {
                        tokens.add(new Token(token, TokenType.STRING));
                        token = "";
                        state = TokenizeState.DEFAULT;
                    } else {
                        token += c;
                    }
                    break;

                case TYPE:
                    if (c == 'S') {
                        tokens.add(new Token(token, TokenType.S_TYPE));
                        token = "";
                        state = TokenizeState.DEFAULT;
                    } else if (c == 'V') {
                        tokens.add(new Token(token, TokenType.V_TYPE));
                        token = "";
                        state = TokenizeState.DEFAULT;
                    } else {
                        token += c;
                    }
                    break;
            }
        }

        return tokens;
    }
}
