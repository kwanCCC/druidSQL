parser grammar DruidQuery;

options {
	tokenVocab = DruidLexer;
}

prog
:
	SELECT (quantifiers)? columnList FROM tableRef
	(
		whereClause
	)?
	(
		groupClause
	)?
	(
		orderClause
	)?
	(
		limitClause
	)?
	(
		granularityClause
	)? EOF
;

quantifiers
:
    DISTINCT
;


columnList
:
	nameOperand
	(
		COMMA nameOperand
	)*
;

nameOperand
:
	(
		tableName = ID DOT
	)? columnName = name
	(
		AS alias = ID
	)?
;

name
:
	LPAREN name RPAREN # LRName
	| DISTINCT columnName = name #distinct
	| left = name op = (STAR | SLASH | MOD) right = name # MulName
	| left = name op = (PLUS | SUB) right = name # AddName
	| aggregation = name IF condition = boolExpr # ConditionAggregationName
	| ID LPAREN columnName = name RPAREN # AggregationName
	| identity # columnName
;

identity
:
	ID # idEle
	| INT # intEle
	| FLOAT # floatEle
	| STRING # stringEle
;

tableRef
:
	tableName = ID
	(
		AS alias = ID
	)?
;

whereClause
:
	WHERE timestamps
	(
		AND boolExpr
	)?
;




timestamps
:
	ID BETWEEN left = INT AND right = INT
;

boolExpr
:
	LPAREN boolExpr RPAREN # lrExpr
	| left = boolExpr EQ right = boolExpr # eqOpr
	| left = boolExpr GT right = boolExpr # gtOpr
	| left = boolExpr LT right = boolExpr # ltOpr
	| left = boolExpr GTEQ right = boolExpr #gteqOpr
	| left = boolExpr LTEQ right = boolExpr #lteqOpr
	| left = boolExpr BANGEQ right = boolExpr # notEqOpr
	| left = boolExpr LIKE right = boolExpr #likeOpr
	| left = boolExpr AND right = boolExpr # andOpr
	| left = boolExpr OR right = boolExpr # orOpr
	| inExpr # inBooleanExpr
	| name # nameOpr
;


inExpr
:   left = identity in_or_not_in right = inRightOperandList
;

inRightOperandList
:   inRightOperand
    |LPAREN inRightOperand (COMMA inRightOperand)* RPAREN
;

inRightOperand
:
    const_literal # constLiteral
    |left = inRightOperand op =
                  	(
                  		STAR
                  		| SLASH
                  		| MOD
                  		| PLUS
                  		| MINUS
                  	)
    right = inRightOperand # arithMetricLiteral
;

in_or_not_in
:
    IN # inOp
    | NOT IN # notInOp
;


const_literal
:
	INT # intLiteral
	| FLOAT # floatLiteral
	| STRING # stringLiteral
;



groupClause
:
	GROUP BY name
	(
		COMMA name
	)*
;

orderClause
:
	ORDER BY order
	(
		COMMA order
	)*
;

order
:
	name type =
	(
		ASC
		| DESC
	)?
;

limitClause
:
	LIMIT
	(
		offset = INT (COMMA)?
	)? resultCount = INT
;

granularityClause
:
	SLICE BY granularityExpr
;

granularityExpr
:
	simple =
	(
		ALL
		| NONE
		| MINUTE
		| FIFTEEN_MINUTE
		| THIRTY_MINUTE
		| HOUR
		| DAY
	) # simpleGran
	|
	(
		left = INT TO
	)? right = INT # durationGran
	|
	(
		left = INT TO
	) right = ID AS timeZone = ID # periodGran
;
