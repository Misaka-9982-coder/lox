%{
#include <stdio.h>
%}

/* Token Definitions */
%token CLASS FUN VAR PRINT IF ELSE FOR WHILE RETURN TRUE FALSE NIL THIS SUPER IDENTIFIER NUMBER STRING EOF

%token ASSIGN
%token NOT_EQUAL EQUAL LESS GREATER LESS_EQUAL GREATER_EQUAL ADD SUB MUL DIV NOT UMINUS

%right ASSIGN
%left OR
%left AND
%left NOT_EQUAL EQUAL
%left LESS LESS_EQUAL GREATER GREATER_EQUAL
%left ADD SUB
%left MUL DIV
%left NOT
%right UMINUS

%nonassoc ELSE

%%

program: declarations EOF ;

declarations: /* empty */
           | declarations declaration ;

declaration: classDecl
           | funDecl
           | varDecl
           | statement ;

classDecl: CLASS IDENTIFIER opt_inheritance '{' functions '}' ;

opt_inheritance: /* empty */
               | '<' IDENTIFIER ;

functions: /* empty */
         | functions function ;

varDecl: VAR IDENTIFIER opt_assignment ';' ;

opt_assignment: /* empty */
              | ASSIGN expression ;

funDecl: FUN function ;

function: IDENTIFIER '(' opt_parameters ')' block ;

opt_parameters: /* empty */
              | parameters ;

parameters: IDENTIFIER
          | parameters ',' IDENTIFIER ;

statement: exprStmt
         | forStmt
         | ifStmt
         | printStmt
         | returnStmt
         | whileStmt
         | block ;

exprStmt: expression ';' ;

forStmt: FOR '(' opt_varOrExprStmt ';' opt_expression ';' opt_expression ')' statement ;

opt_varOrExprStmt: /* empty */
                | varDecl
                | exprStmt ;

opt_expression: /* empty */
              | expression ;

ifStmt: IF '(' expression ')' statement %prec ELSE
      | IF '(' expression ')' statement ELSE statement ;

printStmt: PRINT expression ';' ;

returnStmt: RETURN opt_expression ';' ;

whileStmt: WHILE '(' expression ')' statement ;

block: '{' declarations '}' ;

arguments: expression
         | arguments ',' expression ;

expression: assignment ;

assignment: IDENTIFIER ASSIGN assignment
          | call '.' IDENTIFIER ASSIGN assignment
          | logic_or ;

logic_or: logic_and
        | logic_or OR logic_and ;

logic_and: equality
         | logic_and AND equality ;

equality: comparison
        | equality NOT_EQUAL comparison
        | equality EQUAL comparison ;

comparison: term
          | comparison LESS term
          | comparison LESS_EQUAL term
          | comparison GREATER term
          | comparison GREATER_EQUAL term ;

term: factor
    | term ADD factor
    | term SUB factor ;

factor: unary
      | factor MUL unary
      | factor DIV unary ;

unary: NOT unary
     | SUB unary %prec UMINUS
     | call ;

call: primary
    | call '(' opt_arguments ')'
    | call '.' IDENTIFIER ;

opt_arguments: /* empty */
             | arguments ;

primary: TRUE
       | FALSE
       | NIL
       | THIS
       | NUMBER
       | STRING
       | IDENTIFIER
       | '(' expression ')'
       | SUPER '.' IDENTIFIER ;

%%

int main() {
    return yyparse();
}

void yyerror(const char *s) {
    fprintf(stderr, "Error: %s\n", s);
}
