package parser.expression.infix

import parser.ParseException
import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Token
import parser.tokenizer.Type
import program.expression.*

class CallExpressionParser(override val precedence: Int) : InfixParser {
    override fun parse(parser: Parser, token: Token, left: Expression): Expression {
        val namelessArguments: MutableList<Expression> = mutableListOf()
        val namedArguments: MutableMap<String, Expression> = mutableMapOf()
        var parseDefaults = false

        while (!parser.peekIs(Type.RIGHT_PARENTHESIS)) {
            val expression: Expression = ExpressionParser.parse(parser, 0, true)

            if (expression is AssignExpression && expression.operator == "=") {
                parseDefaults = true
            }

            if (parseDefaults) {
                try {
                    val assignExpression = expression as AssignExpression
                    val identifierExpression = assignExpression.left

                    if (identifierExpression is IdentifierExpression) {
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
        return CallExpression(left, Arguments(namelessArguments, namedArguments))
    }
}
