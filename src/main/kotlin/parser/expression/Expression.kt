package parser.expression

import parser.Parser
import parser.Program
import parser.expression.value.Value

interface Expression {
    fun evaluate(program: Program): Value<*>?

    interface Output

    data class Operand(val expression: Expression) : Output

    data class Operator(val operator: String) : Output

    companion object {
        fun parse(parser: Parser): Expression {
            return ExpressionParser(parser).parse()
        }
    }
}
