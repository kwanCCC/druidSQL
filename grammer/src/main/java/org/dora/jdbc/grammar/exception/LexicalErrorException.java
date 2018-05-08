package org.dora.jdbc.grammar.exception;


public class LexicalErrorException extends ParseException {

    private static final long serialVersionUID = 4887916123720569042L;

    public LexicalErrorException(String msg) {
        super(msg);
    }

    public LexicalErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
