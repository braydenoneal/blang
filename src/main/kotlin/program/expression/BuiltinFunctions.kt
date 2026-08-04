package program.expression

import program.Program
import program.RunException
import program.expression.value.FloatValue
import program.expression.value.IntegerValue
import program.expression.value.StringValue
import program.expression.value.Value
import program.expression.value.util.Null
import program.statement.IncompleteException
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

object BuiltinFunctions {
    val builtins: MutableMap<String, (Program, Arguments) -> Value<*>> = mutableMapOf(
        "abs" to ::absoluteValue,
        "round" to ::round,
        "floor" to ::floor,
        "ceil" to ::ceil,
        "print" to ::print,
        "min" to ::minimum,
        "max" to ::maximum,
        "type" to ::type,
        "wait" to ::wait,
    )

    fun register(name: String, function: (Program, Arguments) -> Value<*>) {
        builtins[name] = function
    }

    fun absoluteValue(program: Program, arguments: Arguments): Value<*> {
        context(program, arguments) {
            val value = arguments.getAny("value")

            if (value is IntegerValue) {
                return IntegerValue(abs(value.value))
            } else if (value is FloatValue) {
                return FloatValue(abs(value.value))
            }

            throw RunException("Expression is not a number", arguments.span)
        }
    }

    fun ceil(program: Program, arguments: Arguments): Value<*> {
        context(program, arguments) {
            return FloatValue(kotlin.math.ceil(arguments.get<FloatValue>("value").value.toDouble()).toFloat())
        }
    }

    fun floor(program: Program, arguments: Arguments): Value<*> {
        context(program, arguments) {
            return FloatValue(kotlin.math.floor(arguments.get<FloatValue>("value").value.toDouble()).toFloat())
        }
    }

    fun minMax(program: Program, arguments: Arguments, minimum: Boolean): Value<*> {
        context(program, arguments) {
            var a = arguments.getAny("a")
            var b = arguments.getAny("b")

            if (a is IntegerValue && b is FloatValue) {
                a = FloatValue(a.value.toFloat())
            } else if (a is FloatValue && b is IntegerValue) {
                b = FloatValue(b.value.toFloat())
            }

            if (a is IntegerValue && b is IntegerValue) {
                return IntegerValue(if (minimum) min(a.value, b.value) else max(a.value, b.value))
            } else if (a is FloatValue && b is FloatValue) {
                return FloatValue(if (minimum) min(a.value, b.value) else max(a.value, b.value))
            }

            throw RunException("Arguments are not numbers", arguments.span)
        }
    }

    fun maximum(program: Program, arguments: Arguments): Value<*> {
        context(program, arguments) {
            return minMax(program, arguments, false)
        }
    }

    fun minimum(program: Program, arguments: Arguments): Value<*> {
        context(program, arguments) {
            return minMax(program, arguments, true)
        }
    }

    fun print(program: Program, arguments: Arguments): Value<*> {
        context(program, arguments) {
            val value = arguments.getAny("value", StringValue(""))
            var string = value.toString()

            if (value is StringValue) {
                string = string.substring(1, string.length - 1)
            }

            println(string)
            return Null.VALUE
        }
    }

    fun round(program: Program, arguments: Arguments): Value<*> {
        context(program, arguments) {
            val value = arguments.getAny("value")

            if (value is IntegerValue) {
                return value
            } else if (value is FloatValue) {
                return IntegerValue(value.value.roundToInt())
            }

            throw RunException("Expression is not a number", arguments.span)
        }
    }

    fun type(program: Program, arguments: Arguments): Value<*> {
        context(program, arguments) {
            return StringValue(arguments.getAny("value").typeString())
        }
    }

    fun wait(program: Program, arguments: Arguments): Value<*> {
        context(program, arguments) {
            val value = arguments.get<IntegerValue>("value", IntegerValue(1))

            arguments.counter++

            if (arguments.counter > value.value) {
                return Null.VALUE
            }

            program.waitUntilNextTick()
            throw IncompleteException()
        }
    }
}
