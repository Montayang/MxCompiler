grammar MxStar;

//Parser

program : (subProgram)* EOF;

suite : '{' statement* '}';

subProgram : varDef | classDef | funcDef;

varDef : varType singleInit (',' singleInit)* ';';

classDef : CLASS IDENTIFIER '{' (funcDef | varDef)* '}' ';' ;

funcDef : funcType? IDENTIFIER '(' parList? ')' suite ;

funcType: VOID | varType ;

varType
    : varType '['']'           #arrayType
    | baseType                 #baseVarType
;

baseType
    : INT
    | BOOL
    | STRING
    | IDENTIFIER
;

parList : singleVarDef (',' singleVarDef)* ;

singleVarDef : varType IDENTIFIER;

singleInit : IDENTIFIER ('=' expression)?;

statement
    : suite                                                      #block
    | varDef                                                     #vardefStmt
    | IF '(' expression ')' thenStmt=statement (ELSE elseStmt=statement)?  #ifStmt
    | FOR '(' (initDef =varDef | initExpr = expression)? ';' condExpr = expression? ';' stepExpr = expression? ')' statement  #forStmt
    | WHILE '(' expression ')' statement                         #whileStmt
    | RETURN expression? ';'                                     #returnStmt
    | CONTINUE ';'                                               #continueStmt
    | BREAK ';'                                                  #breakStmt
    | expression ';'                                             #pureExprStmt
    | ';'                                                        #emptyStmt
    ;

expression
    : '('expression')'                                           #subExpr
    | IDENTIFIER                                                 #idExpr
    | THIS                                                       #thisExpr
    | literal                                                    #constExpr
    | <assoc=right> NEW newType                                  #newExpr
    | expression '.' IDENTIFIER                                  #memberAccExpr
    | expression '(' exprList? ')'                               #funcCallExpr
    | array=expression '[' index = expression ']'                #arrayAccExpr
    | expression op=('++' | '--')                                #selfExpr
    | <assoc=right> op=('++' | '--') expression                  #unaryExpr
    | <assoc=right> op=('!' | '~') expression                    #unaryExpr
    | <assoc=right> op=('+' | '-') expression                    #unaryExpr
    | exprL=expression op=('*' | '/' | '%') exprR=expression     #binaryExpr
    | exprL=expression op=('+' | '-') exprR=expression           #binaryExpr
    | exprL=expression op=('<<' | '>>') exprR=expression         #binaryExpr
    | exprL=expression op=('>' | '<' | '>=' | '<=' | '==' | '!=' ) exprR=expression               #binaryExpr
    | exprL=expression op='&' exprR=expression                   #binaryExpr
    | exprL=expression op='|' exprR=expression                   #binaryExpr
    | exprL=expression op='^' exprR=expression                   #binaryExpr
    | exprL=expression op='&&' exprR=expression                  #binaryExpr
    | exprL=expression op='||' exprR=expression                  #binaryExpr
    | <assoc=right> exprL=expression '=' exprR=expression        #assignExpr
    | '[&]' ('('parList?')')? '->' suite '('expression (',' expression)*')' #lambdaExpr
    ;

newType
    : baseType ('[' expression ']')*('['']')+('[' expression ']')+('[' expression? ']')* #newErrorType
    | baseType ('[' expression? ']')+                            #newArrayType
    | baseType ('(' ')')?                                        #newBaseType
;

exprList : expression (',' expression)* ;

literal
    : NULL_CONST
    | INTERGER_CONST
    | STRING_CONST
    | BOOL_CONST
    ;

//Lexer

INT : 'int';
BOOL : 'bool';
STRING : 'string';
//NULL : 'null';
//TRUE : 'true';
//FALSE : 'false';
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
BOOL_CONST: 'true' | 'false';
STRING_CONST: '"' ('\\n' | '\\\\' | '\\"' | .)*? '"';
NULL_CONST: 'null';

IDENTIFIER : [a-zA-Z] [a-zA-Z0-9_]* ;
WHITESPACE : [ \t]+ -> skip ;
NEWLINE : ('\r' ? '\n' | '\r') -> skip ;

BLOCK_COMMENT : '/*' .*? '*/' -> skip ;
LINE_COMMENT : '//' ~[\r\n]* -> skip ;