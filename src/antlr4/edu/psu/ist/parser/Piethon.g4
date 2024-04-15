grammar Piethon;

script
    :   def* EOF
    ;

def :   'def' name=ID '(' paramList? ')' ':' retType=ty 'is'
        varDefs=varDef*
        stmts=statement*
        'end'
    ;

varDef
    :   'var' name=ID ':' type=ty ':=' exp ';'
    ;

statement
    :   name=ID ':=' exp ';'            #assignStmt
    |   name=ID '(' expList? ')' ';'    #callStmt
    |   'return' exp ';'                #returnStmt
    ;

paramList
    :   paramDef (',' paramDef)*
    ;

paramDef
    :   name=ID ':' ty
    ;

expList
    :   exp (',' exp)*
    ;

exp :   left=exp '+' right=exp          #addExp
    |   name=ID                         #varRefExp
    |   'true'                          #trueExp
    |   'false'                         #falseExp
    |   n=NUM                           #intExp
    ;

ty  :   'Bool'              #boolTy
    |   'Int32'             #intTy
    |   'Void'              #voidTy
    ;

NUM : [0-9]+;

COMMENT
    :   ( '//' ~[\r\n]* | '/*' .*? '*/' ) -> skip
    ;

ID  : [a-zA-Z_] [a-zA-Z_0-9]* ;
STRING : '"' (ESC | ~["\\])* '"' ;
fragment ESC : '\\' ["\bfnrt] ;
WS : [ \t\n\r]+ -> channel(HIDDEN) ;