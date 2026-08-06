package program.expression.value.list

import program.Program
import program.expression.Arguments
import program.expression.value.Static
import program.expression.value.Value

class ListValue(value: MutableList<Value<*>>) : Value<MutableList<Value<*>>>(value) {
    override fun toString(): String {
        return "[${value.joinToString()}]"
    }

    override fun toList(): List<Value<*>> {
        return value
    }

    override fun plus(other: MutableList<Value<*>>): Value<*> {
        return ListValue(value.plus(other).toMutableList())
    }

    override fun set(item: Value<*>, setValue: Value<*>): Value<*> {
        value[asIndex(item)] = setValue
        return setValue
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

    companion object : Static<ListValue>() {
        override val name = "List"

        override fun initializeItems() {
            register(SizeItem)
        }

        override fun initializeFunctions() {
            register(AppendFunction)
            register(ContainsAllFunction)
            register(ContainsFunction)
            register(InsertFunction)
            register(PopFunction)
            register(RemoveFunction)
            register(ReversedFunction)
            register(ReverseFunction)
        }
    }
}
