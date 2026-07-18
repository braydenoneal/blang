package parser.expression.infix

import parser.ParseException
import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.*
import java.util.*

class CallExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, token: Token, left: Expression): Expression {
        val arguments: MutableList<Expression> = Stack()
        val namedArguments: MutableMap<String, Expression> = HashMap()
        var parseDefaults = false

        while (!parser.peekIs(Type.RIGHT_PARENTHESIS)) {
            val expression: Expression = ExpressionParser.parse(parser, 0, true)

            if (expression is AssignmentExpression) {
                parseDefaults = true
            }

            if (parseDefaults) {
                try {
                    val assignmentExpression = expression as AssignmentExpression
                    val variableExpression = assignmentExpression.left

                    if (variableExpression is IdentifierExpression && assignmentExpression.operator == "=") {
                        namedArguments[variableExpression.name] = assignmentExpression.right
                    } else {
                        throw ParseException("")
                    }
                } catch (_: ParseException) {
                    throw ParseException("Function cannot have parameter with default after parameter without default")
                }
            } else {
                arguments.add(expression)
            }

            if (!parser.peekIs(Type.RIGHT_PARENTHESIS)) {
                parser.expect(Type.COMMA)
            }
        }

        parser.expect(Type.RIGHT_PARENTHESIS)
        return CallExpression(left, Arguments(arguments, namedArguments))
    }
}
