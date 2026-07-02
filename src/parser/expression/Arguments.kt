package parser.expression

import parser.ParseException
import parser.Program
import parser.RunException
import parser.expression.value.*
import tokenizer.Type
import java.util.*


data class Arguments(val arguments: MutableList<Expression>, val namedArguments: MutableMap<String, Expression>) {
    fun anyValue(program: Program, name: String, index: Int): Value<*>? {
        var expression = namedArguments[name]

        if (expression == null) {
            if (index >= arguments.size) {
                throw RunException("Missing argument $name")
            }

            expression = arguments[index]
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
        fun parse(program: Program): Arguments {
            val arguments: MutableList<Expression> = Stack()
            val namedArguments: MutableMap<String, Expression> = HashMap()
            var parseDefaults = false

            program.expect(Type.PARENTHESIS, "(")

            while (!program.peekIs(Type.PARENTHESIS, ")")) {
                val expression: Expression = Expression.parse(program)

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

                if (!program.peekIs(Type.PARENTHESIS, ")")) {
                    program.expect(Type.COMMA)
                }
            }

            program.expect(Type.PARENTHESIS, ")")
            return Arguments(arguments, namedArguments)
        }

        val EMPTY: Arguments = Arguments(mutableListOf(), mutableMapOf())
    }
}
