package pl.edu.agh.amwj.tokenizer;

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
        String charTokens = "=;";
        TokenType[] tokenTypes = { TokenType.EQUALS, TokenType.SEMICOLON};

        // Scan through the code one character at a time, building up the list
        // of tokens.
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (state) {
                case DEFAULT:
                    if (charTokens.indexOf(c) != -1) {
                        //System.out.println("Add token: " + Character.toString(c));
                        tokens.add(new Token(Character.toString(c), tokenTypes[charTokens.indexOf(c)]));
                    } else if (Character.isLetter(c) && c == 'V') {
                        token += c;
                        state = TokenizeState.TYPE;
                    } else if (Character.isLetter(c)) {
                        token += c;
                        state = TokenizeState.WORD;
                    } else if (Character.isDigit(c)) {
                        token += c;
                        state = TokenizeState.INTEGER;
                    } else if (c == '"') {
                        state = TokenizeState.STRING;
                    }
                    break;

                case WORD:
                    if (Character.isLetterOrDigit(c) || c == '.') {
                        token += c;
                    } else if (token.equals("NULL")) {
                        //System.out.println("Add token: " + token);
                        tokens.add(new Token(token, TokenType.NULL));
                        token = "";
                        state = TokenizeState.DEFAULT;
                        i--; // Reprocess this character in the default state.
                    } else {
                        //System.out.println("Add token: " + token);
                        tokens.add(new Token(token, TokenType.WORD));
                        token = "";
                        state = TokenizeState.DEFAULT;
                        i--; // Reprocess this character in the default state.
                    }
                    break;

                case INTEGER:
                    if (Character.isDigit(c)) {
                        token += c;
                    } else {
                        //System.out.println("Add token: " + token);
                        tokens.add(new Token(token, TokenType.INTEGER));
                        token = "";
                        state = TokenizeState.DEFAULT;
                        i--; // Reprocess this character in the default state.
                    }
                    break;

                case STRING:
                    if (c == '"') {
                        //System.out.println("Add token: \"" + token + "\"");
                        tokens.add(new Token(token, TokenType.STRING));
                        token = "";
                        state = TokenizeState.DEFAULT;
                    } else {
                        token += c;
                    }
                    break;

                case TYPE:
                    if (c == 'S') {
                        token += c;
                        //System.out.println("Add token: " + token);
                        tokens.add(new Token(token, TokenType.S_TYPE));
                        token = "";
                        state = TokenizeState.DEFAULT;
                    } else if (c == 'T') {
                        token += c;
                        //System.out.println("Add token: " + token);
                        tokens.add(new Token(token, TokenType.T_TYPE));
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
