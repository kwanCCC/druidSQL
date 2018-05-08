package org.dora.jdbc.grammar.exception;


public class ParseException extends RuntimeException {

    private static final long serialVersionUID = 1134808507717143482L;

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
