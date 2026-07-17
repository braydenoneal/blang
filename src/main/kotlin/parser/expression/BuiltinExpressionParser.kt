package parser.expression

import parser.Parser
import parser.statement.ArgumentsParser
import program.expression.Arguments
import program.expression.CallExpression
import program.expression.Expression
import program.expression.builtin.*

object BuiltinExpressionParser {
    val builtins: MutableMap<String, (Arguments) -> Builtin> = mutableMapOf()

    fun register(name: String, builder: (Arguments) -> Builtin) {
        builtins[name] = builder
    }

    fun initialize() {
        register("abs") { arguments: Arguments -> AbsoluteValueBuiltin(arguments) }
        register("int") { arguments: Arguments -> IntegerCastBuiltin(arguments) }
        register("float") { arguments: Arguments -> FloatCastBuiltin(arguments) }
        register("str") { arguments: Arguments -> StringCastBuiltin(arguments) }
        register("round") { arguments: Arguments -> RoundBuiltin(arguments) }
        register("floor") { arguments: Arguments -> FloorBuiltin(arguments) }
        register("ceil") { arguments: Arguments -> CeilBuiltin(arguments) }
        register("len") { arguments: Arguments -> LengthBuiltin(arguments) }
        register("print") { arguments: Arguments -> PrintBuiltin(arguments) }
        register("min") { arguments: Arguments -> MinimumBuiltin(arguments) }
        register("max") { arguments: Arguments -> MaximumBuiltin(arguments) }
        register("range") { arguments: Arguments -> RangeBuiltin(arguments) }
        register("type") { arguments: Arguments -> TypeBuiltin(arguments) }
        register("wait") { arguments: Arguments -> WaitBuiltin(arguments) }
    }

    fun parse(parser: Parser, name: String): Expression {
        val arguments = ArgumentsParser.parse(parser)
        val builtin = builtins[name] ?: return CallExpression(name, arguments)
        return builtin.invoke(arguments)
    }
}
