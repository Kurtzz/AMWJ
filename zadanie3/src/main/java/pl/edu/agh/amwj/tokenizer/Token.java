package pl.edu.agh.amwj.tokenizer;

/**
 * Created by Kurtzz on 2016-11-10.
 */
public class Token {
    private final String text;
    private final TokenType type;

    public Token(String text, TokenType type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public TokenType getType() {
        return type;
    }
}
