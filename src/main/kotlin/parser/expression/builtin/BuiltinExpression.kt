package parser.expression.builtin

import parser.Program
import parser.expression.Arguments
import parser.expression.CallExpression
import parser.expression.Expression

object BuiltinExpression {
    fun parse(program: Program, name: String): Expression {
        return program.parseCustomBuiltins(name) ?: when (name) {
            "abs" -> AbsoluteValueBuiltin(Arguments.parse(program))
            "int" -> IntegerCastBuiltin(Arguments.parse(program))
            "float" -> FloatCastBuiltin(Arguments.parse(program))
            "str" -> StringCastBuiltin(Arguments.parse(program))
            "round" -> RoundBuiltin(Arguments.parse(program))
            "floor" -> FloorBuiltin(Arguments.parse(program))
            "ceil" -> CeilBuiltin(Arguments.parse(program))
            "len" -> LengthBuiltin(Arguments.parse(program))
            "print" -> PrintBuiltin(Arguments.parse(program))
            "min" -> MinimumBuiltin(Arguments.parse(program))
            "max" -> MaximumBuiltin(Arguments.parse(program))
            "range" -> RangeBuiltin(Arguments.parse(program))
            "type" -> TypeBuiltin(Arguments.parse(program))
            "wait" -> WaitBuiltin(Arguments.parse(program))
            else -> CallExpression(name, Arguments.parse(program))
        }
    }
}
