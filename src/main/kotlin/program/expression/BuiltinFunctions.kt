package program.expression

import program.Program
import program.RunException
import program.expression.value.*
import program.expression.value.util.Null
import program.expression.value.util.Range
import program.statement.IncompleteException
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

object BuiltinFunctions {
    val builtins: MutableMap<String, (Program, Arguments) -> Value<*>> = mutableMapOf(
        "abs" to ::absoluteValue,
        "int" to ::integer,
        "float" to ::floatCast,
        "str" to ::stringCast,
        "round" to ::round,
        "floor" to ::floor,
        "ceil" to ::ceil,
        "len" to ::length,
        "print" to ::print,
        "min" to ::minimum,
        "max" to ::maximum,
        "range" to ::range,
        "type" to ::type,
        "wait" to ::wait,
    )

    fun register(name: String, function: (Program, Arguments) -> Value<*>) {
        builtins[name] = function
    }

    fun absoluteValue(program: Program, arguments: Arguments): Value<*> {
        val value = arguments.getAny(program, "value")

        if (value is IntegerValue) {
            return IntegerValue(abs(value.value))
        } else if (value is FloatValue) {
            return FloatValue(abs(value.value))
        }

        throw RunException("Expression is not a number")
    }

    fun ceil(program: Program, arguments: Arguments): Value<*> {
        return FloatValue(kotlin.math.ceil(arguments.get<FloatValue>(program, "value").value.toDouble()).toFloat())
    }

    fun floatCast(program: Program, arguments: Arguments): Value<*> {
        return FloatValue(arguments.get<IntegerValue>(program, "value").value.toFloat())
    }

    fun floor(program: Program, arguments: Arguments): Value<*> {
        return FloatValue(kotlin.math.floor(arguments.get<FloatValue>(program, "value").value.toDouble()).toFloat())
    }

    fun integer(program: Program, arguments: Arguments): Value<*> {
        return IntegerValue(arguments.get<FloatValue>(program, "value").value.toInt())
    }

    fun length(program: Program, arguments: Arguments): Value<*> {
        return IntegerValue(arguments.get<ListValue>(program, "value").value.size)
    }

    fun minMax(program: Program, arguments: Arguments, minimum: Boolean): Value<*> {
        var a = arguments.getAny(program, "a")
        var b = arguments.getAny(program, "b")

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

        throw RunException("Arguments are not numbers")
    }

    fun maximum(program: Program, arguments: Arguments): Value<*> {
        return minMax(program, arguments, false)
    }

    fun minimum(program: Program, arguments: Arguments): Value<*> {
        return minMax(program, arguments, true)
    }

    fun print(program: Program, arguments: Arguments): Value<*> {
        val value = arguments.getAny(program, "value", StringValue(""))
        var string = value.toString()

        if (value is StringValue) {
            string = string.substring(1, string.length - 1)
        }

        println(string)
        return Null.VALUE
    }

    fun range(program: Program, arguments: Arguments): Value<*> {
        val start = arguments.get<IntegerValue>(program, "start", IntegerValue(0)).value
        val end = arguments.get<IntegerValue>(program, "end").value
        val step = arguments.get<IntegerValue>(program, "step", IntegerValue(1)).value
        return RangeValue(Range(start, end, step))
    }

    fun round(program: Program, arguments: Arguments): Value<*> {
        val value = arguments.getAny(program, "value")

        if (value is IntegerValue) {
            return value
        } else if (value is FloatValue) {
            return IntegerValue(value.value.roundToInt())
        }

        throw RunException("Expression is not a number")
    }

    fun stringCast(program: Program, arguments: Arguments): Value<*> {
        return StringValue(arguments.getAny(program, "value").value.toString())
    }

    fun type(program: Program, arguments: Arguments): Value<*> {
        return StringValue(arguments.getAny(program, "value").typeString())
    }

    fun wait(program: Program, arguments: Arguments): Value<*> {
        val value = arguments.get<IntegerValue>(program, "value", IntegerValue(1))

        arguments.counter++

        if (arguments.counter > value.value) {
            return Null.VALUE
        }

        program.waitUntilNextTick()
        throw IncompleteException()
    }
}