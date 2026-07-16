package program.expression

import parser.ParseException
import parser.Parser
import parser.tokenizer.Type
import program.Program
import program.RunException
import program.expression.value.*
import java.util.*

data class Arguments(val namelessArguments: MutableList<Expression>, val namedArguments: MutableMap<String, Expression>) {
    fun anyValue(program: Program, name: String, index: Int): Value<*>? {
        var expression = namedArguments[name]

        if (expression == null) {
            if (index >= namelessArguments.size) {
                throw RunException("Missing argument $name")
            }

            expression = namelessArguments[index]
        }

        return expression.evaluate(program)
    }

    fun booleanValue(program: Program, name: String, index: Int): BooleanValue? {
        val value = anyValue(program, name, index) ?: return null

        if (value is BooleanValue) {
            return value
        }

        throw RunException("$name is not a boolean")
    }

    fun floatValue(program: Program, name: String, index: Int): FloatValue? {
        val value = anyValue(program, name, index) ?: return null

        if (value is FloatValue) {
            return value
        }

        throw RunException("$name is not a float")
    }

    fun functionValue(program: Program, name: String, index: Int): FunctionValue? {
        val value = anyValue(program, name, index) ?: return null

        if (value is FunctionValue) {
            return value
        }

        throw RunException("$name is not a function")
    }

    fun integerValue(program: Program, name: String, index: Int): IntegerValue? {
        val value = anyValue(program, name, index) ?: return null

        if (value is IntegerValue) {
            return value
        }

        throw RunException("$name is not an integer")
    }

    fun listValue(program: Program, name: String, index: Int): ListValue? {
        val value = anyValue(program, name, index) ?: return null

        if (value is ListValue) {
            return value
        }

        throw RunException("$name is not a list")
    }

    @Suppress("unused")
    fun rangeValue(program: Program, name: String, index: Int): RangeValue? {
        val value = anyValue(program, name, index) ?: return null

        if (value is RangeValue) {
            return value
        }

        throw RunException("$name is not a range")
    }

    fun stringValue(program: Program, name: String, index: Int): StringValue? {
        val value = anyValue(program, name, index) ?: return null

        if (value is StringValue) {
            return value
        }

        throw RunException("$name is not a string")
    }

    companion object {
        fun parse(parser: Parser): Arguments {
            val arguments: MutableList<Expression> = Stack()
            val namedArguments: MutableMap<String, Expression> = HashMap()
            var parseDefaults = false

            parser.expect(Type.LEFT_PARENTHESIS)

            while (!parser.peekIs(Type.RIGHT_PARENTHESIS)) {
                val expression: Expression = Expression.parse(parser, true)

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

        val EMPTY: Arguments = Arguments(mutableListOf(), mutableMapOf())
    }
}
