grammar MxStar;

//Parser

program : (subProgram)* EOF;

suite : '{' statement* '}';

subProgram : varDef | classDef | funcDef;

varDef : varType IDENTIFIER ('=' expression)? (',' IDENTIFIER ('=' expression)?)* ';';

classDef : CLASS IDENTIFIER '{' (funcDef | varDef)* '}' ';' ;

funcDef : funcType? IDENTIFIER '(' parList? ')' suite ;

funcType: VOID | varType ;

varType
    : varType '['']'
    | baseType
;

baseType
    : INT
    | BOOL
    | STRING
    | IDENTIFIER
;

parList : varType IDENTIFIER (',' varType IDENTIFIER)* ;

statement
    : suite                                                      #block
    | varDef                                                     #vardefStmt
    | IF '(' expression ')' thenStmt=statement (ELSE elseStmt=statement)?  #ifStmt
    | FOR '(' initExp = expression? ';' condExp = expression? ';' stepExp = expression? ')' statement  #forStmt
    | WHILE '(' expression ')' statement                         #whileStmt
    | RETURN expression? ';'                                     #returnStmt
    | CONTINUE ';'                                               #continueStmt
    | BREAK ';'                                                  #breakStmt
    | expression ';'                                             #pureExprStmt
    | ';'                                                        #emptyStmt
    ;

expression
    : primary                                                    #atomExpr
    | <assoc=right> NEW newType                                  #newExpr
    | expression '.' IDENTIFIER                                  #memberAcc
    | expression '(' exprList? ')'                               #funccal
    | arr=expression '[' index = expression ']'                  #arraydefExpr
    | expression op=('++' | '--')                                #selfExpr
    | <assoc=right> op=('++' | '--') expression                  #unaryExpr
    | <assoc=right> op=('!' | '~') expression                    #unaryExpr
    | <assoc=right> op=('+' | '-') expression                    #unaryExpr
    | exprl=expression op=('*' | '/' | '%') exprr=expression     #binaryExpr
    | exprl=expression op=('+' | '-') exprr=expression           #binaryExpr
    | exprl=expression op=('<<' | '>>') exprr=expression         #binaryExpr
    | exprl=expression op=('>' | '<' | '>=' | '<=' | '==' | '!=' ) exprr=expression               #binaryExpr
    | exprl=expression op='&' exprr=expression                   #binaryExpr
    | exprl=expression op='|' exprr=expression                   #binaryExpr
    | exprl=expression op='^' exprr=expression                   #binaryExpr
    | exprl=expression op='&&' exprr=expression                  #binaryExpr
    | exprl=expression op='||' exprr=expression                  #binaryExpr
    | <assoc=right> exprl=expression '=' exprr=expression        #assignExpr
    | '[&]' ('('parList?')')? '->' suite '('expression (',' expression)*')' #lambdaExpr
    ;

newType
    : baseType ('[' expression ']')*('['']')+('[' expression ']')+('[' expression? ']')* #newErrorType
    | baseType ('[' expression? ']')+                            #newArrayType
    | baseType ('(' ')')?                                        #newBaseType
;

exprList : expression (',' expression)* ;

primary
    : '('expression')'
    | IDENTIFIER
    | THIS
    | literal
    ;

literal
    : INTERGER_CONST
    | STRING_CONST
    | NULL_CONST
    | BOOL_CONST
    ;

//Lexer

INT : 'int';
BOOL : 'bool';
STRING : 'string';
NULL : 'null';
TRUE : 'true';
FALSE : 'false';
IF : 'if';
ELSE : 'else';
FOR : 'for';
WHILE : 'while';
RETURN : 'return';
CONTINUE : 'continue';
BREAK : 'break';
CLASS : 'class';
VOID : 'void';
THIS : 'this';
NEW : 'new';

INTERGER_CONST: '0' | [1-9][0-9]*;
BOOL_CONST: TRUE | FALSE;
STRING_CONST: '"' ('\\n' | '\\\\' | '\\"' | .)*? '"';
NULL_CONST: NULL;

IDENTIFIER : [a-zA-Z] [a-zA-Z0-9_]* ;
WHITESPACE : [ \t]+ -> skip ;
NEWLINE : ('\r' ? '\n' | '\r') -> skip ;

BLOCK_COMMENT : '/*' .*? '*/' -> skip ;
LINE_COMMENT : '//' ~[\r\n]* -> skip ;