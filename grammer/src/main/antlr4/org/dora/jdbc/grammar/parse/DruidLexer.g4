lexer grammar DruidLexer;

//error handler @see: antlr4
@lexer::members {
	public void notifyListeners(LexerNoViableAltException e) {
		String text = _input.getText(Interval.of(_tokenStartCharIndex, _input.index()));		
		ANTLRErrorListener listener = getErrorListenerDispatch();
		listener.syntaxError(this, null, _tokenStartLine, _tokenStartCharPositionInLine, text, e);
	}
}

SELECT:S E L E C T;  
DELETE:D E L E T E;
INSERT:I N S E R T;
UPDATE:U P D A T E;

FROM:F R O M;
HAVING:H A V I N G;
WHERE:W H E R E;
ORDER:O R D E R;
BY:B Y;  
GROUP:G R O U P;
INTO:I N T O;
AS:A S;
SOUNDS:S O U N D S;
REGEXP:R E G E X P;

CREATE:C R E A T E;
ALTER:A L T E R;
DROP:D R O P; 
SET:S E T;

NULL:N U L L;
NOT:N O T | '!';
DISTINCT:D I S T I N C T;
DISTINCTROW:D I S T I N C T R O W;


TABLE:T A B L E; 
TABLESPACE:T A B L E S P A C E;
VIEW:V I E W;
SEQUENCE:S E Q U E N C E;
TRIGGER:T R I G G E R;
USER:U S E R;
PROCEDURE:P R O C E D U R E;
FUNCTION:F U N C T I O N;

PRIMARY:P R I M A R Y;
KEY:K E Y;
DEFAULT:D E F A U L T; 
CONSTRAINT:C O N S T R A I N T; 
CHECK:C H E C K; 
UNIQUE:U N I Q U E; 
FOREIGN:F O R E I G N; 
REFERENCES:R E F E R E N C E S; 

EXPLAIN:E X P L A I N; 
FOR:F O R; 
IF:I F; 



ALL:A L L; 
UNION:U N I O N; 
EXCEPT:E X C E P T; 
INTERSECT:I N T E R S E C T; 
MINUS:M I N U S;
INNER:I N N E R; 
LEFT:L E F T; 
RIGHT:R I G H T; 
FULL:F U L L; 
OUTER:O U T E R; 
JOIN:J O I N; 
ON:O N; 
SCHEMA:S C H E M A; 
CAST:C A S T;
COLUMN:C O L U M N;
USE:U S E;
DATABASE:D A T A B A S E;
TO:T O;

AND:A N D | '&&'; 
OR:O R | '||'; 
XOR:X O R; 
CASE:C A S E; 
WHEN:W H E N; 
THEN:T H E N; 
ELSE:E L S E; 
END:E N D; 
EXISTS:E X I S T S; 
IN:I N;

NEW:N E W; 
ASC:A S C; 
DESC:D E S C; 
IS:I S; 
LIKE:L I K E; 
ESCAPE:E S C A P E; 
BETWEEN:B E T W E E N; 
VALUES:V A L U E S; 
INTERVAL:I N T E R V A L;


// mysql
TRUE:T R U E; 
FALSE:F A L S E;
LIMIT:L I M I T;

SLICE:S L I C E;

NONE:N O N E;
MINUTE:M I N U T E;
FIFTEEN_MINUTE:F I F T E E N UNDERLINE M I N U T E;
THIRTY_MINUTE:T H I R T Y UNDERLINE M I N U T E;
HOUR:H O U R;
DAY:D A Y;


LPAREN:'('; 
RPAREN:')'; 
LBRACE:'{'; 
RBRACE:'}'; 
LBRACKET:'[';
RBRACKET:']'; 
SEMI:';'; 
COMMA:','; 
DOT:'.'; 
DOTDOT:'..'; 
DOTDOTDOT:'..,'; 
EQ:'='; 
GT:'>'; 
LT:'<';
BANG:'!';
BANGBANG:'!!';
TILDE:'~'; 
QUES:'?'; 
COLON:':'; 
COLONCOLON:':'; 
COLONEQ:':='; 
EQEQ:'=='; 
LTEQ:'<='; 
LTEQGT:'<=>';  
// LTGT:'<>';
GTEQ:'>='; 
BANGEQ:'<>';
BANGGT:'!>'; 
BANGLT:'!<';
AMPAMP:'&&'; 
BARBAR:'||'; 
BARBARSLASH:'||/'; 
BARSLASH:'|/'; 
PLUS:'+'; 
SUB:'-'; 
STAR:'*'; 
SLASH:'/';
AMP:'&'; 
BAR:'|'; 
CARET:'^'; 
PERCENT:'%'; 
LTLT:'<<'; 
GTGT:'>>';
MONKEYS_AT:'@';
POUND:'#';
DIV:D I V;
MOD:'%';

UNDERLINE:'_';
QUOTES:'\"'|'\'';

INT : ('-')? DIGIT+;
FLOAT :DIGIT+ '.' DIGIT* | '.' DIGIT+;

STRING:QUOTES ( EscapeSequence | ~('\\'|'"'|'\n'|'\r'|'\'') )* QUOTES;


ID: ID_LETTER (ID_LETTER | DIGIT)*;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;
    
fragment HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment UnicodeEscape:   '\\' 'u' HexDigit HexDigit HexDigit HexDigit;

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment ID_LETTER: [a-zA-Z] | '_';
fragment DIGIT: [0-9];
fragment A: [aA];
fragment B: [bB];
fragment C: [cC];
fragment D: [dD];
fragment E: [eE];
fragment F: [fF];
fragment G: [gG];
fragment H: [hH];
fragment I: [iI];
fragment J: [jJ];
fragment K: [kK];
fragment L: [lL];
fragment M: [mM];
fragment N: [nN];
fragment O: [oO];
fragment P: [pP];
fragment Q: [qQ];
fragment R: [rR];
fragment S: [sS];
fragment T: [tT];
fragment U: [uU];
fragment V: [vV];
fragment W: [wW];
fragment X: [xX];
fragment Y: [yY];
fragment Z: [zZ];


WS  :   [ \r\n\t\u000C]+ -> channel(HIDDEN);







