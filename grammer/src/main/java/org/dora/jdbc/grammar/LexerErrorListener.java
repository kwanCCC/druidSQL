package org.dora.jdbc.grammar;

import java.util.BitSet;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.dora.jdbc.grammar.exception.LexicalErrorException;

/**
 * Created by SDE on 2018/5/8.
 */
public class LexerErrorListener implements org.antlr.v4.runtime.ANTLRErrorListener {


    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        String position = "line " + line + ", pos " + charPositionInLine;

        String charText = "";
        String hint = "";
        if (recognizer != null && recognizer instanceof Lexer) {
            Lexer lexer = (Lexer)recognizer;
            String fullText = lexer.getInputStream().toString();
            charText = fullText.charAt(lexer.getCharIndex()) + "";
            hint = Utils.underlineError(fullText, charText, line, charPositionInLine);
        }
        throw new LexicalErrorException(position + " near " + charText + " : " + msg + hint, e);
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact,
                                BitSet ambigAlts, ATNConfigSet configs) {

    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex,
                                            BitSet conflictingAlts, ATNConfigSet configs) {

    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction,
                                         ATNConfigSet configs) {

    }
}
