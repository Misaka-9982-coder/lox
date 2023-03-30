from lark import Lark, Transformer, v_args
from lark.tree import pydot__tree_to_png


grammar = """
start: program

program: declaration*

declaration: class_decl
           | fun_decl
           | var_decl
           | statement

class_decl: "class" IDENTIFIER ("<" IDENTIFIER)? "{" function* "}"

var_decl: "var" IDENTIFIER ("=" expression)? ";"

fun_decl: "fun" function

function: IDENTIFIER "(" parameters? ")" block

parameters: IDENTIFIER ("," IDENTIFIER)*

statement: expr_stmt
         | for_stmt
         | if_stmt
         | print_stmt
         | return_stmt
         | while_stmt
         | block

expr_stmt: expression ";"

for_stmt: "for" "(" (var_decl | expr_stmt | ";") expression? ";" expression? ")" statement

if_stmt: "if" "(" expression ")" statement ("else" statement)?

print_stmt: "print" expression ";"

return_stmt: "return" expression? ";"

while_stmt: "while" "(" expression ")" statement

block: "{" declaration* "}"

arguments: expression ("," expression)*

expression: assignment

assignment: (call ".")? IDENTIFIER "=" assignment
          | logic_or

logic_or: logic_and ("or" logic_and)*

logic_and: equality ("and" equality)*

equality: comparison (("!=" | "==") comparison)*

comparison: term ((">" | ">=" | "<" | "<=") term)*

term: factor (("-" | "+") factor)*

factor: unary (("/" | "*") unary)*

unary: ("!" | "-") unary
     | call

call: primary ("(" arguments? ")" | "." IDENTIFIER)*

primary: "true"
       | "false"
       | "nil"
       | "this"
       | NUMBER
       | STRING
       | IDENTIFIER
       | "(" expression ")"
       | "super" "." IDENTIFIER

%import common.CNAME -> IDENTIFIER
%import common.NUMBER
%import common.ESCAPED_STRING -> STRING
%import common.WS_INLINE
%ignore WS_INLINE

"""

parser = Lark(grammar, start="start", parser="lalr")


source_code = """
1+2;
"""


# 用 '\n' 分隔字符串并去掉每个部分的前后空格
lines = [line.strip() for line in source_code.split('\n')]

# 删除每行中的 "//" 以及其后面的内容
stripped_lines = [line.split('//')[0] for line in lines]

# 将所有行合并为单个字符串，并用分号分隔它们
processed_source_code = ' '.join(stripped_lines)

# 输出结果
print(processed_source_code)


tree = parser.parse(processed_source_code.strip())
print(tree)
pydot__tree_to_png(tree, 'output.png')
