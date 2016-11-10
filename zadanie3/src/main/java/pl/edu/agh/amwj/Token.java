package pl.edu.agh.amwj;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Token {
    public final String text;
    public final TokenType type;

    public Token(String text, TokenType type) {
        this.text = text;
        this.type = type;
    }
}
