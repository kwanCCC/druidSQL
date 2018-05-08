package org.dora.jdbc.grammar.exception;

public class SyntaxErrorException extends ParseException {

    private static final long serialVersionUID = 7844580751442638660L;

    public SyntaxErrorException(String msg) {
        super(msg);
    }

    public SyntaxErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
