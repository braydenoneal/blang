package program.expression.builtin

import parser.Parser
import program.expression.Arguments
import program.expression.CallExpression
import program.expression.Expression

object BuiltinExpression {
    fun parse(parser: Parser, name: String): Expression {
        return parser.program.parseCustomBuiltins(parser, name) ?: when (name) {
            "abs" -> AbsoluteValueBuiltin(Arguments.parse(parser))
            "int" -> IntegerCastBuiltin(Arguments.parse(parser))
            "float" -> FloatCastBuiltin(Arguments.parse(parser))
            "str" -> StringCastBuiltin(Arguments.parse(parser))
            "round" -> RoundBuiltin(Arguments.parse(parser))
            "floor" -> FloorBuiltin(Arguments.parse(parser))
            "ceil" -> CeilBuiltin(Arguments.parse(parser))
            "len" -> LengthBuiltin(Arguments.parse(parser))
            "print" -> PrintBuiltin(Arguments.parse(parser))
            "min" -> MinimumBuiltin(Arguments.parse(parser))
            "max" -> MaximumBuiltin(Arguments.parse(parser))
            "range" -> RangeBuiltin(Arguments.parse(parser))
            "type" -> TypeBuiltin(Arguments.parse(parser))
            "wait" -> WaitBuiltin(Arguments.parse(parser))
            else -> CallExpression(name, Arguments.parse(parser))
        }
    }
}
