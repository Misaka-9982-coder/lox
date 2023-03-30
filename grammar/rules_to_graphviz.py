from lark import Lark, Transformer, v_args
from lark.tree import pydot__tree_to_png
from graphviz import Digraph
from collections import defaultdict

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

def rules_to_graphviz(parser):
    dot = Digraph(comment='Lark Grammar', format='png')
    # dot.attr(rankdir='LR')
    dot.attr(margin='0.2')
    # dot.attr(nodesep='0.5')
    dot.attr(ranksep='1.0')

    # 使用 defaultdict 初始化一个字典，其默认值为列表
    rule_dict = defaultdict(set)
    rules = set()
    edges = set()

    for rule in parser.rules:
        rules.add(rule)
        for item in rule.expansion:
            # 将规则的每个项添加到字典中
            rule_dict[rule.origin.name].add(item)

    for rule in rules:
        origin_name = rule.origin.name
        dot.node(origin_name, label=origin_name, shape='ellipse', style='filled', fillcolor='lightblue')
        
        for expansion in rule_dict[origin_name]:
            dot.node(expansion.name, label=expansion.name, shape='ellipse')
            edge = (origin_name, expansion.name)
            edges.add(edge)
    
    for edge in edges:
        dot.edge(*edge)

    # 保存并呈现图形
    dot.render('lark_grammar.gv', view=True)


parser = Lark(grammar, start="start", parser="lalr")

rules_to_graphviz(parser)