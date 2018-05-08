package org.dora.jdbc.grammar;

/**
 * Created by SDE on 2018/5/8.
 */
public class Utils {
    public static String underlineError(String fullText, String symbolText, int line, int charPositionInLine) {
        StringBuilder buffer = new StringBuilder("\n");
        String[] lines = fullText.split("\n");
        String errorLine = lines[line - 1];
        buffer.append(errorLine);
        buffer.append("\n");

        for (int i = 0; i < charPositionInLine; i++) {
            char c = errorLine.charAt(i);
            if (Character.isWhitespace(c)) {
                buffer.append(c);
            } else {
                buffer.append(" ");
            }
        }

        for (int i = 0; i < symbolText.length(); i++) { buffer.append("^"); }

        buffer.append("\n");
        return buffer.toString();
    }
}
