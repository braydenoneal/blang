package program.expression.value.function

import program.Program
import program.expression.Arguments
import program.expression.value.Static
import program.expression.value.Value

class FunctionValue(value: Function) : Value<Function>(value) {
    context(program: Program, arguments: Arguments)
    override fun innerCall(): Value<*> {
        return value.innerCall()
    }

    context(program: Program, arguments: Arguments)
    override fun abort() {
        value.abort()
    }

    context(program: Program, arguments: Arguments)
    override fun done() {
        value.done()
    }

    context(program: Program)
    override fun getItem(name: String): Value<*> {
        return static.items[name]?.get() ?: super.getItem(name)
    }

    context(program: Program, arguments: Arguments)
    override fun innerCallFunction(name: String, local: Boolean): Value<*> {
        return static.functions[name]?.call() ?: super.innerCallFunction(name, local)
    }

    override val static = Companion

    companion object : Static<FunctionValue>() {
        override val name = "Function"
    }
}
