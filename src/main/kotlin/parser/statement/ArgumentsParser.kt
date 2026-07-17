package parser.statement

import parser.ParseException
import parser.Parser
import parser.expression.ExpressionParser
import parser.tokenizer.Type
import program.expression.Arguments
import program.expression.AssignmentExpression
import program.expression.Expression
import program.expression.VariableExpression
import java.util.*

object ArgumentsParser {
    fun parse(parser: Parser): Arguments {
        val arguments: MutableList<Expression> = Stack()
        val namedArguments: MutableMap<String, Expression> = HashMap()
        var parseDefaults = false

        parser.expect(Type.LEFT_PARENTHESIS)

        while (!parser.peekIs(Type.RIGHT_PARENTHESIS)) {
            val expression: Expression = ExpressionParser.parse(parser, 0, true)

            if (expression is AssignmentExpression) {
                parseDefaults = true
            }

            if (parseDefaults) {
                try {
                    val assignmentExpression = expression as AssignmentExpression
                    val variableExpression = assignmentExpression.variableExpression

                    if (variableExpression is VariableExpression && assignmentExpression.operator == "=") {
                        namedArguments[variableExpression.name] = assignmentExpression.expression
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
        return Arguments(arguments, namedArguments)
    }
}
