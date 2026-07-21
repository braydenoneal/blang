package parser.expression.infix

import parser.ParseException
import parser.Parser
import parser.expression.BuiltinExpressionParser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.*
import java.util.*

class CallExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, token: Token, left: Expression): Expression {
        val namelessArguments: MutableList<Expression> = Stack()
        val namedArguments: MutableMap<String, Expression> = HashMap()
        var parseDefaults = false

        while (!parser.peekIs(Type.RIGHT_PARENTHESIS)) {
            val expression: Expression = ExpressionParser.parse(parser, 0, true)

            if (expression is AssignExpression) {
                parseDefaults = true
            }

            if (parseDefaults) {
                try {
                    val assignExpression = expression as AssignExpression
                    val identifierExpression = assignExpression.left

                    if (identifierExpression is IdentifierExpression && assignExpression.operator == "=") {
                        namedArguments[identifierExpression.name] = assignExpression.right
                    } else {
                        throw ParseException("")
                    }
                } catch (_: ParseException) {
                    throw ParseException("Function cannot have parameter with default after parameter without default")
                }
            } else {
                namelessArguments.add(expression)
            }

            if (!parser.peekIs(Type.RIGHT_PARENTHESIS)) {
                parser.expect(Type.COMMA)
            }
        }

        parser.expect(Type.RIGHT_PARENTHESIS)

        val arguments = Arguments(namelessArguments, namedArguments)

        if (left is IdentifierExpression) {
            val builtin = BuiltinExpressionParser.builtins[left.name]

            if (builtin != null) {
                return builtin.invoke(arguments)
            }
        }

        return CallExpression(left, arguments)
    }
}
