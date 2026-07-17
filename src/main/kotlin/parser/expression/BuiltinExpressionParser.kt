package parser.expression

import parser.Parser
import parser.statement.ArgumentsParser
import program.expression.CallExpression
import program.expression.Expression
import program.expression.builtin.*

object BuiltinExpressionParser {
    fun parse(parser: Parser, name: String): Expression {
        return parser.program.parseCustomBuiltins(parser, name) ?: when (name) {
            "abs" -> AbsoluteValueBuiltin(ArgumentsParser.parse(parser))
            "int" -> IntegerCastBuiltin(ArgumentsParser.parse(parser))
            "float" -> FloatCastBuiltin(ArgumentsParser.parse(parser))
            "str" -> StringCastBuiltin(ArgumentsParser.parse(parser))
            "round" -> RoundBuiltin(ArgumentsParser.parse(parser))
            "floor" -> FloorBuiltin(ArgumentsParser.parse(parser))
            "ceil" -> CeilBuiltin(ArgumentsParser.parse(parser))
            "len" -> LengthBuiltin(ArgumentsParser.parse(parser))
            "print" -> PrintBuiltin(ArgumentsParser.parse(parser))
            "min" -> MinimumBuiltin(ArgumentsParser.parse(parser))
            "max" -> MaximumBuiltin(ArgumentsParser.parse(parser))
            "range" -> RangeBuiltin(ArgumentsParser.parse(parser))
            "type" -> TypeBuiltin(ArgumentsParser.parse(parser))
            "wait" -> WaitBuiltin(ArgumentsParser.parse(parser))
            else -> CallExpression(name, ArgumentsParser.parse(parser))
        }
    }
}
