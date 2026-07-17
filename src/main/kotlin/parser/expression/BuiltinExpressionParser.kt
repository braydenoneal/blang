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
        register("abs") { arguments -> AbsoluteValueBuiltin(arguments) }
        register("int") { arguments -> IntegerCastBuiltin(arguments) }
        register("float") { arguments -> FloatCastBuiltin(arguments) }
        register("str") { arguments -> StringCastBuiltin(arguments) }
        register("round") { arguments -> RoundBuiltin(arguments) }
        register("floor") { arguments -> FloorBuiltin(arguments) }
        register("ceil") { arguments -> CeilBuiltin(arguments) }
        register("len") { arguments -> LengthBuiltin(arguments) }
        register("print") { arguments -> PrintBuiltin(arguments) }
        register("min") { arguments -> MinimumBuiltin(arguments) }
        register("max") { arguments -> MaximumBuiltin(arguments) }
        register("range") { arguments -> RangeBuiltin(arguments) }
        register("type") { arguments -> TypeBuiltin(arguments) }
        register("wait") { arguments -> WaitBuiltin(arguments) }
    }

    fun parse(parser: Parser, name: String): Expression {
        val arguments = ArgumentsParser.parse(parser)
        val builtin = builtins[name] ?: return CallExpression(name, arguments)
        return builtin.invoke(arguments)
    }
}
